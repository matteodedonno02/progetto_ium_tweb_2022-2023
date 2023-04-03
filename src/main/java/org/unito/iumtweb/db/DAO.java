package org.unito.iumtweb.db;

import org.unito.iumtweb.model.*;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class DAO {
    private final String dbUrl;
    private final String dbUsername;
    private final String dbPassword;

    public DAO(String dbUrl, String dbUsername, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
        registerDriver();
    }

    public ArrayList<Course> getCourses() {
        ArrayList<Course> courses = new ArrayList<>();
        Statement s = null;
        ResultSet rs = null;
        Connection conn = openConnection();
        try {
            String query = "SELECT * FROM course where active = 1";
            s = conn.createStatement();
            rs = s.executeQuery(query);
            while (rs.next()) {
                courses.add(new Course(rs.getInt("idCourse"), rs.getString("title"), rs.getString("iconUrl"), rs.getBoolean("active")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }
        return courses;
    }

    public ArrayList<Course> searchCourses(String searchField) {
        ArrayList<Course> courses = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = openConnection();
        try {
            ps = conn.prepareStatement("SELECT * FROM course c WHERE LOWER(c.title) LIKE ? AND active = 1");
            ps.setString(1, "%" + searchField.toLowerCase() + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                courses.add(new Course(rs.getInt("idCourse"), rs.getString("title"), rs.getString("iconUrl"), rs.getBoolean("active")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return courses;
    }

    public ArrayList<Course> getCoursesBySerialNumberNeg(String serialNumber) {
        Connection conn = openConnection();

        ArrayList<Course> courses = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM course c WHERE c.active = 1 AND NOT EXISTS(SELECT idCourse FROM teaching WHERE serialNumber=? AND c.idCourse=idCourse); ");
            ps.setString(1, serialNumber);
            rs = ps.executeQuery();
            while (rs.next()) {
                courses.add(new Course(rs.getInt("idCourse"), rs.getString("title"), rs.getString("iconUrl"), rs.getBoolean("active")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }


        return courses;
    }


    public ArrayList<Course> getMostRequestedCourses() {
        Connection conn = openConnection();

        ArrayList<Course> mostRequestedCourses = new ArrayList<Course>();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT COUNT(r.idRepetition) as nRepetition, c.idCourse, c.title, c.iconUrl, c.active FROM course c JOIN teaching t ON c.idCourse = t.idCourse LEFT JOIN repetition r ON t.idTeaching = r.idTeaching WHERE  c.active = 1 GROUP BY c.idCourse ORDER BY nRepetition DESC LIMIT 4;");
            rs = ps.executeQuery();
            while (rs.next()) {
                mostRequestedCourses.add(new Course(rs.getInt("idCourse"), rs.getString("title"), rs.getString("iconUrl"), rs.getBoolean("active")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return mostRequestedCourses;
    }

    public int addCourse(String title, String iconUrl) {
        Connection conn = openConnection();
        PreparedStatement ps = null;
        int res = 1;
        try {
            ps = conn.prepareStatement("INSERT INTO course (title, iconUrl) VALUES (?, ?)");
            ps.setString(1, title);
            ps.setString(2, iconUrl);
            ps.execute();
        } catch (SQLIntegrityConstraintViolationException e) {
            res = 0;
        } catch (SQLException e) {
            res = -1;
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return res;
    }

    public int updateCourse(int idCourse, String title) {
        Connection conn = openConnection();
        PreparedStatement ps = null;
        int res = 1;
        try {
            ps = conn.prepareStatement("UPDATE course SET title = ? WHERE idCourse = ?");
            ps.setString(1, title);
            ps.setInt(2, idCourse);
            ps.execute();
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
            res = 0;
        } catch (SQLException e) {
            res = -1;
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return res;
    }

    public int deleteCourse(int idCourse) {
        Connection conn = openConnection();
        PreparedStatement ps = null;
        int res = 1;
        try {
            ps = conn.prepareStatement("UPDATE course SET active = FALSE WHERE idCourse = ?");
            ps.setInt(1, idCourse);
            ps.execute();
        } catch (SQLException e) {
            res = -1;
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return res;
    }

    public Course getCourseById(int idCourse) {
        Course c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = openConnection();
        try {
            ps = conn.prepareStatement("SELECT * FROM course WHERE idCourse = ? AND active = 1");
            ps.setInt(1, idCourse);
            rs = ps.executeQuery();
            if (rs.next())
                c = new Course(rs.getInt("idCourse"), rs.getString("title"), rs.getString("iconUrl"), rs.getBoolean("active"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return c;
    }


    public Course getCourseByTitle(String title) {
        Course c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = openConnection();
        try {
            ps = conn.prepareStatement("SELECT * FROM course WHERE title = ? AND active = 1");
            ps.setString(1, title);
            rs = ps.executeQuery();
            if (rs.next())
                c = new Course(rs.getInt("idCourse"), rs.getString("title"), rs.getString("iconUrl"), rs.getBoolean("active"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return c;
    }

    public User login(String email, String password) {
        Connection conn = openConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        User logged = null;
        try {
            String query = "SELECT * from user WHERE email = ? AND password = SHA2(?,256)";
            ps = conn.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                logged = new User(rs.getString("email"), rs.getString("name"), rs.getString("surname"), rs.getBoolean("role"), rs.getBoolean("active"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return logged;
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        Statement s = null;
        ResultSet rs = null;
        Connection conn = openConnection();

        try {
            String query = "SELECT * FROM user WHERE active = 1 ";

            s = conn.createStatement();
            rs = s.executeQuery(query);
            while (rs.next()) {
                users.add(new User(rs.getString("email"), rs.getString("name"), rs.getString("surname"), rs.getBoolean("role"), rs.getBoolean("active")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return users;
    }

    public int addUser(String email, String name, String surname, String password) {
        Connection conn = openConnection();
        PreparedStatement ps = null;
        int res = 1;

        try {
            String query = "INSERT INTO user (email,password,name,surname) VALUES (?,SHA2(?,256),?,?)";
            ps = conn.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);
            ps.setString(3, name);
            ps.setString(4, surname);
            ps.execute();
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
            res = 0;
        } catch (SQLException e) {
            e.printStackTrace();
            res = -1;
        } finally {
            closeConnection(conn);
        }

        return res;
    }

    public int deleteUser(String email) {
        Connection conn = openConnection();
        PreparedStatement ps = null;
        int res = 1;
        try {
            String query = "UPDATE user SET active = 0 WHERE email = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, email);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            res = -1;
        } finally {
            closeConnection(conn);
        }

        return res;
    }

    public int updatePassword(String email, String password) {
        Connection conn = openConnection();
        PreparedStatement ps = null;
        int res = 1;
        try {
            String query = "UPDATE user SET password = SHA2(?,256) WHERE email = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, password);
            ps.setString(2, email);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            res = -1;
        } finally {
            closeConnection(conn);
        }

        return res;
    }

    public int updateUser(String newEmail, String oldEmail, String name, String surname, boolean role) {
        Connection conn = openConnection();
        PreparedStatement ps = null;
        int res = 1;
        try {
            String query = "UPDATE user SET email = ?,name = ?, surname = ?, role = ? WHERE email = ? AND active = 1";
            ps = conn.prepareStatement(query);
            ps.setString(1, newEmail);
            ps.setString(2, name);
            ps.setString(3, surname);
            ps.setBoolean(4, role);
            ps.setString(5, oldEmail);
            ps.execute();
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
            res = 0;
        } catch (SQLException e) {
            e.printStackTrace();
            res = -1;
        } finally {
            closeConnection(conn);
        }

        return res;
    }

    public User getUserByEmail(String email) {
        User u = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = openConnection();
        try {
            ps = conn.prepareStatement("SELECT * FROM user WHERE email = ? AND active = 1 ");
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next())
                u = new User(rs.getString("email"), rs.getString("name"), rs.getString("surname"), rs.getBoolean("role"), rs.getBoolean("active"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return u;
    }

    public int addProfessor(String serialNumber, String name, String surname, String imageUrl) {
        Connection conn = openConnection();

        PreparedStatement ps = null;
        int res = 1;
        try {
            ps = conn.prepareStatement("INSERT INTO professor (serialNumber, name, surname, imageUrl) VALUES (?, ?, ?, ?)");
            ps.setString(1, serialNumber);
            ps.setString(2, name);
            ps.setString(3, surname);
            ps.setString(4, imageUrl);

            ps.execute();
        } catch (SQLIntegrityConstraintViolationException e) {
            res = 0;
        } catch (SQLException e) {
            e.printStackTrace();
            res = -1;
        } finally {
            closeConnection(conn);
        }

        return res;
    }

    public ArrayList<Professor> getProfessors() {
        Connection conn = openConnection();

        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Professor> professors = new ArrayList<Professor>();

        try {
            ps = conn.prepareStatement("SELECT * FROM professor WHERE active = 1 ");
            rs = ps.executeQuery();

            while (rs.next()) {
                professors.add(new Professor(rs.getString("serialNumber"), rs.getString("name"), rs.getString("surname"), rs.getString("imageUrl"), rs.getBoolean("active")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return professors;
    }

    public ArrayList<Professor> searchProfessor(String searchField) {
        Connection conn = openConnection();

        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Professor> professors = new ArrayList<Professor>();

        try {
            ps = conn.prepareStatement("SELECT * FROM professor p WHERE (LOWER(CONCAT(p.name,' ', p.surname)) LIKE ? OR LOWER(CONCAT(p.surname,' ', p.name)) LIKE ? OR LOWER(p.serialNumber) LIKE ?) AND p.active = 1; ");
            ps.setString(1, "%" + searchField.toLowerCase() + "%");
            ps.setString(2, "%" + searchField.toLowerCase() + "%");
            ps.setString(3, "%" + searchField.toLowerCase() + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                professors.add(new Professor(rs.getString("serialNumber"), rs.getString("name"), rs.getString("surname"), rs.getString("imageUrl"), rs.getBoolean("active")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return professors;
    }

    public ArrayList<Professor> getMostRequestedProfessor() {
        Connection conn = openConnection();

        ArrayList<Professor> mostRequestedProfessor = new ArrayList<Professor>();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT COUNT(r.idRepetition) as nRepetition, p.serialNumber, p.name, p.surname, p.imageUrl, p.active FROM professor p JOIN teaching t ON p.serialNumber = t.serialNumber LEFT JOIN repetition r ON t.idTeaching = r.idTeaching AND p.active = 1 GROUP BY p.serialNumber ORDER BY nRepetition DESC LIMIT 4;");
            rs = ps.executeQuery();
            while (rs.next()) {
                mostRequestedProfessor.add(new Professor(rs.getString("serialNumber"), rs.getString("name"), rs.getString("surname"), rs.getString("imageUrl"), rs.getBoolean("active")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return mostRequestedProfessor;
    }

    public int updateProfessor(String oldSerialNumber, String newSerialNumber, String name, String surname) {
        Connection conn = openConnection();
        int res = 1;
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("UPDATE professor SET serialNumber = ?, name = ?, surname = ? WHERE serialNumber = ? AND active = 1 ");
            ps.setString(1, newSerialNumber);
            ps.setString(2, name);
            ps.setString(3, surname);
            ps.setString(4, oldSerialNumber);

            ps.execute();
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
            res = 0;
        } catch (SQLException e) {
            e.printStackTrace();
            res = -1;
        } finally {
            closeConnection(conn);
        }

        return res;
    }

    public Professor getProfessorBySerialNumber(String serialNumber) {
        Professor p = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = openConnection();
        try {
            ps = conn.prepareStatement("SELECT * FROM professor WHERE serialNumber = ? AND active = 1 ");
            ps.setString(1, serialNumber);
            rs = ps.executeQuery();
            if (rs.next())
                p = new Professor(rs.getString("serialNumber"), rs.getString("name"), rs.getString("surname"), rs.getString("imageUrl"), rs.getBoolean("active"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return p;
    }

    public void deleteProfessor(String serialNumber) {
        Connection conn = openConnection();

        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("UPDATE professor SET active = FALSE WHERE serialNUmber = ?");
            ps.setString(1, serialNumber);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }
    }

    public ArrayList<Teaching> getTeachings() {
        ArrayList<Teaching> teachings = new ArrayList<Teaching>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = openConnection();

        try {
            ps = conn.prepareStatement("SELECT t.idTeaching, t.active as teachingActive, " +
                    "p.serialNumber, p.name, p.surname, p.imageUrl, p.active as professorActive, " +
                    "c.idCourse, c.title, c.iconUrl, c.active as courseActive " +
                    "FROM teaching t " +
                    "JOIN professor p ON t.serialNumber = p.serialNumber " +
                    "JOIN course c ON t.idCourse = c.idCourse " +
                    "WHERE t.active = 1 AND p.active = 1 AND c.active = 1");
            rs = ps.executeQuery();
            while (rs.next())
                teachings.add(new Teaching(rs.getInt("idTeaching"),
                        new Professor(rs.getString("serialNumber"), rs.getString("name"), rs.getString("surname"), rs.getString("imageUrl"), rs.getBoolean("professorActive")),
                        new Course(rs.getInt("idCourse"), rs.getString("title"), rs.getString("iconUrl"), rs.getBoolean("courseActive")),
                        rs.getBoolean("teachingActive")));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return teachings;
    }

    public ArrayList<Teaching> searchTeachings(String searchField) {
        Connection conn = openConnection();

        ArrayList<Teaching> teachings = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT t.idTeaching, t.active as teachingActive, " +
                    "p.serialNumber, p.name, p.surname, p.imageUrl, p.active as professorActive, " +
                    "c.idCourse, c.title, c.iconUrl, c.active as courseActive " +
                    "FROM teaching t " +
                    "JOIN professor p ON t.serialNumber = p.serialNumber " +
                    "JOIN course c ON t.idCourse = c.idCourse " +
                    "WHERE t.active = 1 AND p.active = 1 AND c.active = 1 AND (LOWER(CONCAT(p.name,' ', p.surname)) LIKE ? OR LOWER(CONCAT(p.surname, ' ', p.name)) LIKE ? OR LOWER(c.title) LIKE ?)");
            ps.setString(1, "%" + searchField.toLowerCase() + "%");
            ps.setString(2, "%" + searchField.toLowerCase() + "%");
            ps.setString(3, "%" + searchField.toLowerCase() + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                teachings.add(new Teaching(rs.getInt("idTeaching"),
                        new Professor(rs.getString("serialNumber"), rs.getString("name"), rs.getString("surname"), rs.getString("imageUrl"), rs.getBoolean("professorActive")),
                        new Course(rs.getInt("idCourse"), rs.getString("title"), rs.getString("iconUrl"), rs.getBoolean("courseActive")),
                        rs.getBoolean("teachingActive")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return teachings;
    }

    public int addTeaching(String serialNumber, int idCourse) {
        Connection conn = openConnection();
        PreparedStatement ps = null;
        int res = 1;
        try {
            ps = conn.prepareStatement("INSERT INTO teaching (serialNumber, idCourse) VALUES (?,?)");
            ps.setString(1, serialNumber);
            ps.setInt(2, idCourse);
            ps.execute();
        } catch (SQLIntegrityConstraintViolationException e) {
            res = 0;
        } catch (SQLException e) {
            res = -1;
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return res;
    }

    public int updateTeaching(int idTeaching, String serialNumber, int idCourse) {
        Connection conn = openConnection();
        PreparedStatement ps = null;
        int res = 1;
        try {
            ps = conn.prepareStatement("UPDATE teaching SET serialNumber = ?, idCourse = ? WHERE idTeaching = ? AND active = 1 ");
            ps.setString(1, serialNumber);
            ps.setInt(2, idCourse);
            ps.setInt(3, idTeaching);
            ps.execute();
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
            res = 0;
        } catch (SQLException e) {
            res = -1;
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return res;
    }

    public int deleteTeaching(int idTeaching) {
        Connection conn = openConnection();
        PreparedStatement ps = null;
        int res = 1;
        try {
            ps = conn.prepareStatement("UPDATE teaching SET active = FALSE WHERE idTeaching = ?");
            ps.setInt(1, idTeaching);
            ps.execute();
        } catch (SQLException e) {
            res = -1;
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return res;
    }

    public Teaching getTeachingById(int idTeaching) {
        Teaching t = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = openConnection();
        try {
            ps = conn.prepareStatement("SELECT t.idTeaching, t.active as teachingActive, " +
                    "c.idCourse, c.title, c.iconUrl, c.active as courseActive, " +
                    "p.serialNumber, p.name, p.surname, p.imageUrl, p.active as professorActive " +
                    "FROM teaching t JOIN course c ON t.idCourse = c.idCourse JOIN professor p ON t.serialNumber = p.serialNumber " +
                    "WHERE idTeaching = ? AND t.active = 1;");
            ps.setInt(1, idTeaching);
            rs = ps.executeQuery();
            if (rs.next())
                t = new Teaching(rs.getInt("idTeaching"),
                        new Professor(rs.getString("serialNumber"), rs.getString("name"), rs.getString("surname"), rs.getString("imageUrl"), rs.getBoolean("professorActive")),
                        new Course(rs.getInt("idCourse"), rs.getString("title"), rs.getString("iconUrl"), rs.getBoolean("courseActive")),
                        rs.getBoolean("teachingActive"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return t;
    }

    public Teaching getTeachingByProfessorAndCourse(String serialNumber, int idCourse) {
        Teaching t = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = openConnection();
        try {
            ps = conn.prepareStatement("SELECT t.idTeaching, t.active as teachingActive, " +
                    "c.idCourse, c.title, c.iconUrl, c.active as courseActive, " +
                    "p.serialNumber, p.name, p.surname, p.imageUrl, p.active as professorActive " +
                    "FROM teaching t " +
                    "JOIN course c ON t.idCourse = c.idCourse " +
                    "JOIN professor p ON t.serialNumber = p.serialNumber " +
                    "WHERE p.serialNumber = ? AND c.idCourse = ? AND t.active = 1 AND c.active = 1 AND p.active = 1");
            ps.setString(1, serialNumber);
            ps.setInt(2, idCourse);
            rs = ps.executeQuery();
            if (rs.next())
                t = new Teaching(rs.getInt("idTeaching"),
                        new Professor(rs.getString("serialNumber"), rs.getString("name"), rs.getString("surname"), rs.getString("imageUrl"), rs.getBoolean("professorActive")),
                        new Course(rs.getInt("idCourse"), rs.getString("title"), rs.getString("iconUrl"), rs.getBoolean("courseActive")),
                        rs.getBoolean("teachingActive"));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return t;
    }

    public Integer getIdTeachingByProfessorAndCourse(String serialNumber, int idCourse) {
        Integer idTeaching = null;

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = openConnection();
        try {
            ps = conn.prepareStatement("SELECT idTeaching FROM teaching WHERE serialNumber = ? AND idCourse = ? AND active = 1");
            ps.setString(1, serialNumber);
            ps.setInt(2, idCourse);
            rs = ps.executeQuery();
            if (rs.next()) {
                idTeaching = rs.getInt("idTeaching");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return idTeaching;
    }

    public ArrayList<Course> getCoursesByProfessor(String serialNumber) {
        Connection conn = openConnection();

        ArrayList<Course> courses = new ArrayList<Course>();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM course c " +
                    "WHERE c.idCourse IN " +
                    "(SELECT t.idCourse FROM teaching t WHERE t.idCourse = c.idCourse AND t.serialNumber = ? AND t.active = true) " +
                    "AND c.active = true; ");
            ps.setString(1, serialNumber);
            rs = ps.executeQuery();
            while(rs.next()) {
                courses.add(new Course(rs.getInt("idCourse"), rs.getString("title"), rs.getString("iconUrl"), rs.getBoolean("active")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return courses;
    }

    public ArrayList<Professor> getProfessorsByCourse(int idCourse) {
        Connection conn = openConnection();

        ArrayList<Professor> professors = new ArrayList<Professor>();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM professor p " +
                    "WHERE p.serialNumber IN " +
                    "(SELECT t.serialNUmber FROM teaching t WHERE t.serialNumber = p.serialNumber AND t.idCourse = ? AND t.active = true) " +
                    "AND p.active = true; ");
            ps.setInt(1, idCourse);
            rs = ps.executeQuery();
            while(rs.next()) {
                professors.add(new Professor(rs.getString("serialNumber"), rs.getString("name"), rs.getString("surname"), rs.getString("imageUrl"), rs.getBoolean("active")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return professors;
    }

    public ArrayList<Teaching> getTeachingsByCourse(int idCourse) {
        Connection conn = openConnection();

        ArrayList<Teaching> teachings = new ArrayList<Teaching>();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT t.idTeaching, t.active as teachingActive, " +
                    "p.serialNumber, p.name, p.surname, p.imageUrl, p.active as professorActive, " +
                    "c.idCourse, c.title, c.iconUrl, c.active as courseActive " +
                    "FROM teaching t " +
                    "JOIN professor p ON t.serialNumber = p.serialNumber " +
                    "JOIN course c ON t.idCourse = c.idCourse " +
                    "WHERE c.idCourse = ? AND t.active = 1 AND p.active = 1 AND c.active = 1; ");
            ps.setInt(1, idCourse);
            rs = ps.executeQuery();
            while (rs.next()) {
                teachings.add(new Teaching(rs.getInt("idTeaching"),
                        new Professor(rs.getString("serialNumber"), rs.getString("name"), rs.getString("surname"), rs.getString("imageUrl"), rs.getBoolean("professorActive")),
                        new Course(rs.getInt("idCourse"), rs.getString("title"), rs.getString("iconUrl"), rs.getBoolean("courseActive")), rs.getBoolean("teachingActive")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return teachings;
    }

    public ArrayList<Teaching> getTeachingsByProfessor(String serialNumber) {
        Connection conn = openConnection();

        ArrayList<Teaching> teachings = new ArrayList<Teaching>();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT t.idTeaching, t.active as teachingActive, " +
                    "p.serialNumber, p.name, p.surname, p.imageUrl, p.active as professorActive, " +
                    "c.idCourse, c.title, c.iconUrl, c.active as courseActive " +
                    "FROM teaching t " +
                    "JOIN professor p ON t.serialNumber = p.serialNumber " +
                    "JOIN course c ON t.idCourse = c.idCourse " +
                    "WHERE p.serialNumber = ? AND t.active = 1 AND p.active = 1 AND c.active = 1; ");
            ps.setString(1, serialNumber);
            rs = ps.executeQuery();
            while (rs.next()) {
                teachings.add(new Teaching(rs.getInt("idTeaching"),
                        new Professor(rs.getString("serialNumber"), rs.getString("name"), rs.getString("surname"), rs.getString("imageUrl"), rs.getBoolean("professorActive")),
                        new Course(rs.getInt("idCourse"), rs.getString("title"), rs.getString("iconUrl"), rs.getBoolean("courseActive")), rs.getBoolean("teachingActive")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return teachings;
    }

    public int addRepetition(String email, int idTeaching, String date, String time) {
        Connection conn = openConnection();

        PreparedStatement ps = null;
        int res = 1;
        try {
            ps = conn.prepareStatement("INSERT INTO repetition (email, idTeaching, date, time) VALUES (?, ?, ?, ?)");
            ps.setString(1, email);
            ps.setInt(2, idTeaching);
            ps.setDate(3, Date.valueOf(date));
            ps.setTime(4, Time.valueOf(time));

            ps.execute();
        } catch (SQLIntegrityConstraintViolationException e) {
            res = 0;
        } catch (SQLException e) {
            e.printStackTrace();
            res = -1;
        } finally {
            closeConnection(conn);
        }

        return res;
    }

    public ArrayList<Repetition> getRepetitions() {
        Connection conn = openConnection();

        ArrayList<Repetition> repetitions = new ArrayList<Repetition>();

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT r.idRepetition, r.state, r.date, r.time, " +
                    "u.email, u.name as userName, u.surname as userSurname, u.role, u.active as userActive, " +
                    "t.idTeaching, t.active as teachingActive, " +
                    "p.serialNumber, p.name as professorName, p.surname as professorSurname, p.imageUrl, p.active as professorActive, " +
                    "c.idCourse, c.title, c.iconUrl, c.active as courseActive " +
                    "FROM repetition r " +
                    "JOIN user u ON r.email = u.email " +
                    "JOIN teaching t ON r.idTeaching = t.idTeaching " +
                    "JOIN professor p ON t.serialNumber = p.serialNumber " +
                    "JOIN course c ON t.idCourse = c.idCourse ORDER BY date,time; ");

            rs = ps.executeQuery();
            while (rs.next()) {
                repetitions.add(new Repetition(rs.getInt("idRepetition"),
                        new User(rs.getString("email"), rs.getString("userName"), rs.getString("userSurname"), rs.getBoolean("role"), rs.getBoolean("userActive")),
                        new Teaching(rs.getInt("idTeaching"), new Professor(rs.getString("serialNumber"), rs.getString("professorName"), rs.getString("professorSurname"), rs.getString("imageUrl"), rs.getBoolean("professorActive")), new Course(rs.getInt("idCourse"), rs.getString("title"), rs.getString("iconUrl"), rs.getBoolean("courseActive")), rs.getBoolean("teachingActive")),
                        rs.getInt("state"), rs.getDate("date"), rs.getTime("time")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return repetitions;
    }

    public Repetition getRepetitionById(int idRepetition) {
        Connection conn = openConnection();

        PreparedStatement ps = null;
        ResultSet rs = null;
        Repetition repetition = null;
        try {
            ps = conn.prepareStatement("SELECT r.idRepetition, r.state, r.date, r.time, " +
                    "u.email, u.name as userName, u.surname as userSurname, u.role, u.active as userActive, " +
                    "t.idTeaching, t.active as teachingActive, " +
                    "p.serialNumber, p.name as professorName, p.surname as professorSurname, p.imageUrl, p.active as professorActive, " +
                    "c.idCourse, c.title, c.iconUrl, c.active as courseActive " +
                    "FROM repetition r " +
                    "JOIN user u ON r.email = u.email " +
                    "JOIN teaching t ON r.idTeaching = t.idTeaching " +
                    "JOIN professor p ON t.serialNumber = p.serialNumber " +
                    "JOIN course c ON t.idCourse = c.idCourse WHERE r.idRepetition = ?");
            ps.setInt(1, idRepetition);

            rs = ps.executeQuery();
            if (rs.next()) {
                repetition = new Repetition(rs.getInt("idRepetition"),
                        new User(rs.getString("email"), rs.getString("userName"), rs.getString("userSurname"), rs.getBoolean("role"), rs.getBoolean("userActive")),
                        new Teaching(rs.getInt("idTeaching"), new Professor(rs.getString("serialNumber"), rs.getString("professorName"), rs.getString("professorSurname"), rs.getString("imageUrl"), rs.getBoolean("professorActive")), new Course(rs.getInt("idCourse"), rs.getString("title"), rs.getString("iconUrl"), rs.getBoolean("courseActive")), rs.getBoolean("teachingActive")),
                        rs.getInt("state"), rs.getDate("date"), rs.getTime("time"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return repetition;
    }

    public Repetition getRepetition(String email, int idTeaching, String date, String time) {
        Connection conn = openConnection();

        PreparedStatement ps = null;
        ResultSet rs = null;
        Repetition repetition = null;
        try {
            ps = conn.prepareStatement("SELECT r.idRepetition, r.state, r.date, r.time, " +
                    "u.email, u.name as userName, u.surname as userSurname, u.role, u.active as userActive, " +
                    "t.idTeaching, t.active as teachingActive, " +
                    "p.serialNumber, p.name as professorName, p.surname as professorSurname, p.imageUrl, p.active as professorActive, " +
                    "c.idCourse, c.title, c.iconUrl, c.active as courseActive " +
                    "FROM repetition r " +
                    "JOIN user u ON r.email = u.email " +
                    "JOIN teaching t ON r.idTeaching = t.idTeaching " +
                    "JOIN professor p ON t.serialNumber = p.serialNumber " +
                    "JOIN course c ON t.idCourse = c.idCourse WHERE u.email = ? AND t.idTeaching = ? AND r.date = ? AND r.time = ? ORDER BY date,time");
            ps.setString(1, email);
            ps.setInt(2, idTeaching);
            ps.setDate(3, Date.valueOf(date));
            ps.setTime(4, Time.valueOf(time));

            rs = ps.executeQuery();
            if (rs.next()) {
                repetition = new Repetition(rs.getInt("idRepetition"),
                        new User(rs.getString("email"), rs.getString("userName"), rs.getString("userSurname"), rs.getBoolean("role"), rs.getBoolean("userActive")),
                        new Teaching(rs.getInt("idTeaching"), new Professor(rs.getString("serialNumber"), rs.getString("professorName"), rs.getString("professorSurname"), rs.getString("imageUrl"), rs.getBoolean("professorActive")), new Course(rs.getInt("idCourse"), rs.getString("title"), rs.getString("iconUrl"), rs.getBoolean("courseActive")), rs.getBoolean("teachingActive")),
                        rs.getInt("state"), rs.getDate("date"), rs.getTime("time"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return repetition;
    }

    public ArrayList<Repetition> getRepetitionsByEmailFromToday(String email) {
        Connection conn = openConnection();

        ArrayList<Repetition> repetitions = new ArrayList<Repetition>();

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT r.idRepetition, r.state, r.date, r.time, " +
                    "u.email, u.name as userName, u.surname as userSurname, u.role, u.active as userActive, " +
                    "t.idTeaching, t.active as teachingActive, " +
                    "p.serialNumber, p.name as professorName, p.surname as professorSurname, p.imageUrl, p.active as professorActive, " +
                    "c.idCourse, c.title, c.iconUrl, c.active as courseActive " +
                    "FROM repetition r " +
                    "JOIN user u ON r.email = u.email " +
                    "JOIN teaching t ON r.idTeaching = t.idTeaching " +
                    "JOIN professor p ON t.serialNumber = p.serialNumber " +
                    "JOIN course c ON t.idCourse = c.idCourse WHERE u.email = ? AND r.date = CURRENT_DATE() ORDER BY date,time");

            ps.setString(1, email);
            rs = ps.executeQuery();
            while (rs.next()) {
                repetitions.add(new Repetition(rs.getInt("idRepetition"),
                        new User(rs.getString("email"), rs.getString("userName"), rs.getString("userSurname"), rs.getBoolean("role"), rs.getBoolean("userActive")),
                        new Teaching(rs.getInt("idTeaching"), new Professor(rs.getString("serialNumber"), rs.getString("professorName"), rs.getString("professorSurname"), rs.getString("imageUrl"), rs.getBoolean("professorActive")), new Course(rs.getInt("idCourse"), rs.getString("title"), rs.getString("iconUrl"), rs.getBoolean("courseActive")), rs.getBoolean("teachingActive")),
                        rs.getInt("state"), rs.getDate("date"), rs.getTime("time")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return repetitions;
    }

    public ArrayList<Repetition> getRepetitionsByEmail(String email) {
        Connection conn = openConnection();

        ArrayList<Repetition> repetitions = new ArrayList<Repetition>();

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT r.idRepetition, r.state, r.date, r.time, " +
                    "u.email, u.name as userName, u.surname as userSurname, u.role, u.active as userActive, " +
                    "t.idTeaching, t.active as teachingActive, " +
                    "p.serialNumber, p.name as professorName, p.surname as professorSurname, p.imageUrl, p.active as professorActive, " +
                    "c.idCourse, c.title, c.iconUrl, c.active as courseActive " +
                    "FROM repetition r " +
                    "JOIN user u ON r.email = u.email " +
                    "JOIN teaching t ON r.idTeaching = t.idTeaching " +
                    "JOIN professor p ON t.serialNumber = p.serialNumber " +
                    "JOIN course c ON t.idCourse = c.idCourse WHERE u.email = ? ORDER BY date,time");

            ps.setString(1, email);
            rs = ps.executeQuery();
            while (rs.next()) {
                repetitions.add(new Repetition(rs.getInt("idRepetition"),
                        new User(rs.getString("email"), rs.getString("userName"), rs.getString("userSurname"), rs.getBoolean("role"), rs.getBoolean("userActive")),
                        new Teaching(rs.getInt("idTeaching"), new Professor(rs.getString("serialNumber"), rs.getString("professorName"), rs.getString("professorSurname"), rs.getString("imageUrl"), rs.getBoolean("professorActive")), new Course(rs.getInt("idCourse"), rs.getString("title"), rs.getString("iconUrl"), rs.getBoolean("courseActive")), rs.getBoolean("teachingActive")),
                        rs.getInt("state"), rs.getDate("date"), rs.getTime("time")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return repetitions;
    }

    public ArrayList<Repetition> getRepetitionsByEmailAndDate(String email, String date) {
        Connection conn = openConnection();

        ArrayList<Repetition> repetitions = new ArrayList<Repetition>();

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT r.idRepetition, r.state, r.date, r.time, " +
                    "u.email, u.name as userName, u.surname as userSurname, u.role, u.active as userActive, " +
                    "t.idTeaching, t.active as teachingActive, " +
                    "p.serialNumber, p.name as professorName, p.surname as professorSurname, p.imageUrl, p.active as professorActive, " +
                    "c.idCourse, c.title, c.iconUrl, c.active as courseActive " +
                    "FROM repetition r " +
                    "JOIN user u ON r.email = u.email " +
                    "JOIN teaching t ON r.idTeaching = t.idTeaching " +
                    "JOIN professor p ON t.serialNumber = p.serialNumber " +
                    "JOIN course c ON t.idCourse = c.idCourse WHERE u.email = ? AND r.date = ? AND r.state <> 2 ORDER BY date,time");

            ps.setString(1, email);
            ps.setDate(2, Date.valueOf(date));
            rs = ps.executeQuery();
            while (rs.next()) {
                repetitions.add(new Repetition(rs.getInt("idRepetition"),
                        new User(rs.getString("email"), rs.getString("userName"), rs.getString("userSurname"), rs.getBoolean("role"), rs.getBoolean("userActive")),
                        new Teaching(rs.getInt("idTeaching"), new Professor(rs.getString("serialNumber"), rs.getString("professorName"), rs.getString("professorSurname"), rs.getString("imageUrl"), rs.getBoolean("professorActive")), new Course(rs.getInt("idCourse"), rs.getString("title"), rs.getString("iconUrl"), rs.getBoolean("courseActive")), rs.getBoolean("teachingActive")),
                        rs.getInt("state"), rs.getDate("date"), rs.getTime("time")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return repetitions;
    }

    public ArrayList<Repetition> getRepetitionsByCourseAndDate(int idCourse, String date) {
        Connection conn = openConnection();

        ArrayList<Repetition> repetitions = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT r.idRepetition, r.state, r.date, r.time, " +
                    "u.email, u.name, u.surname, u.role, u.active as userActive, " +
                    "t.idTeaching, t.active as teachingActive, " +
                    "p.serialNumber, p.name, p.surname, p.imageUrl, p.active as professorActive, " +
                    "c.idCourse, c.title, c.iconUrl, c.active as courseActive " +
                    "FROM repetition r " +
                    "JOIN user u ON r.email = u.email " +
                    "JOIN teaching t ON r.idTeaching = t.idTeaching " +
                    "JOIN professor p ON t.serialNumber = p.serialNumber " +
                    "JOIN course c ON t.idCourse = c.idCourse " +
                    "WHERE t.idCourse = ? AND r.date = ? AND r.state != 2 AND t.active = 1 AND c.active = 1;");

            ps.setInt(1, idCourse);
            ps.setDate(2, Date.valueOf(date));
            rs = ps.executeQuery();
            while (rs.next()) {
                repetitions.add(new Repetition(rs.getInt("idRepetition"),
                        new User(rs.getString("email"), rs.getString("name"), rs.getString("surname"), rs.getBoolean("role"), rs.getBoolean("userActive")),
                        new Teaching(rs.getInt("idTeaching"), new Professor(rs.getString("serialNumber"), rs.getString("name"), rs.getString("surname"), rs.getString("imageUrl"), rs.getBoolean("professorActive")), new Course(rs.getInt("idCourse"), rs.getString("title"), rs.getString("iconUrl"), rs.getBoolean("courseActive")), rs.getBoolean("teachingActive")),
                        rs.getInt("state"), rs.getDate("date"), rs.getTime("time")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return repetitions;
    }
    
    public ArrayList<Repetition> getRepetitionsByProfessorAndDate(String serialNumber, String date) {
        Connection conn = openConnection();

        ArrayList<Repetition> repetitions = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT r.idRepetition, r.state, r.date, r.time, " +
                    "u.email, u.name, u.surname, u.role, u.active as userActive, " +
                    "t.idTeaching, t.active as teachingActive, " +
                    "p.serialNumber, p.name, p.surname, p.imageUrl, p.active as professorActive, " +
                    "c.idCourse, c.title, c.iconUrl, c.active as courseActive " +
                    "FROM repetition r " +
                    "JOIN user u ON r.email = u.email " +
                    "JOIN teaching t ON r.idTeaching = t.idTeaching " +
                    "JOIN professor p ON t.serialNumber = p.serialNumber " +
                    "JOIN course c ON t.idCourse = c.idCourse " +
                    "WHERE t.serialNumber = ? AND r.date = ? AND r.state != 2 AND t.active = 1 AND c.active = 1;");

            ps.setString(1, serialNumber);
            ps.setDate(2, Date.valueOf(date));
            rs = ps.executeQuery();
            while (rs.next()) {
                repetitions.add(new Repetition(rs.getInt("idRepetition"),
                        new User(rs.getString("email"), rs.getString("name"), rs.getString("surname"), rs.getBoolean("role"), rs.getBoolean("userActive")),
                        new Teaching(rs.getInt("idTeaching"), new Professor(rs.getString("serialNumber"), rs.getString("name"), rs.getString("surname"), rs.getString("imageUrl"), rs.getBoolean("professorActive")), new Course(rs.getInt("idCourse"), rs.getString("title"), rs.getString("iconUrl"), rs.getBoolean("courseActive")), rs.getBoolean("teachingActive")),
                        rs.getInt("state"), rs.getDate("date"), rs.getTime("time")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return repetitions;
    }

    public ArrayList<Repetition> getRepetitionsByProfessorCourseAndDate(String serialNumber, int idCourse, String date) {
        Connection conn = openConnection();

        ArrayList<Repetition> repetitions = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT r.idRepetition, r.state, r.date, r.time, " +
                    "u.email, u.name, u.surname, u.role, u.active as userActive, " +
                    "t.idTeaching, t.active as teachingActive, " +
                    "p.serialNumber, p.name, p.surname, p.imageUrl, p.active as professorActive, " +
                    "c.idCourse, c.title, c.iconUrl, c.active as courseActive " +
                    "FROM repetition r " +
                    "JOIN user u ON r.email = u.email " +
                    "JOIN teaching t ON r.idTeaching = t.idTeaching " +
                    "JOIN professor p ON t.serialNumber = p.serialNumber " +
                    "JOIN course c ON t.idCourse = c.idCourse " +
                    "WHERE t.serialNumber = ? AND t.idCourse = ? AND r.date = ? AND r.state != 2 AND t.active = 1 AND c.active = 1;");

            ps.setString(1, serialNumber);
            ps.setInt(2, idCourse);
            ps.setDate(3, Date.valueOf(date));
            rs = ps.executeQuery();
            while (rs.next()) {
                repetitions.add(new Repetition(rs.getInt("idRepetition"),
                        new User(rs.getString("email"), rs.getString("name"), rs.getString("surname"), rs.getBoolean("role"), rs.getBoolean("userActive")),
                        new Teaching(rs.getInt("idTeaching"), new Professor(rs.getString("serialNumber"), rs.getString("name"), rs.getString("surname"), rs.getString("imageUrl"), rs.getBoolean("professorActive")), new Course(rs.getInt("idCourse"), rs.getString("title"), rs.getString("iconUrl"), rs.getBoolean("courseActive")), rs.getBoolean("teachingActive")),
                        rs.getInt("state"), rs.getDate("date"), rs.getTime("time")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return repetitions;
    }

    public Repetition updateRepetition(int idRepetition, int newState) {
        Connection conn = openConnection();

        PreparedStatement ps = null;
        Repetition repetition = null;

        try {
            ps = conn.prepareStatement("UPDATE repetition SET state = ? WHERE idRepetition = ?");
            ps.setInt(1, newState);
            ps.setInt(2, idRepetition);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        repetition = getRepetitionById(idRepetition);
        return repetition;
    }

    private void registerDriver() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Connection openConnection() {
        try {
            return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void closeConnection(Connection conn) {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
