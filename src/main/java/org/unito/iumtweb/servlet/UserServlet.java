package org.unito.iumtweb.servlet;


import com.google.gson.Gson;
import org.unito.iumtweb.db.DAO;
import org.unito.iumtweb.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "UserServlet", value = "/UserServlet")
public class UserServlet extends HttpServlet {
    private DAO managerDB;

    //TODO: istanziare tutti i parametri della request
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
            case "getFromSession":
                getLoggedUserFromSession(request, response);
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
                response.getWriter().write("{\"error\":\"Invalid operation\"}");
                break;

        }
    }

    private void getLoggedUserFromSession(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User loggedUser = (User) request.getSession().getAttribute("loggedUser");
        response.getWriter().write(new Gson().toJson(loggedUser));
    }

    private void selectUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("email") != null) {
            User user = managerDB.getUserByEmail(request.getParameter("email"));
            if (user != null) {
                response.getWriter().write(new Gson().toJson(user));
            } else {
                response.getWriter().write("{\"error\":\"User not found\"}");
            }
        } else {
            ArrayList<User> users = managerDB.getUsers();
            response.getWriter().write(new Gson().toJson(users));
        }
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        int res = managerDB.addUser(email, request.getParameter("name"), request.getParameter("surname"), request.getParameter("password"));
        switch (res) {
            case 1:
                response.getWriter().write(new Gson().toJson(managerDB.getUserByEmail(email)));
                break;
            case 0:
                response.getWriter().write("{\"error\":\"Email already exists\"}");
                break;
            case -1:
                response.getWriter().write("{\"error\":\"Server error\"}");
                break;
        }
    }

    private void editUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int res = managerDB.updateUser(request.getParameter("newEmail"), request.getParameter("oldEmail"), request.getParameter("name"), request.getParameter("surname"), Boolean.valueOf(request.getParameter("role")));
        switch (res) {
            case 1:
                response.getWriter().write(new Gson().toJson(managerDB.getUserByEmail(request.getParameter("newEmail"))));
                break;
            case 0:
                response.getWriter().write("{\"error\":\"Email already exists\"}");
                break;
            case -1:
                response.getWriter().write("{\"error\":\"Server error\"}");
                break;
        }
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int res = managerDB.deleteUser(request.getParameter("email"));
    }

    private void updatePassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int res = managerDB.updatePassword(request.getParameter("email"), request.getParameter("password"));
    }

    public void loginUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        User user = managerDB.login(email, password);

        if (user != null) {
            Gson gson = new Gson();
            String json = gson.toJson(user);

            response.addHeader("Set-Cookie", "JSESSIONID=" + request.getSession().getId() + "; SameSite=None;");
            response.getWriter().write(json);

            request.getSession().setAttribute("loggedUser", user);
        } else {
            if (managerDB.getUserByEmail(email) != null) {
                response.getWriter().write("{\"error\":\"Wrong password\"}");
            } else {
                response.getWriter().write("{\"error\":\"Email not found\"}");
            }
        }
    }

    private void checkSession(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.getWriter().write(new Gson().toJson((User) request.getSession().getAttribute("loggedUser")));
    }
}
