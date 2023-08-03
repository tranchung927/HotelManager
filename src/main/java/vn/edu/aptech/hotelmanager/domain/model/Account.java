package vn.edu.aptech.hotelmanager.domain.model;

import lombok.Data;

import java.util.Date;

@Data
public class Account  {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Date dob;
    private String code;
    private GENDER_TYPE gender;
    private int status;
    private Date createdAt;
    private Date modifiedAt;
    private String description;
    private String role;
    private String username;
    private String password;
    private long positionId;
    private long addressId;
}
