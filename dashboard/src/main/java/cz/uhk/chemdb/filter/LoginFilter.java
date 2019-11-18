package cz.uhk.chemdb.filter;

import cz.uhk.chemdb.bean.UserManager;

import javax.faces.application.ViewExpiredException;
import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/secured/*")
public class LoginFilter implements Filter {

    private static final String LOGIN_PAGE = "/login.xhtml";

    @Inject
    private UserManager userManager;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (userManager != null) {
            if (userManager.isLoggedIn()) {
                try {
                    chain.doFilter(request, response);
                } catch (ViewExpiredException e) {
                    return;
                }
            } else {
                httpServletResponse.sendRedirect(
                        httpServletRequest.getContextPath() + LOGIN_PAGE);
            }
        } else {
            httpServletResponse.sendRedirect(
                    httpServletRequest.getContextPath() + LOGIN_PAGE);
        }
    }

    @Override
    public void destroy() {

    }
}
