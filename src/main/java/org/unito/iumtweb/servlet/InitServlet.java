package org.unito.iumtweb.servlet;

import org.unito.iumtweb.db.DAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet(name = "InitServlet", value = "/InitServlet", asyncSupported = true, loadOnStartup = 1)
public class InitServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        String dbUrl = getServletContext().getInitParameter("dbUrl");
        String dbUsername = getServletContext().getInitParameter("dbUsername");
        String dbPassword = getServletContext().getInitParameter("dbPassword");

        DAO dao = new DAO(dbUrl, dbUsername, dbPassword);
        getServletContext().setAttribute("dao", dao);
        System.out.println(dao.getProfessors().toString());
    }
}
