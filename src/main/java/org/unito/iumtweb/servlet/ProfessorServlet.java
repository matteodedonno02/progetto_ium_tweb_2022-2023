package org.unito.iumtweb.servlet;

import com.cloudinary.Cloudinary;
import com.google.gson.Gson;
import org.unito.iumtweb.db.DAO;
import org.unito.iumtweb.model.Professor;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "ProfessorServlet", value = "/ProfessorServlet")
public class ProfessorServlet extends HttpServlet {
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
                selectUser(request, response);
                break;
            case "mostRequested":
                getMostRequestedProfessor(request, response);
                break;
            case "search":
                searchProfessor(request, response);
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
                addProfessor(request, response);
                break;
            case "edit":
                editProfessor(request, response);
                break;
            case "delete":
                deleteProfessor(request, response);
                break;
            default:
                PrintWriter pw = response.getWriter();
                pw.write("{\"error\":\"Invalid operation\"}");
                pw.close();
                break;
        }
    }

    private void selectUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        if (request.getParameter("serialNumber") != null) {
            Professor professor = managerDB.getProfessorBySerialNumber(request.getParameter("serialNumber"));
            if (professor != null) {
                pw.write(gson.toJson(professor));
            } else {
                pw.write("{\"error\":\"Professor not found\"}");
            }
        } else {
            pw.write(gson.toJson(managerDB.getProfessors()));
        }
        pw.close();
    }

    private void searchProfessor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        pw.write(gson.toJson(managerDB.searchProfessor(request.getParameter("searchField"))));
        pw.close();
    }

    private void getMostRequestedProfessor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        pw.write(gson.toJson(managerDB.getMostRequestedProfessor()));
        pw.close();
    }

    private void addProfessor(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter pw = response.getWriter();

        String serialNumber = request.getParameter("serialNumber");
        Map<String, String> img = cloudinary.uploader().upload(request.getParameter("file"), null);
        int res = managerDB.addProfessor(serialNumber, request.getParameter("name"), request.getParameter("surname"), img.get("secure_url").replace("upload/", "upload/c_scale,w_400/"));
        switch (res) {
            case 1:
                pw.write(gson.toJson(managerDB.getProfessorBySerialNumber(serialNumber)));
                break;
            case 0:
                pw.write("{\"error\":\"Serial number already exists\"}");
                break;
            case -1:
                pw.write("{\"error\":\"Server error\"}");
                break;
        }
        pw.close();
    }

    private void editProfessor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        int res = managerDB.updateProfessor(request.getParameter("oldSerialNumber"), request.getParameter("newSerialNumber"), request.getParameter("name"), request.getParameter("surname"));
        switch (res) {
            case 1:
                pw.write(gson.toJson(managerDB.getProfessorBySerialNumber(request.getParameter("newSerialNumber"))));
                break;
            case 0:
                pw.write("{\"error\":\"Serial number already exists\"}");
                break;
            case -1:
                pw.write("{\"error\":\"Server error\"}");
                break;
        }
        pw.close();
    }

    private void deleteProfessor(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        managerDB.deleteProfessor(request.getParameter("serialNumber"));
        pw.write("1");
    }


}
