package com.example.memo.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/** 统一设置字符编码，并拦截未登录的备忘录请求。 */
public class LoginFilter implements Filter {
    public void init(FilterConfig filterConfig) {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String path = httpRequest.getServletPath();

        if (path.startsWith("/memo/") || "/logout".equals(path)) {
            HttpSession session = httpRequest.getSession(false);
            if (session == null || !(session.getAttribute("userId") instanceof Integer)) {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
                return;
            }
        }
        chain.doFilter(request, response);
    }

    public void destroy() {
    }
}
