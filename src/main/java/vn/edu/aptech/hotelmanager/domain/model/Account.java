package vn.edu.aptech.hotelmanager.domain.model;

import java.util.Date;

public class Account {

    private int id;
    private String name;
    private String email;
    private String phone;
    private Date dob;
    private String sex;
    private String position;
    private String userName;
    private String password;

    public Account() {
    }

    public Account(int id, String name, String email, String phone, Date dob, String sex, String position, String userName, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
        this.sex = sex;
        this.position = position;
        this.userName = userName;
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", dob=" + dob +
                ", sex='" + sex + '\'' +
                ", position='" + position + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getFirstName(String name) {
        String firstName = "";

        String[] splitName = name.split(" ");

        firstName = splitName[0] + " " + splitName[1];

        return firstName;
    }

    public String getLastName(String name) {

        String[] splitName = name.split(" ");
        String lastName = "";
        lastName = splitName[2];


        return lastName;

    }
}