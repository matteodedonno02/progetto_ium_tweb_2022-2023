package org.unito.iumtweb.servlet;

import com.google.gson.Gson;
import org.unito.iumtweb.db.DAO;
import org.unito.iumtweb.model.Course;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "CourseServlet", value = "/CourseServlet")
public class CourseServlet extends HttpServlet {

    private DAO managerDB;

    @Override
    public void init() throws ServletException {
        managerDB = (DAO) getServletContext().getAttribute("managerDB");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operation = request.getParameter("operation");
        switch (operation) {
            case "select":
                selectCourse(request, response);
                break;
            default:
                response.getWriter().write("{\"error\":\"Invalid operation\"}");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operation = request.getParameter("operation");
        switch (operation) {
            case "add":
                addCourse(request, response);
                break;
            case "edit":
                updateCourse(request, response);
                break;
            case "delete":
                deleteCourse(request, response);
                break;
            default:
                response.getWriter().write("{\"error\":\"Invalid operation\"}");
                break;

        }
    }

    private void selectCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("idCourse") != null) {
            Course course = managerDB.getCourseById(Integer.valueOf(request.getParameter("idCourse")));
            if (course != null)
                response.getWriter().write(new Gson().toJson(course));
            else
                response.getWriter().write("{\"error\":\"Course not found\"}");

        } else {
            ArrayList<Course> courses = managerDB.getCourses();
            response.getWriter().write(new Gson().toJson(courses));
        }
    }

    private void addCourse(HttpServletRequest request, HttpServletResponse
            response) throws IOException {
        int res = managerDB.addCourse(request.getParameter("title"));
        switch (res) {
            case 1:
                //TODO: non saprei cosa mettere nella response nel caso il corso venga inserito, inoltre in caso modifico il metodo nel dao per la risposta
                break;
            case -1:
                response.getWriter().write("{\"error\":\"Server error\"}");
                break;
        }
    }

    private void updateCourse(HttpServletRequest request, HttpServletResponse
            response) throws IOException {
        int res = managerDB.updateCourse(Integer.valueOf(request.getParameter("idCourse")), request.getParameter("title"));
        switch (res) {
            case 1:
                //TODO: non saprei cosa mettere nella response nel caso il corso venga aggiornato, inoltre in caso modifico il metodo nel dao per la risposta
                break;
            case -1:
                response.getWriter().write("{\"error\":\"Server error\"}");
                break;
        }
    }

    private void deleteCourse(HttpServletRequest request, HttpServletResponse
            response) throws IOException {
        int res = managerDB.deleteCourse(Integer.valueOf(request.getParameter("idCourse")));
        switch (res) {
            case 1:
                //TODO: non saprei cosa mettere nella response nel caso il corso venga aggiornato, inoltre in caso modifico il metodo nel dao per la risposta
                break;
            case -1:
                response.getWriter().write("{\"error\":\"Server error\"}");
                break;
        }
    }


}
