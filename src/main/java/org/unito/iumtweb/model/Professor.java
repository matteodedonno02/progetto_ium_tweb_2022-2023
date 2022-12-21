package org.unito.iumtweb.model;

public class Professor {
    private String serialNumber;
    private String name;
    private String surname;
    private String imageUrl;
    private boolean active;

    public Professor(String serialNumber, String name, String surname, String imageUrl, boolean active) {
        this.serialNumber = serialNumber;
        this.name = name;
        this.surname = surname;
        this.imageUrl = imageUrl;
        this.active = active;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Professor{" +
                "serialNumber='" + serialNumber + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", active=" + active +
                '}';
    }
}
