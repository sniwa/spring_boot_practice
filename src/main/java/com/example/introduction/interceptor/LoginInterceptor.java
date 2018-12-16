package com.example.introduction.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ログインしていない場合は、ログイン画面にリダイレクトさせる
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUri = request.getRequestURI();
        if (requestUri.equals(request.getContextPath() + "/sign") || requestUri.equals(request.getContextPath() + "/login")) {
            return true;
        }

        String[] pathElements = requestUri.split("/");
        if (pathElements.length == 0) {
            pathElements = new String[] { request.getServletPath() };
        }

        String lastPath = pathElements[pathElements.length - 1];

        if (lastPath.matches(".*\\.(js|css|png)")) {
            return true;
        }

        Object isLogin = request.getSession().getAttribute("LOGIN");
        if (isLogin instanceof Boolean) {
            if ((Boolean)isLogin) {
                return true;
            }
        }

        response.sendRedirect("/login");
        return false;
    }
}
