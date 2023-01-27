package org.unito.iumtweb.servlet;

import com.google.gson.Gson;
import org.unito.iumtweb.db.DAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.util.ArrayList;

@WebServlet(name = "InitServlet", value = "/InitServlet", asyncSupported = true, loadOnStartup = 1)
public class InitServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        String dbUrl = getServletContext().getInitParameter("dbUrl");
        String dbUsername = getServletContext().getInitParameter("dbUsername");
        String dbPassword = getServletContext().getInitParameter("dbPassword");

        DAO managerDB = new DAO(dbUrl, dbUsername, dbPassword);
        getServletContext().setAttribute("managerDB", managerDB);

        Gson gson = new Gson();
        getServletContext().setAttribute("gson", gson);
    }
}
