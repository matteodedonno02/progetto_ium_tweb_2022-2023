package org.unito.iumtweb.servlet;

import com.google.gson.Gson;
import org.unito.iumtweb.db.DAO;
import org.unito.iumtweb.model.Repetition;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "RepetitionServlet", value = "/RepetitionServlet")
public class RepetitionServlet extends HttpServlet {
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
                selectRepetition(request, response);
                break;
            case "selectByEmail":
                selectRepetitionByEmail(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operation = request.getParameter("operation");
        switch (operation) {
            case "add":
                addRepetition(request, response);
                break;
        }
    }

    private void addRepetition(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        int idTeaching = Integer.valueOf(request.getParameter("idTeaching"));
        String date = request.getParameter("date");
        String time = request.getParameter("time");

        int res = managerDB.addRepetition(email, idTeaching, date, time);
        switch (res) {
            case 1:
                response.getWriter().write(new Gson().toJson(managerDB.getRepetition(email, idTeaching, date, time)));
                break;
            case 0:
                response.getWriter().write("{\"error\":\"Email or teaching doesn't exist\"}");
                break;
            case -1:
                response.getWriter().write("{\"error\":\"Server error\"}");
                break;
        }
    }

    private void selectRepetition(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("idRepetition") != null) {
            Repetition repetition = managerDB.getRepetitionById(Integer.valueOf(request.getParameter("idRepetition")));
            if (repetition == null) {
                response.getWriter().write("{\"error\":\"Repetition not found\"}");
            } else {
                response.getWriter().write(new Gson().toJson(repetition));
            }

            return;
        }

        response.getWriter().write(new Gson().toJson(managerDB.getRepetitions()));
    }

    private void selectRepetitionByEmail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        System.out.println("YEAH!");
        response.getWriter().write(new Gson().toJson(managerDB.getRepetitionsByEmail(email)));
    }
}
