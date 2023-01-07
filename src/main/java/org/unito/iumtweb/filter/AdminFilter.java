package org.unito.iumtweb.filter;

import org.unito.iumtweb.model.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AdminFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
     /*   String servletName = ((HttpServletRequest) servletRequest).getHttpServletMapping().getServletName();
        String operation = servletRequest.getParameter("operation");

        if (servletName.equals("RepetitionServlet") && operation.equals("select") && !checkIfUserIsAdmin((HttpServletRequest) servletRequest)) {
            servletResponse.getWriter().write("{\"error\": \"Operation not permitted\"}");
            return;
        }

        if (servletName.equals("TeachingServlet") && operation.equals("add") && !checkIfUserIsAdmin((HttpServletRequest) servletRequest)) {
            servletResponse.getWriter().write("{\"error\": \"Operation not permitted\"}");
            return;
        }
*/
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
