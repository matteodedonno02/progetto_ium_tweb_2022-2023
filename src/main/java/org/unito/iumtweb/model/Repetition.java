package org.unito.iumtweb.model;

import java.sql.Date;
import java.sql.Time;

public class Repetition implements Comparable {
    private int idRepetition;
    private User user;
    private Teaching teaching;
    private int state;
    private Date date;
    private Time time;

    public Repetition(int idRepetition, User user, Teaching teaching, int state, Date date, Time time) {
        this.idRepetition = idRepetition;
        this.user = user;
        this.teaching = teaching;
        this.state = state;
        this.date = date;
        this.time = time;
    }

    public Repetition(Teaching teaching, Date date, Time time) {
        this.teaching = teaching;
        this.date = date;
        this.time = time;
    }

    public int getIdRepetition() {
        return idRepetition;
    }

    public void setIdRepetition(int idRepetition) {
        this.idRepetition = idRepetition;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Teaching getTeaching() {
        return teaching;
    }

    public void setTeaching(Teaching teaching) {
        this.teaching = teaching;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Repetition{" +
                "idRepetition=" + idRepetition +
                ", user=" + user +
                ", teaching=" + teaching +
                ", state=" + state +
                ", date=" + date +
                ", time=" + time +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        System.out.println(((Repetition) o).getDate().getTime());
        return 0;
    }
}
