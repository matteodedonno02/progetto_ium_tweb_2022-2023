package org.unito.iumtweb.model;

public class Course {
    private int idCourse;
    private String title;
    private boolean active;

    public Course(int idCourse, String title, boolean active){
        this.idCourse = idCourse;
        this.title = title;
        this.active = active;
    }

    public int getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(int idCourse) {
        this.idCourse = idCourse;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
