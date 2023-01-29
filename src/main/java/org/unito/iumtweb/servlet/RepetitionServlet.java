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
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@WebServlet(name = "RepetitionServlet", value = "/RepetitionServlet")
public class RepetitionServlet extends HttpServlet {
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
                selectRepetition(request, response);
                break;
            case "selectByEmail":
                selectRepetitionByEmail(request, response);
                break;
            case "selectByCourseAndDate":
                selectRepetitionByCourseAndDate(request, response);
                break;
            case "selectByProfessorAndDate":
                selectRepetitionByProfessorAndDate(request, response);
                break;
            case "selectByProfessorCourseAndDate":
                selectRepetitionByProfessorCourseAndDate(request, response);
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
        PrintWriter pw = response.getWriter();
        String email = request.getParameter("email");
        int idTeaching = Integer.valueOf(request.getParameter("idTeaching"));
        String date = request.getParameter("date");
        String time = request.getParameter("time");

        int res = managerDB.addRepetition(email, idTeaching, date, time);
        switch (res) {
            case 1:
                Repetition r = managerDB.getRepetition(email, idTeaching, date, time);
                pw.write(gson.toJson(r));
                EmailSender.bookedRepetition(r);
                break;
            case 0:
                pw.write("{\"error\":\"Email or teaching doesn't exist\"}");
                break;
            case -1:
                pw.write("{\"error\":\"Server error\"}");
                break;
        }

        pw.close();
    }

    private void selectRepetitionByCourseAndDate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        ArrayList<Repetition> repetitions = managerDB.getRepetitionsByCourseAndDate(Integer.parseInt(request.getParameter("idCourse")), request.getParameter("date"));
        pw.write(gson.toJson(repetitions));
        pw.close();
    }

    private void selectRepetitionByProfessorAndDate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        ArrayList<Repetition> repetitions = managerDB.getRepetitionsByProfessorAndDate(request.getParameter("serialNumber"), request.getParameter("date"));
        pw.write(gson.toJson(repetitions));
        pw.close();
    }

    private void selectRepetitionByProfessorCourseAndDate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        ArrayList<Repetition> repetitions = managerDB.getRepetitionsByProfessorCourseAndDate(request.getParameter("serialNumber"), Integer.valueOf(request.getParameter("idCourse")), request.getParameter("date"));
        pw.write(gson.toJson(repetitions));
        pw.close();
    }

    private void selectRepetition(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();

        if (request.getParameter("idRepetition") != null) {
            Repetition repetition = managerDB.getRepetitionById(Integer.valueOf(request.getParameter("idRepetition")));
            if (repetition == null) {
                pw.write("{\"error\":\"Repetition not found\"}");
            } else {
                pw.write(gson.toJson(repetition));
            }
        } else {
            pw.write(gson.toJson(managerDB.getRepetitions()));
        }

        pw.close();
    }

    private void selectRepetitionByEmail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        String email = request.getParameter("email");
        ArrayList<Repetition> repetitions = managerDB.getRepetitionsByEmail(email);
        pw.write(gson.toJson(managerDB.getRepetitionsByEmail(email)));
        pw.close();
    }

    private void updateRepetition(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        int idRepetition = Integer.valueOf(request.getParameter("idRepetition"));
        int newState = Integer.valueOf(request.getParameter("newState"));

        Repetition repetition = managerDB.updateRepetition(idRepetition, newState);
        if (repetition != null) {
            pw.write(gson.toJson(managerDB.getRepetitionById(idRepetition)));
        } else {
            pw.write("{\"error\":\"Repetition not found\"}");
        }

        pw.close();
    }
}
