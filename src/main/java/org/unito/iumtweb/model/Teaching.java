package org.unito.iumtweb.model;

public class Teaching {
    private int idTeaching;
    private Professor professor;
    private Course course;
    private boolean active;

    public Teaching(int idTeaching, Professor professor, Course course, boolean active) {
        this.idTeaching = idTeaching;
        this.professor = professor;
        this.course = course;
        this.active = active;
    }

    public int getIdTeaching() {
        return idTeaching;
    }

    public void setIdTeaching(int idTeaching) {
        this.idTeaching = idTeaching;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Teaching{" +
                "idTeaching=" + idTeaching +
                ", professor=" + professor +
                ", course=" + course +
                ", active=" + active +
                '}';
    }
}
