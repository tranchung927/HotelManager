package vn.edu.aptech.hotelmanager.controllers;

import java.util.Date;

public class Customer {
    private int id;
    private String name;
    private String nationId;
    private String room;
    private Date checkIn;
    private Date checkOut;

    private Double totalPayment;

    private String employee;

    public Customer() {
    }

    public Customer(int id, String name, String nationId, String room, Date checkIn, Date checkOut, Double totalPayment, String employee) {
        this.id = id;
        this.name = name;
        this.nationId = nationId;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalPayment = totalPayment;
        this.employee = employee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationId() {
        return nationId;
    }

    public void setNationId(String nationId) {
        this.nationId = nationId;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public Double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(Double totalPayment) {
        this.totalPayment = totalPayment;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nationId ='" + nationId + '\'' +
                ", room='" + room + '\'' +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", totalPayment=" + totalPayment +
                ", employee='" + employee + '\'' +
                '}';
    }
}
