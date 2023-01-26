package org.unito.iumtweb.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.unito.iumtweb.db.DAO;
import org.unito.iumtweb.model.Repetition;
import org.unito.iumtweb.util.DateAndTimeManipulator;
import org.unito.iumtweb.util.EmailSender;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
            case "available":
                getAvailableRepetitions(request, response);
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
            case "edit":
                updateRepetition(request, response);
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
                Repetition r = managerDB.getRepetition(email, idTeaching, date, time);
                response.getWriter().write(new Gson().toJson(r));
                EmailSender.bookedRepetition(r);
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
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        ArrayList<Repetition> repetitions = managerDB.getRepetitionsByEmail(email);
        response.getWriter().write(gson.toJson(managerDB.getRepetitionsByEmail(email)));
    }

    private void updateRepetition(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idRepetition = Integer.valueOf(request.getParameter("idRepetition"));
        int newState = Integer.valueOf(request.getParameter("newState"));

        Repetition repetition = managerDB.updateRepetition(idRepetition, newState);
        if (repetition != null) {
            response.getWriter().write(new Gson().toJson(managerDB.getRepetitionById(idRepetition)));
        } else {
            response.getWriter().write("{\"error\":\"Repetition not found\"}");
        }
    }

    private void getAvailableRepetitions(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LocalDate now = LocalDate.now();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        ArrayList<Repetition> availableRepetitions = managerDB.getAvailableRepetitions(DateAndTimeManipulator.fromLocalDateToString(now), DateAndTimeManipulator.fromLocalDateToString(now.plusDays(7)), "17:00:00", "20:00:00");

        response.getWriter().write(gson.toJson(availableRepetitions));
    }
}
