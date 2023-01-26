package org.unito.iumtweb.servlet;

import com.google.gson.Gson;
import org.unito.iumtweb.db.DAO;
import org.unito.iumtweb.model.Professor;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "ProfessorServlet", value = "/ProfessorServlet")
public class ProfessorServlet extends HttpServlet {
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
                selectUser(request, response);
                break;
            case "mostRequested":
                getMostRequestedProfessor(request, response);
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
                addProfessor(request, response);
                break;
            case "edit":
                editProfessor(request, response);
                break;
            case "delete":
                deleteProfessor(request, response);
                break;
            default:
                response.getWriter().write("{\"error\":\"Invalid operation\"}");
        }
    }

    private void selectUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("serialNumber") != null) {
            Professor professor = managerDB.getProfessorBySerialNumber(request.getParameter("serialNumber"));
            if (professor != null) {
                response.getWriter().write(new Gson().toJson(professor));
            } else {
                response.getWriter().write("{\"error\":\"Professor not found\"}");
            }
        } else {
            response.getWriter().write(new Gson().toJson(managerDB.getProfessors()));
        }
    }

    private void getMostRequestedProfessor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().write(new Gson().toJson(managerDB.getMostRequestedProfessor()));
    }

    private void addProfessor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String serialNumber = request.getParameter("serialNumber");
        int res = managerDB.addProfessor(serialNumber, request.getParameter("name"), request.getParameter("surname"), request.getParameter("serialNumber"));
        switch (res) {
            case 1:
                response.getWriter().write(new Gson().toJson(managerDB.getProfessorBySerialNumber(serialNumber)));
                break;
            case 0:
                response.getWriter().write("{\"error\":\"Serial number already exists\"}");
                break;
            case -1:
                response.getWriter().write("{\"error\":\"Server error\"}");
                break;
        }
    }

    private void editProfessor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int res = managerDB.updateProfessor(request.getParameter("oldSerialNumber"), request.getParameter("newSerialNumber"), request.getParameter("name"), request.getParameter("surname"));
        switch (res) {
            case 1:
                response.getWriter().write(new Gson().toJson(managerDB.getProfessorBySerialNumber(request.getParameter("newSerialNumber"))));
                break;
            case 0:
                response.getWriter().write("{\"error\":\"Serial number already exists\"}");
                break;
            case -1:
                response.getWriter().write("{\"error\":\"Server error\"}");
                break;
        }
    }

    private void deleteProfessor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        managerDB.deleteProfessor(request.getParameter("serialNumber"));

        response.getWriter().write("1");
    }


}
