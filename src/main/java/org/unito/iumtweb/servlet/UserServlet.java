package org.unito.iumtweb.servlet;


import com.google.gson.Gson;
import org.unito.iumtweb.db.DAO;
import org.unito.iumtweb.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Pattern;

@WebServlet(name = "UserServlet", value = "/UserServlet")
public class UserServlet extends HttpServlet {
    private DAO managerDB;
    private Gson gson;

    //TODO: istanziare tutti i parametri della request
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
                selectUser(request, response);
                break;
            case "getFromSession":
                getLoggedUserFromSession(request, response);
                break;

            case "logout":
                destroySession(request, response);
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
                addUser(request, response);
                break;
            case "edit":
                editUser(request, response);
                break;
            case "delete":
                deleteUser(request, response);
                break;
            case "updatePassword":
                updatePassword(request, response);
                break;
            case "login":
                loginUser(request, response);
                break;
            case "checkSession":
                checkSession(request, response);
                break;
            case "getFromSession":
                getLoggedUserFromSession(request, response);
                break;
            default:
                PrintWriter pw = response.getWriter();
                pw.write("{\"error\":\"Invalid operation\"}");
                pw.close();
                break;
        }
    }

    private void getLoggedUserFromSession(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        User loggedUser = (User) request.getSession().getAttribute("loggedUser");
        pw.write(gson.toJson(loggedUser));
        pw.close();
    }

    private void destroySession(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        request.getSession().invalidate();
        pw.write("{\"message\": \"Logout success\"}");
        pw.close();
    }

    private void selectUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        if (request.getParameter("email") != null) {
            User user = managerDB.getUserByEmail(request.getParameter("email"));
            if (user != null) {
                pw.write(gson.toJson(user));
            } else {
                pw.write("{\"error\":\"User not found\"}");
            }
        } else {
            ArrayList<User> users = managerDB.getUsers();
            pw.write(gson.toJson(users));
        }
        pw.close();
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if(name.equals("") || surname.equals("") || email.equals("") || password.equals("")) {
            pw.write("{\"error\":\"Empty fields\"}");
        } else if(!isEmail(email)) {
            pw.write("{\"error\":\"Email not valid\"}");
        } else {
            int res = managerDB.addUser(email, name, surname, password);
            switch (res) {
                case 1:
                    pw.write(gson.toJson(managerDB.getUserByEmail(email)));
                    break;
                case 0:
                    pw.write("{\"error\":\"Email already exists\"}");
                    break;
                case -1:
                    pw.write("{\"error\":\"Server error\"}");
                    break;
            }
        }
        
        pw.close();
    }

    private void editUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        int res = managerDB.updateUser(request.getParameter("newEmail"), request.getParameter("oldEmail"), request.getParameter("name"), request.getParameter("surname"), Boolean.valueOf(request.getParameter("role")));
        switch (res) {
            case 1:
                pw.write(gson.toJson(managerDB.getUserByEmail(request.getParameter("newEmail"))));
                break;
            case 0:
                pw.write("{\"error\":\"Email already exists\"}");
                break;
            case -1:
                pw.write("{\"error\":\"Server error\"}");
                break;
        }
        pw.close();
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int res = managerDB.deleteUser(request.getParameter("email"));
    }

    private void updatePassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int res = managerDB.updatePassword(request.getParameter("email"), request.getParameter("password"));
    }

    private boolean isEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        return Pattern.compile(emailRegex).matcher(email).matches();
    }

    public void loginUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        User user = managerDB.login(email, password);

        if(email.equals("") || password.equals("")) {
            pw.write("{\"error\":\"Empty fields\"}");
        } else if(!isEmail(email)) {
            pw.write("{\"error\":\"Email not valid\"}");
        } else if (user != null) {
            String json = gson.toJson(user);

            response.addHeader("Set-Cookie", "JSESSIONID=" + request.getSession().getId() + "; SameSite=None;");
            pw.write(json);

            request.getSession().setAttribute("loggedUser", user);
        } else {
            if (managerDB.getUserByEmail(email) != null) {
                pw.write("{\"error\":\"Wrong password\"}");
            } else {
                pw.write("{\"error\":\"Email not found\"}");
            }
        }
        pw.close();
    }

    private void checkSession(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = response.getWriter();
        pw.write(gson.toJson((User) request.getSession().getAttribute("loggedUser")));
        pw.close();
    }
}
