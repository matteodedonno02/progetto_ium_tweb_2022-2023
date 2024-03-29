package org.unito.iumtweb.servlet;

import com.cloudinary.Cloudinary;
import com.google.gson.Gson;
import org.unito.iumtweb.db.DAO;
import org.unito.iumtweb.model.Course;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

@WebServlet(name = "CourseServlet", value = "/CourseServlet")
public class CourseServlet extends HttpServlet {

    private DAO managerDB;
    private Gson gson;
    private Cloudinary cloudinary;

    @Override
    public void init() throws ServletException {
        managerDB = (DAO) getServletContext().getAttribute("managerDB");
        gson = (Gson) getServletContext().getAttribute("gson");
        cloudinary = (Cloudinary) getServletContext().getAttribute("cloudinary");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operation = request.getParameter("operation");
        switch (operation) {
            case "select":
                selectCourse(request, response);
                break;
            case "mostRequested":
                selectMostRequested(request, response);
                break;
            case "newTeaching":
                newTeaching(request, response);
                break;
            case "search":
                searchCourses(request, response);
                break;
            default:
                PrintWriter pw = response.getWriter();
                pw.write("{\"error\":\"Invalid operation\"}");
                pw.close();
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
                PrintWriter pw = response.getWriter();
                pw.write("{\"error\":\"Invalid operation\"}");
                pw.close();
                break;

        }
    }

    private void selectCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        if (request.getParameter("idCourse") != null) {
            Course course = managerDB.getCourseById(Integer.valueOf(request.getParameter("idCourse")));
            if (course != null)
                pw.write(gson.toJson(course));
            else
                pw.write("{\"error\":\"Course not found\"}");

        } else {
            ArrayList<Course> courses = managerDB.getCourses();
            pw.write(gson.toJson(courses));
        }
        pw.close();
    }

    private void searchCourses(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        pw.write(gson.toJson(managerDB.searchCourses(request.getParameter("searchField"))));
        pw.close();
    }

    private void newTeaching(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        ArrayList<Course> courses = managerDB.getCoursesBySerialNumberNeg(request.getParameter("serialNumber"));
        pw.write(gson.toJson(courses));
        pw.close();
    }

    private void selectMostRequested(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        ArrayList<Course> courses = managerDB.getMostRequestedCourses();
        pw.write(gson.toJson(courses));
        pw.close();
    }

    private void addCourse(HttpServletRequest request, HttpServletResponse
            response) throws IOException {
        PrintWriter pw = response.getWriter();
        Map<String, String> img = cloudinary.uploader().upload(request.getParameter("file"), null);
        int res = managerDB.addCourse(request.getParameter("title"), img.get("secure_url").replace("upload/", "upload/c_scale,w_400/"));
        switch (res) {
            case 1:
                pw.write(gson.toJson(managerDB.getCourseByTitle(request.getParameter("title"))));
                break;
            case 0:
                pw.write("{\"error\":\"Course already exists\"}");
                break;
            case -1:
                pw.write("{\"error\":\"Server error\"}");
                break;
        }
        pw.close();
    }

    private void updateCourse(HttpServletRequest request, HttpServletResponse
            response) throws IOException {
        PrintWriter pw = response.getWriter();
        int res = managerDB.updateCourse(Integer.valueOf(request.getParameter("idCourse")), request.getParameter("title"));
        switch (res) {
            case 1:
                pw.write(gson.toJson(managerDB.getCourseById(Integer.valueOf(request.getParameter("idCourse")))));
                break;
            case 0:
                pw.write("{\"error\":\"Course already exists\"}");
                break;
            case -1:
                pw.write("{\"error\":\"Server error\"}");
                break;
        }
        pw.close();
    }

    private void deleteCourse(HttpServletRequest request, HttpServletResponse
            response) throws IOException {
        PrintWriter pw = response.getWriter();

        int res = managerDB.deleteCourse(Integer.valueOf(request.getParameter("idCourse")));
        if (res == -1)
            pw.write("{\"error\":\"Server error\"}");
        else
            pw.write("1");

        pw.close();
    }
}
