package vn.edu.aptech.hotelmanager.controllers;

import java.util.Arrays;

public class Employee {
    private int id;
    private String name;

    private int age;
    private String address;
    private int phone;
    private String position;
    private String userName;
    private String passWord;

    public Employee() {
    }

    public Employee(int id, String name, int age, String address, int phone, String position, String userName, String passWord) {
        this.id = id;
        this.name = name;

        this.age = age;
        this.address = address;
        this.phone = phone;
        this.position = position;
        this.userName = userName;
        this.passWord = passWord;
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


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + name + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", phone=" + phone +
                ", position='" + position + '\'' +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                '}';
    }
    public String getFirstName(String name){
        String firstName = "";

        String[] splitName = name.split(" ");

        firstName = splitName[0] +" "+splitName[1];

        return firstName;
    }
    public String getLastName(String name){

        String[] splitName = name.split(" ");
        String lastName="";
        lastName = splitName[2] ;



        return lastName;

    }

}
