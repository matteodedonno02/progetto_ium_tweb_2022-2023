package org.unito.iumtweb.servlet;

import com.google.gson.Gson;
import org.unito.iumtweb.db.DAO;
import org.unito.iumtweb.model.Course;
import org.unito.iumtweb.model.Teaching;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "TeachingServlet", value = "/TeachingServlet")
public class TeachingServlet extends HttpServlet {

    private DAO managerDB;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        managerDB = (DAO) getServletContext().getAttribute("managerDB");
        gson = (Gson) getServletContext().getAttribute("gson");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operation = request.getParameter("operation");
        switch (operation) {
            case "select":
                selectTeaching(request, response);
                break;
            case "selectByCourse":
                selectTeachingByCourse(request, response);
                break;
            case "selectByProfessor":
                selectTeachingByProfessor(request, response);
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
                addTeaching(request, response);
                break;
            case "edit":
                updateTeaching(request, response);
                break;
            case "delete":
                deleteTeaching(request, response);
                break;
            default:
                PrintWriter pw = response.getWriter();
                pw.write("{\"error\":\"Invalid operation\"}");
                pw.close();
                break;
        }
    }

    private void selectTeachingByCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        int idCourse = Integer.parseInt(request.getParameter("idCourse"));
        ArrayList<Teaching> teachings = managerDB.getTeachingsByCourse(idCourse);
        pw.write(gson.toJson(teachings));
        pw.close();
    }

    private void selectTeachingByProfessor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        String serialNumber = request.getParameter("serialNumber");
        ArrayList<Teaching> teachings = managerDB.getTeachingsByProfessor(serialNumber);
        pw.write(gson.toJson(teachings));
        pw.close();
    }

    private void selectTeaching(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        if (request.getParameter("idTeaching") != null) {
            Teaching teaching = managerDB.getTeachingById(Integer.valueOf(request.getParameter("idTeaching")));
            if (teaching != null)
                pw.write(gson.toJson(teaching));
            else
                pw.write("{\"error\":\"Teaching not found\"}");

        } else {
            ArrayList<Teaching> teachings = managerDB.getTeachings();
            pw.write(gson.toJson(teachings));
        }
        pw.close();
    }

    private void addTeaching(HttpServletRequest request, HttpServletResponse
            response) throws IOException {
        PrintWriter pw = response.getWriter();
        int res = managerDB.addTeaching(request.getParameter("serialNumber"), Integer.valueOf(request.getParameter("idCourse")));
        switch (res) {
            case 1:
                pw.write(gson.toJson(managerDB.getTeachingByProfessorCourse(request.getParameter("serialNumber"), Integer.valueOf(request.getParameter("idCourse")))));
                break;
            case 0:
                pw.write("{\"error\":\"Teaching already exists\"}");
                break;
            case -1:
                pw.write("{\"error\":\"Server error\"}");
                break;
        }
        pw.close();
    }

    private void updateTeaching(HttpServletRequest request, HttpServletResponse
            response) throws IOException {
        PrintWriter pw = response.getWriter();
        int res = managerDB.updateTeaching(Integer.valueOf(request.getParameter("idTeaching")), request.getParameter("serialNumber"), Integer.valueOf(request.getParameter("idCourse")));

        switch (res) {
            case 1:
                pw.write(gson.toJson(managerDB.getTeachingByProfessorCourse(request.getParameter("serialNumber"), Integer.valueOf(request.getParameter("idCourse")))));
                break;
            case 0:
                pw.write("{\"error\":\"Teaching already exists\"}");
                break;
            case -1:
                pw.write("{\"error\":\"Server error\"}");
                break;
        }

        pw.close();
    }

    private void deleteTeaching(HttpServletRequest request, HttpServletResponse
            response) throws IOException {
        PrintWriter pw = response.getWriter();
        int res = managerDB.deleteTeaching(Integer.valueOf(request.getParameter("idTeaching")));
        if (res == -1)
            pw.write("{\"error\":\"Server error\"}");
        else
            pw.write("1");
        pw.close();
    }
}
