package org.unito.iumtweb.db;

import org.unito.iumtweb.model.Course;
import org.unito.iumtweb.model.Professor;
import org.unito.iumtweb.model.User;

import java.sql.*;
import java.util.ArrayList;

public class DAO {
    private String dbUrl;
    private String dbUsername;
    private String dbPassword;
    private Connection conn;

    public DAO(String dbUrl, String dbUsername, String dbPassword) {
        this.dbUrl = dbUrl;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
        registerDriver();
    }

    public ArrayList<Course> getCourses(){
        ArrayList<Course> courses=new ArrayList<Course>();
        Statement s=null;
        ResultSet rs=null;
        openConnection();
        try{
            String query="SELECT * FROM course";
            s=conn.createStatement();
            rs = s.executeQuery(query);
            while(rs.next()) {
                courses.add(new Course(rs.getInt("idCourse"),rs.getString("title"),rs.getBoolean("active")));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        closeConnection();
        closeStatement(s);
        closeResultSet(rs);
        return courses;
    }

    public int addCourse(String courseTitle){
        openConnection();
        PreparedStatement ps = null;
        int res = 1;
        try {
            ps = conn.prepareStatement("INSERT INTO course (title) VALUES (?)");
            ps.setString(1, courseTitle);
            ps.execute();
        } catch (SQLIntegrityConstraintViolationException e) {
            res = 0;
        } catch (SQLException e) {
            res = -1;
            e.printStackTrace();
        } finally {
            closeConnection();
            closePreparedStatement(ps);
            return res;
        }
    }

    public int updateCourse(int idCourse,String title){
        openConnection();
        PreparedStatement ps=null;
        int res=1;
        try{
            ps=conn.prepareStatement("UPDATE course SET title=? WHERE idCourse=?");
            ps.setString(1,title);
            ps.setInt(2,idCourse);
            ps.execute();
        } catch (SQLException e) {
            res = -1;
            e.printStackTrace();
        } finally {
            closeConnection();
            closePreparedStatement(ps);
            return res;
        }
    }

    public int deleteCourse(int idCourse){
        openConnection();
        PreparedStatement ps=null;
        int res=1;
        try{
            ps=conn.prepareStatement("UPDATE course SET active=0 WHERE idCourse=?");
            ps.setInt(1,idCourse);
            ps.execute();
        } catch (SQLException e) {
            res = -1;
            e.printStackTrace();
        } finally {
            closeConnection();
            closePreparedStatement(ps);
            return res;
        }
    }

    private User login(String email, String password){
        openConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        User logged = null;
        try{
            String query = "SELECT * from user WHERE email = ? AND password = SHA2(?,256)";
            ps = conn.prepareStatement(query);
            ps.setString(1,email);
            ps.setString(2,password);
            rs = ps.executeQuery();
            if(rs!=null){
                logged = new User(rs.getString("email"),rs.getString("name"),rs.getString("surname"),rs.getString("password"),rs.getBoolean("role"), rs.getBoolean("active"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        closePreparedStatement(ps);
        closeResultSet(rs);
        closeConnection();
        return logged;
    }

    public ArrayList<User> getUsers(){
        ArrayList<User> users = new ArrayList<>();
        Statement s = null;
        ResultSet rs = null;
        openConnection();

        try{
            String query = "SELECT * FROM utente";
            s = conn.createStatement();
            rs = s.executeQuery(query);
            while(rs.next()){
                users.add(new User(rs.getString("email"),rs.getString("password"),rs.getString("name"),rs.getString("surname"),rs.getBoolean("role"),rs.getBoolean("active")));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        closeStatement(s);
        closeResultSet(rs);
        closeConnection();
        return users;
    }

    public int addUser(String email,  String name, String surname, String password){
        openConnection();
        PreparedStatement ps = null;
        int res = 1;

        try {
            String query = "INSERT INTO user (email,password,name,surname) VALUES (?,SHA2(?,256),?,?)";
            ps = conn.prepareStatement(query);
            ps.setString(1,email);
            ps.setString(2,password);
            ps.setString(3,name);
            ps.setString(4,surname);
            ps.execute();
        }catch (SQLIntegrityConstraintViolationException e){
            e.printStackTrace();
            res = -1;
        }catch (SQLException e){
            e.printStackTrace();
            res = 0;
        }finally {
            closePreparedStatement(ps);
            closeConnection();
            return res;
        }

    }

    public int deleteUser(String email){
        openConnection();
        PreparedStatement ps = null;
        int res = 1;
        try{
            String query = "UPDATE user SET active = 0 WHERE email = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1,email);
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
            res = -1;
        }finally {
            closePreparedStatement(ps);
            closeConnection();
            return res;
        }

    }

    public int updatePassword(String email, String password){
        openConnection();
        PreparedStatement ps = null;
        int res = 1;
        try{
            String query = "UPDATE user SET password = SHA2(?,256) WHERE email = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1,password);
            ps.setString(2,email);
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
            res = -1;
        }finally {
            closePreparedStatement(ps);
            closeConnection();
            return res;
        }


    }

    public int updateUser(String newEmail,String oldEmail,String name, String surname,boolean role){
        openConnection();
        PreparedStatement ps = null;
        int res = 1;
        try{
            String query = "UPDATE user SET email = ?,name = ?, surname = ?, role = ? WHERE email = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1,newEmail);
            ps.setString(2,name);
            ps.setString(3,surname);
            ps.setBoolean(4,role);
            ps.setString(5,oldEmail);
            ps.execute();
        }catch(SQLException e){
            e.printStackTrace();
            res = -1;
        }finally {
            closePreparedStatement(ps);
            closeConnection();
            return res;
        }


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

            while(rs.next()) {
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

    public void updateProfessor(String oldSerialNumber, String newSerialNumber, String name, String surname) {
        openConnection();

        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("UPDATE professor SET serialNumber = ?, name = ?, surname = ? WHERE serialNumber = ?");
            ps.setString(1, newSerialNumber);
            ps.setString(2, name);
            ps.setString(3, surname);
            ps.setString(4, oldSerialNumber);

            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        closePreparedStatement(ps);
        closeConnection();
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
            if(conn != null)
                conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void closeStatement(Statement s) {
        try {
            if(s != null)
                s.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void closePreparedStatement(PreparedStatement ps) {
        try {
            if(ps != null)
                ps.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void closeResultSet(ResultSet rs) {
        try {
            if(rs != null)
                rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
