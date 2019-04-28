package com.pinwheel.anabel.service.interceptor;

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
public class SecurityInterceptor extends HandlerInterceptorAdapter {
    /**
     * {@inheritDoc}
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);

        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("authError") != null) {
            modelAndView.getModel().put("authError", session.getAttribute("authError"));
            session.removeAttribute("authError");
        }
    }
}
