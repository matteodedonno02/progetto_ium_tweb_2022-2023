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

    @Override
    public void init() throws ServletException {
        super.init();
        managerDB = (DAO) getServletContext().getAttribute("dao");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operation = request.getParameter("operation");
        switch (operation) {
            case "selectAll":
                selectAllUsers(request, response);
            case "selectById":
                selectByIdUser(request, response);
            default:
                response.getWriter().write("{\"error\":\"Invalid operation\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String operation = request.getParameter("operation");
        switch (operation) {
            case "add":
                addUser(request, response);
            case "edit":
                editUser(request, response);
            case "delete":
                deleteUser(request, response);
            case "updatePassword":
                updatePassword(request, response);
            default:
                response.getWriter().write("{\"error\":\"Invalid operation\"}");

        }
    }

    private void selectAllUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ArrayList<User> users = managerDB.getUsers();
        response.getWriter().write(new Gson().toJson(users));
    }

    private void selectByIdUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = managerDB.getUserByEmail(request.getParameter("email"));
        response.getWriter().write(new Gson().toJson(user));
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int res = managerDB.addUser(request.getParameter("email"), request.getParameter("name"), request.getParameter("surname"), request.getParameter("password"));
        switch (res) {
            case 1:
                response.getWriter().write(new Gson().toJson(managerDB.getUserByEmail(request.getParameter("email"))));
            case 0:
                response.getWriter().write(new Gson().toJson("{\"error\":\"Email already exists\"}"));
            case -1:
                response.getWriter().write(new Gson().toJson("{\"error\":\"Generic error\"}"));
        }
    }

    private void editUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int res = managerDB.updateUser(request.getParameter("newEmail"), request.getParameter("oldEmail"), request.getParameter("name"), request.getParameter("surname"), Boolean.valueOf(request.getParameter("role")));
        switch (res) {
            case 1:
                response.getWriter().write(new Gson().toJson(managerDB.getUserByEmail(request.getParameter("newEmail"))));
            case 0:
                response.getWriter().write(new Gson().toJson("{\"error\":\"Email already exists\"}"));
            case -1:
                response.getWriter().write(new Gson().toJson("{\"error\":\"Generic error\"}"));
        }
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int res = managerDB.deleteUser(request.getParameter("email"));
        //TODO: definire se e cosa mandare come response
    }

    private void updatePassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int res = managerDB.updatePassword(request.getParameter("password"), request.getParameter("password"));
        //TODO: definire se e cosa mandare come response
    }
}
