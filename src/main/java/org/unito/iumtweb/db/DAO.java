package org.unito.iumtweb.db;

import org.unito.iumtweb.model.*;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DAO {
    private String dbUrl;
    private String dbUsername;
    private String dbPassword;
    private Connection conn;

    //TODO: rendere title di course unique nel db , e forse anche l'insieme di idCourse serialNUmber e active in teaching unique, in caso contrario sistemare i catch e la servlet
    public DAO(String dbUrl, String dbUsername, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
        registerDriver();
    }

    public ArrayList<Course> getCourses() {
        ArrayList<Course> courses = new ArrayList<Course>();
        Statement s = null;
        ResultSet rs = null;
        openConnection();
        try {
            String query = "SELECT * FROM course";
            s = conn.createStatement();
            rs = s.executeQuery(query);
            while (rs.next()) {
                courses.add(new Course(rs.getInt("idCourse"), rs.getString("title"), rs.getBoolean("active")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeStatement(s);
        closeResultSet(rs);
        closeConnection();
        return courses;
    }

    public int addCourse(String title) {
        openConnection();
        PreparedStatement ps = null;
        int res = 1;
        try {
            ps = conn.prepareStatement("INSERT INTO course (title) VALUES (?)");
            ps.setString(1, title);
            ps.execute();
        } catch (SQLIntegrityConstraintViolationException e) {
            res = 0;
        } catch (SQLException e) {
            res = -1;
            e.printStackTrace();
        } finally {
            closePreparedStatement(ps);
            closeConnection();
            return res;
        }
    }

    public int updateCourse(int idCourse, String title) {
        openConnection();
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
            closePreparedStatement(ps);
            closeConnection();
            return res;
        }
    }

    public int deleteCourse(int idCourse) {
        openConnection();
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
            closePreparedStatement(ps);
            closeConnection();
            return res;
        }
    }

    public Course getCourseById(int idCourse) {
        Course c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        openConnection();
        try {
            ps = conn.prepareStatement("SELECT * FROM course WHERE idCourse = ?");
            ps.setInt(1, idCourse);
            rs = ps.executeQuery();
            if (rs.next())
                c = new Course(rs.getInt("idCourse"), rs.getString("title"), rs.getBoolean("active"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        closePreparedStatement(ps);
        closeResultSet(rs);
        closeConnection();
        return c;
    }


    public Course getCourseByTitle(String title) {
        Course c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        openConnection();
        try {
            ps = conn.prepareStatement("SELECT * FROM course WHERE title = ?");
            ps.setString(1, title);
            rs = ps.executeQuery();
            if (rs.next())
                c = new Course(rs.getInt("idCourse"), rs.getString("title"), rs.getBoolean("active"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        closePreparedStatement(ps);
        closeResultSet(rs);
        closeConnection();
        return c;
    }

    private User login(String email, String password) {
        openConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        User logged = null;
        try {
            String query = "SELECT * from user WHERE email = ? AND password = SHA2(?,256)";
            ps = conn.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs != null) {
                logged = new User(rs.getString("email"), rs.getString("name"), rs.getString("surname"), rs.getBoolean("role"), rs.getBoolean("active"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closePreparedStatement(ps);
        closeResultSet(rs);
        closeConnection();
        return logged;
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<>();
        Statement s = null;
        ResultSet rs = null;
        openConnection();

        try {
            String query = "SELECT * FROM user";

            s = conn.createStatement();
            rs = s.executeQuery(query);
            while (rs.next()) {
                users.add(new User(rs.getString("email"), rs.getString("name"), rs.getString("surname"), rs.getBoolean("role"), rs.getBoolean("active")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeStatement(s);
        closeResultSet(rs);
        closeConnection();
        return users;
    }

    public int addUser(String email, String name, String surname, String password) {
        openConnection();
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
            closePreparedStatement(ps);
            closeConnection();
            return res;
        }

    }

    public int deleteUser(String email) {
        openConnection();
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
            closePreparedStatement(ps);
            closeConnection();
            return res;
        }

    }

    public int updatePassword(String email, String password) {
        openConnection();
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
            closePreparedStatement(ps);
            closeConnection();
            return res;
        }


    }

    public int updateUser(String newEmail, String oldEmail, String name, String surname, boolean role) {
        openConnection();
        PreparedStatement ps = null;
        int res = 1;
        try {
            String query = "UPDATE user SET email = ?,name = ?, surname = ?, role = ? WHERE email = ?";
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
            closePreparedStatement(ps);
            closeConnection();
            return res;
        }


    }

    public User getUserByEmail(String email) {
        User u = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        openConnection();
        try {
            ps = conn.prepareStatement("SELECT * FROM user WHERE email = ?");
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next())
                u = new User(rs.getString("email"), rs.getString("name"), rs.getString("surname"), rs.getBoolean("role"), rs.getBoolean("active"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        closePreparedStatement(ps);
        closeResultSet(rs);
        closeConnection();
        return u;
    }

    public int addProfessor(String serialNumber, String name, String surname) {
        openConnection();

        PreparedStatement ps = null;
        int res = 1;
        try {
            ps = conn.prepareStatement("INSERT INTO professor (serialNumber, name, surname) VALUES (?, ?, ?)");
            ps.setString(1, serialNumber);
            ps.setString(2, name);
            ps.setString(3, surname);

            ps.execute();
        } catch (SQLIntegrityConstraintViolationException e) {
            res = 0;
        } catch (SQLException e) {
            e.printStackTrace();
            res = -1;
        } finally {
            closePreparedStatement(ps);
            closeConnection();
            return res;
        }
    }

    public ArrayList<Professor> getProfessors() {
        openConnection();

        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Professor> professors = new ArrayList<Professor>();

        try {
            ps = conn.prepareStatement("SELECT * FROM professor");
            rs = ps.executeQuery();

            while (rs.next()) {
                professors.add(new Professor(rs.getString("serialNumber"), rs.getString("name"), rs.getString("surname"), rs.getBoolean("active")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        closePreparedStatement(ps);
        closeResultSet(rs);
        closeConnection();
        return professors;
    }

    public int updateProfessor(String oldSerialNumber, String newSerialNumber, String name, String surname) {
        openConnection();
        int res = 1;
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("UPDATE professor SET serialNumber = ?, name = ?, surname = ? WHERE serialNumber = ?");
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
            closePreparedStatement(ps);
            closeConnection();
            return res;
        }


    }

    public Professor getProfessorBySerialNumber(String serialNumber) {
        Professor p = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        openConnection();
        try {
            ps = conn.prepareStatement("SELECT * FROM professor WHERE serialNumber = ?");
            ps.setString(1, serialNumber);
            rs = ps.executeQuery();
            if (rs.next())
                p = new Professor(rs.getString("serialNumber"), rs.getString("name"), rs.getString("surname"), rs.getBoolean("active"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        closePreparedStatement(ps);
        closeResultSet(rs);
        closeConnection();
        return p;
    }

    public void deleteProfessor(String serialNumber) {
        openConnection();

        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("UPDATE professor SET active = FALSE WHERE serialNUmber = ?");
            ps.setString(1, serialNumber);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        closePreparedStatement(ps);
        closeConnection();
    }


    public ArrayList<Teaching> getTeachings() {
        ArrayList<Teaching> teachings = new ArrayList<Teaching>();
        Statement s = null;
        ResultSet rs = null;
        openConnection();

        try {
            String query = "SELECT * FROM teaching";
            s = conn.createStatement();
            rs = s.executeQuery(query);
            while (rs.next())
                teachings.add(new Teaching(rs.getInt("idTeaching"), getProfessorBySerialNumber(rs.getString("serialNumber")), getCourseById(rs.getInt("idCourse")), rs.getBoolean("active")));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeStatement(s);
        closeResultSet(rs);
        closeConnection();
        return teachings;
    }

    public int addTeaching(String serialNumber, int idCourse) {
        openConnection();
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
            closePreparedStatement(ps);
            closeConnection();
            return res;
        }
    }

    public int updateTeaching(int idTeaching, String serialNumber, int idCourse) {
        openConnection();
        PreparedStatement ps = null;
        int res = 1;
        try {
            ps = conn.prepareStatement("UPDATE teaching SET serialNumber = ?, idCourse = ? WHERE idTeaching = ?");
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
            closePreparedStatement(ps);
            closeConnection();
            return res;
        }
    }

    public int deleteTeaching(int idTeaching) {
        openConnection();
        PreparedStatement ps = null;
        int res = 1;
        try {
            ps = conn.prepareStatement("UPDATE teaching SET active = FALSE WHERE idTeaching = ?");
            ps.setInt(1, idTeaching);
            ps.execute();
        } catch (SQLException e) {
            res = -1;
            e.printStackTrace();
        }

        closePreparedStatement(ps);
        closeConnection();
        return res;
    }

    public Teaching getTeachingById(int idTeaching) {
        Teaching t = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        openConnection();
        try {
            ps = conn.prepareStatement("SELECT * FROM teaching WHERE idTeaching = ?");
            ps.setInt(1, idTeaching);
            rs = ps.executeQuery();
            if (rs.next())
                t = new Teaching(rs.getInt("idTeaching"), getProfessorBySerialNumber(rs.getString("serialNumber")), getCourseById(rs.getInt("idCourse")), rs.getBoolean("active"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        closePreparedStatement(ps);
        closeResultSet(rs);
        closeConnection();
        return t;
    }

    public Teaching getTeachingByProfessorCourse(String serialNumber, int idCourse) {
        Teaching t = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        openConnection();
        try {
            ps = conn.prepareStatement("SELECT * FROM teaching WHERE serialNumber = ? AND idCourse = ?");
            ps.setString(1, serialNumber);
            ps.setInt(2, idCourse);
            rs = ps.executeQuery();
            if (rs.next())
                t = new Teaching(rs.getInt("idTeaching"), getProfessorBySerialNumber(rs.getString("serialNumber")), getCourseById(rs.getInt("idCourse")), rs.getBoolean("active"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        closePreparedStatement(ps);
        closeResultSet(rs);
        closeConnection();
        return t;
    }

    public int addRepetition(String email, int idTeaching, String date, String time) {
        openConnection();

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
        }

        closePreparedStatement(ps);
        closeConnection();
        return res;
    }

    public ArrayList<Repetition> getRepetitions() {
        openConnection();

        ArrayList<Repetition> repetitions = new ArrayList<Repetition>();

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM repetition");
            rs = ps.executeQuery();
            while (rs.next()) {
                repetitions.add(new Repetition(rs.getInt("idRepetition"), getUserByEmail(rs.getString("email")), getTeachingById(rs.getInt("idTeaching")), rs.getInt("state"), rs.getDate("date"), rs.getTime("time")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeResultSet(rs);
        closePreparedStatement(ps);
        closeConnection();

        return repetitions;
    }

    public Repetition getRepetitionById(int idRepetition) {
        openConnection();

        PreparedStatement ps = null;
        ResultSet rs = null;
        Repetition repetition = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM repetition WHERE idRepetition = ?");
            ps.setInt(1, idRepetition);

            rs = ps.executeQuery();
            if (rs.next()) {
                repetition = new Repetition(rs.getInt("idRepetition"), getUserByEmail(rs.getString("email")), getTeachingById(rs.getInt("idTeaching")), rs.getInt("state"), rs.getDate("date"), rs.getTime("time"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeResultSet(rs);
        closePreparedStatement(ps);
        closeConnection();
        return repetition;
    }

    public Repetition getRepetition(String email, int idTeaching, String date, String time) {
        System.out.println(time);
        openConnection();

        PreparedStatement ps = null;
        ResultSet rs = null;
        Repetition repetition = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM repetition WHERE email = ? AND idTeaching = ? AND date = ? AND time = ?");
            ps.setString(1, email);
            ps.setInt(2, idTeaching);
            ps.setDate(3, Date.valueOf(date));
            ps.setTime(4, Time.valueOf(time));

            rs = ps.executeQuery();
            if (rs.next()) {
                repetition = new Repetition(rs.getInt("idRepetition"), getUserByEmail(rs.getString("email")), getTeachingById(rs.getInt("idTeaching")), rs.getInt("state"), rs.getDate("date"), rs.getTime("time"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeResultSet(rs);
        closePreparedStatement(ps);
        closeConnection();
        return repetition;
    }

    public ArrayList<Repetition> getRepetitionsByEmail(String email) {
        openConnection();

        ArrayList<Repetition> repetitions = new ArrayList<Repetition>();

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("SELECT * FROM repetition WHERE email = ?");
            ps.setString(1, email);
            rs = ps.executeQuery();
            while (rs.next()) {
                repetitions.add(new Repetition(rs.getInt("idRepetition"), getUserByEmail(rs.getString("email")), getTeachingById(rs.getInt("idTeaching")), rs.getInt("state"), rs.getDate("date"), rs.getTime("time")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeResultSet(rs);
        closePreparedStatement(ps);
        closeConnection();

        return repetitions;
    }

    public boolean updateRepetition(int idRepetition, int newState) {
        openConnection();

        PreparedStatement ps = null;
        boolean res = true;
        try {
            ps = conn.prepareStatement("UPDATE repetition SET state = ? WHERE idRepetition = ?");
            ps.setInt(1, newState);
            ps.setInt(2, idRepetition);
            ps.execute();
        } catch (SQLException e) {
            res = false;
            e.printStackTrace();
        }

        closePreparedStatement(ps);
        closeConnection();
        return res;
    }

    private void registerDriver() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void openConnection() {
        try {
            conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void closeConnection() {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void closeStatement(Statement s) {
        try {
            if (s != null)
                s.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void closePreparedStatement(PreparedStatement ps) {
        try {
            if (ps != null)
                ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void closeResultSet(ResultSet rs) {
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
