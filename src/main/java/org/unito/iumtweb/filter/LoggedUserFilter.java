package org.unito.iumtweb.filter;

import org.unito.iumtweb.model.User;
import org.unito.iumtweb.util.FilterProperties;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class LoggedUserFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        FilterProperties loggedUserFilterProperties = (FilterProperties) servletRequest.getServletContext().getAttribute("loggedUserFilterProperties");
        String servletName = ((HttpServletRequest) servletRequest).getHttpServletMapping().getServletName();
        String operation = servletRequest.getParameter("operation");

        if(loggedUserFilterProperties.propertyExists(servletName, operation)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (!userIsLogged((HttpServletRequest) servletRequest)) {
            servletResponse.getWriter().write("{\"error\": \"Operation not permitted\"}");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean userIsLogged(HttpServletRequest request) {
        return request.getSession().getAttribute("loggedUser") != null;
    }
}
