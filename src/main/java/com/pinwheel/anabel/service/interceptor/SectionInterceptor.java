package com.pinwheel.anabel.service.interceptor;

import com.pinwheel.anabel.repository.SectionRepository;
import com.pinwheel.anabel.service.SectionService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Security interceptor.
 *
 * @version 1.0.0
 */
@RequiredArgsConstructor
public class SectionInterceptor extends HandlerInterceptorAdapter {
    private final SectionService sectionService;

    /**
     * {@inheritDoc}
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);

        if (request.getAttribute("menuSections") == null) {
            request.setAttribute("menuSections", sectionService.findAll());
        }
    }
}
