package com.pinwheel.anabel.service.handler;

import com.pinwheel.anabel.service.module.asset.AssetManager;
import com.pinwheel.anabel.service.module.asset.AssetStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.ViewNameMethodReturnValueHandler;

import javax.annotation.Priority;

/**
 * Sets assets map according on returned template name.
 *
 * @version 1.0
 * @see AssetManager
 * @see AssetStorage
 */
@RequiredArgsConstructor
@Priority(1)
public class AssetsMethodReturnValueHandler extends ViewNameMethodReturnValueHandler {
    /**
     * Injection of {@link AssetManager} bean.
     */
    private final AssetManager assetManager;

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest) throws Exception {
        if (returnValue instanceof CharSequence) {
            String template = returnValue.toString();

            if (isRedirectViewName(template)) {
                template = retrieveTemplateFromRedirect(template);
            }

            AssetStorage storage = assetManager.getStorage();
            mavContainer.getModel().addAttribute("assets", storage.getAllByTemplate(template));
        } else if (returnValue != null) {
            // should not happen
            throw new UnsupportedOperationException("Unexpected return type: " +
                    returnType.getParameterType().getName() + " in method: " + returnType.getMethod());
        }

        super.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
    }

    /**
     * Retrieves template name from redirect string.
     *
     * @param template redirect string.
     * @return template name.
     */
    protected String retrieveTemplateFromRedirect(String template) {
        template = template.replaceFirst("redirect:", "");
        return template.startsWith("/") ? template.substring(1) : template;
    }
}
