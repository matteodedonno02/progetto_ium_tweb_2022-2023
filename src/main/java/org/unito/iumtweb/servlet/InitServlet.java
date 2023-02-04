package org.unito.iumtweb.servlet;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.unito.iumtweb.db.DAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
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

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        getServletContext().setAttribute("gson", gson);


        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dnndcjczm",
                "api_key", "498245257748997",
                "api_secret", "5bPvh0K_Lvov3G2eGzgkMxutcJY"));
        getServletContext().setAttribute("cloudinary", cloudinary);
    }
}
