package org.unito.iumtweb.servlet;

import com.google.gson.Gson;
import org.unito.iumtweb.db.DAO;
import org.unito.iumtweb.model.Course;
import org.unito.iumtweb.model.Teaching;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "TeachingServlet", value = "/TeachingServlet")
public class TeachingServlet extends HttpServlet {

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
                selectTeaching(request, response);
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
                addTeaching(request, response);
                break;
            case "edit":
                updateTeaching(request, response);
                break;
            case "delete":
                deleteTeaching(request, response);
                break;
            default:
                response.getWriter().write("{\"error\":\"Invalid operation\"}");
                break;

        }
    }

    private void selectTeaching(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("idTeaching") != null) {
            Teaching teaching = managerDB.getTeachingById(Integer.valueOf(request.getParameter("idTeaching")));
            if (teaching != null)
                response.getWriter().write(new Gson().toJson(teaching));
            else
                response.getWriter().write("{\"error\":\"Teaching not found\"}");

        } else {
            ArrayList<Teaching> teachings = managerDB.getTeachings();
            response.getWriter().write(new Gson().toJson(teachings));
        }
    }

    private void addTeaching(HttpServletRequest request, HttpServletResponse
            response) throws IOException {
        int res = managerDB.addTeaching(request.getParameter("serialNumber"), Integer.valueOf(request.getParameter("idCourse")));
        switch (res) {
            case 1:
                //TODO: non saprei cosa mettere nella response nel caso l'insegnamento venga inserito, inoltre in caso modifico il metodo nel dao per la risposta
                break;
            case -1:
                response.getWriter().write("{\"error\":\"Server error\"}");
                break;
        }
    }

    private void updateTeaching(HttpServletRequest request, HttpServletResponse
            response) throws IOException {
        managerDB.updateTeaching(Integer.valueOf(request.getParameter("idTeaching")), request.getParameter("serialNumber"), Integer.valueOf(request.getParameter("idCourse")));
    }

    private void deleteTeaching(HttpServletRequest request, HttpServletResponse
            response) throws IOException {
        managerDB.deleteTeaching(Integer.valueOf(request.getParameter("idTeaching")));
    }
}
