package org.unito.iumtweb.util;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

//TODO: CHIEDERE ALLA PROFESSORESSA
@WebFilter({"/UserServlet", "/CourseServlet", "/ProfessorServlet", "/TeachingServlet", "/RepetitionServlet"})
public class ContentTypeFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletResponse.setContentType("application/json; charset=utf-8");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
