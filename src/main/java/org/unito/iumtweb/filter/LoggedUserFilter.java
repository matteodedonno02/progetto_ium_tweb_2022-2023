package org.unito.iumtweb.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class LoggedUserFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
      /*  String servletName = ((HttpServletRequest) servletRequest).getHttpServletMapping().getServletName();
        String operation = servletRequest.getParameter("operation");

        if (servletName.equals("ProfessorServlet") && operation.equals("mostRequested")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (servletName.equals("CourseServlet") && operation.equals("mostRequested")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (servletName.equals("UserServlet") && (operation.equals("login") || operation.equals("register") || operation.equals("getFromSession"))) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        if (!userIsLogged((HttpServletRequest) servletRequest)) {
            servletResponse.getWriter().write("{\"error\": \"Operation not permitted\"}");
            return;
        }

*/        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean userIsLogged(HttpServletRequest request) {
        return request.getSession().getAttribute("loggedUser") != null;
    }
}
