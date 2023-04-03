package org.unito.iumtweb.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.unito.iumtweb.db.DAO;
import org.unito.iumtweb.model.Course;
import org.unito.iumtweb.model.Professor;
import org.unito.iumtweb.model.Repetition;
import org.unito.iumtweb.model.Teaching;
import org.unito.iumtweb.util.DateAndTimeManipulator;
import org.unito.iumtweb.util.EmailSender;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.stream.Collectors;

@WebServlet(name = "RepetitionServlet", value = "/RepetitionServlet")
public class RepetitionServlet extends HttpServlet {
    private DAO managerDB;
    private Gson gson;
    private EmailSender emailSender;

    @Override
    public void init() throws ServletException {
        managerDB = (DAO) getServletContext().getAttribute("managerDB");
        gson = (Gson) getServletContext().getAttribute("gson");
        emailSender = (EmailSender) getServletContext().getAttribute("emailSender");
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
            case "selectByEmailFromToday":
                selectRepetitionsByEmailFromToday(request, response);
                break;
            case "selectByEmailAndDate":
                selectRepetitionByEmailAndDate(request, response);
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
            case "newRepetitions":
                generateNewRepetitions(request, response);
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
                Thread t = new Thread(() -> {
                    emailSender.bookedRepetition(r);
                });
                t.start();
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



    public void generateNewRepetitions(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int idCourse = Integer.valueOf(request.getParameter("idCourse"));
        String serialNumber = request.getParameter("serialNumber");
        String date = request.getParameter("date");

        PrintWriter pw = response.getWriter();
        ArrayList<Repetition> existingRepetitions;
        ArrayList<Repetition> newRepetitions = new ArrayList<Repetition>();

        if(idCourse != -1 && serialNumber.equals("")) {
            existingRepetitions = managerDB.getRepetitionsByCourseAndDate(idCourse, date);
            for(int i = 15; i <= 18;i ++) {
                for (Professor professor : managerDB.getProfessorsByCourse(idCourse)) {
                    if (!professorIsBusy(existingRepetitions, professor.getSerialNumber(), String.valueOf(i)) && !repetitionExists(existingRepetitions, String.valueOf(i), idCourse, professor.getSerialNumber())) {
                        Teaching t = managerDB.getTeachingByProfessorAndCourse(professor.getSerialNumber(), idCourse);
                        newRepetitions.add(new Repetition(t, DateAndTimeManipulator.fromStringToSqlDate(date), DateAndTimeManipulator.fromIntToSqlTime(i)));
                    }
                }
            }
        } else if (idCourse == -1 && !serialNumber.equals("")) {
            existingRepetitions = managerDB.getRepetitionsByProfessorAndDate(serialNumber, date);
            for(int i = 15; i <= 18; i ++) {
                for(Course course : managerDB.getCoursesByProfessor(serialNumber)) {
                    if(!professorIsBusy(existingRepetitions, serialNumber, String.valueOf(i)) && !repetitionExists(existingRepetitions, String.valueOf(i), course.getIdCourse(), serialNumber)) {
                        Teaching t = managerDB.getTeachingByProfessorAndCourse(serialNumber, course.getIdCourse());
                        newRepetitions.add(new Repetition(t, DateAndTimeManipulator.fromStringToSqlDate(date), DateAndTimeManipulator.fromIntToSqlTime(i)));
                    }
                }
            }
        } else if(idCourse != -1 && !serialNumber.equals("")) {
            existingRepetitions = managerDB.getRepetitionsByProfessorCourseAndDate(serialNumber, idCourse, date);
            for(int i = 15; i <= 18; i ++) {
                if(!professorIsBusy(existingRepetitions, serialNumber, String.valueOf(i)) && !repetitionExists(existingRepetitions, String.valueOf(i), idCourse, serialNumber)) {
                    Teaching t = managerDB.getTeachingByProfessorAndCourse(serialNumber, idCourse);
                    newRepetitions.add(new Repetition(t, DateAndTimeManipulator.fromStringToSqlDate(date), DateAndTimeManipulator.fromIntToSqlTime(i)));
                }
            }
        } else if(idCourse == -1 && serialNumber.equals("")) {
            pw.write("[]");
            pw.close();
            return;
        }

        pw.write(gson.toJson(newRepetitions));
        pw.close();
    }

    private boolean repetitionExists(ArrayList<Repetition> existingRepetitions, String time, int idCourse, String serialNumber) {
        return existingRepetitions.stream().anyMatch((repetition) -> {
            return repetition.getTeaching().getProfessor().getSerialNumber().equals(serialNumber)
                    && repetition.getTeaching().getCourse().getIdCourse() == idCourse
                    && repetition.getTime().toString().split(":")[0].equals(time);
        });
    }

    private boolean professorIsBusy(ArrayList<Repetition> existingRepetitions, String serialNumber, String time) {
        return existingRepetitions.stream().filter((repetition) -> {
            return repetition.getTeaching().getProfessor().getSerialNumber().equals(serialNumber)
                    && repetition.getTime().toString().split(":")[0].equals(time);
        }).collect(Collectors.toList()).size() == 1;
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

    private void selectRepetitionsByEmailFromToday(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        ArrayList<Repetition> repetitions = managerDB.getRepetitionsByEmailFromToday(request.getParameter("email"));
        pw.write(gson.toJson(repetitions));
        pw.close();
    }

    private void selectRepetitionByEmail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        ArrayList<Repetition> repetitions = managerDB.getRepetitionsByEmail(request.getParameter("email"));
        pw.write(gson.toJson(repetitions));
        pw.close();
    }

    private void selectRepetitionByEmailAndDate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        ArrayList<Repetition> repetitions = managerDB.getRepetitionsByEmailAndDate(request.getParameter("email"), request.getParameter("date"));
        pw.write(gson.toJson(repetitions));
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
