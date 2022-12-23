package org.unito.iumtweb.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ContentTypeFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Origin", "*");
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Methods", "GET, POST");
        ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Credentials", "true");
        servletResponse.setContentType("application/json; charset=utf-8");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
