package org.unito.iumtweb.servlet;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.unito.iumtweb.db.DAO;
import org.unito.iumtweb.util.FilterProperties;
import org.unito.iumtweb.util.EmailSender;

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

        FilterProperties adminFilterProperties = new FilterProperties();
        adminFilterProperties.add("RepetitionServlet", new String[]{"select"});
        adminFilterProperties.add("CourseServlet", new String[]{"newTeaching", "search"});
        adminFilterProperties.add("TeachingServlet", new String[]{"add", "search"});
        getServletContext().setAttribute("adminFilterProperties", adminFilterProperties);

        FilterProperties loggedUserFilterProperties = new FilterProperties();
        loggedUserFilterProperties.add("ProfessorServlet", new String[]{"mostRequested"});
        loggedUserFilterProperties.add("CourseServlet", new String[]{"mostRequested"});
        loggedUserFilterProperties.add("UserServlet", new String[]{"login", "add", "getFromSession"});
        getServletContext().setAttribute("loggedUserFilterProperties", loggedUserFilterProperties);

        DAO managerDB = new DAO(dbUrl, dbUsername, dbPassword);
        getServletContext().setAttribute("managerDB", managerDB);

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        getServletContext().setAttribute("gson", gson);

        EmailSender emailSender = new EmailSender();
        getServletContext().setAttribute("emailSender", emailSender);


        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dnndcjczm",
                "api_key", getServletContext().getInitParameter("cloudinaryApiKey"),
                "api_secret", "5bPvh0K_Lvov3G2eGzgkMxutcJY"));
        getServletContext().setAttribute("cloudinary", cloudinary);
    }
}
