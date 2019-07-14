package com.pinwheel.anabel.service.interceptor;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateModel;
import freemarker.template.Version;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Freemarker interceptor. Sets to the request attributes references to the static classes.
 *
 * @version 1.0.0
 */
public class FreeMarkerInterceptor extends HandlerInterceptorAdapter {
    /**
     * {@inheritDoc}
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);

        if (request.getAttribute("statics") == null) {
            BeansWrapper wrapper = new BeansWrapper(new Version(2, 3, 27));
            TemplateModel statics = wrapper.getStaticModels();
            request.setAttribute("statics", statics);
        }
    }
}
