package com.pinwheel.anabel.service.interceptor;

import com.sun.tools.javac.util.List;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Listratenko Stanislav
 * @version 1.0.0
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);

        if (session != null) {
//            session.removeAttribute("authError");
        }

        return super.preHandle(request, response, handler);
    }

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
