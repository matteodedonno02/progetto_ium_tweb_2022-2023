package org.unito.iumtweb.filter;

import org.unito.iumtweb.model.User;
import org.unito.iumtweb.util.FilterProperties;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AdminFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        FilterProperties adminFilterProperties = (FilterProperties) servletRequest.getServletContext().getAttribute("adminFilterProperties");
        String servletName = ((HttpServletRequest) servletRequest).getHttpServletMapping().getServletName();
        String operation = servletRequest.getParameter("operation");

        if(adminFilterProperties.propertyExists(servletName, operation) && !checkIfUserIsAdmin((HttpServletRequest) servletRequest)) {
            servletResponse.getWriter().write("{\"error\": \"Operation not permitted\"}");
            return;
        }

//        if (servletName.equals("RepetitionServlet") && operation.equals("select") && !checkIfUserIsAdmin((HttpServletRequest) servletRequest)) {
//            servletResponse.getWriter().write("{\"error\": \"Operation not permitted\"}");
//            return;
//        }
//
//        if (servletName.equals("CourseServlet") && (operation.equals("newTeaching") || operation.equals("search")) && !checkIfUserIsAdmin((HttpServletRequest) servletRequest)) {
//            servletResponse.getWriter().write("{\"error\": \"Operation not permitted\"}");
//            return;
//        }
//
//        if (servletName.equals("TeachingServlet") && (operation.equals("add") || operation.equals("search")) && !checkIfUserIsAdmin((HttpServletRequest) servletRequest)) {
//            servletResponse.getWriter().write("{\"error\": \"Operation not permitted\"}");
//            return;
//        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean checkIfUserIsAdmin(HttpServletRequest request) {
        User loggedUser = (User) request.getSession().getAttribute("loggedUser");
        if (loggedUser != null) {
            return loggedUser.getRole();
        }
        return false;
    }
}
