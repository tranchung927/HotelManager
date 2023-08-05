package vn.edu.aptech.hotelmanager.domain.model;
import lombok.Data;

import java.text.SimpleDateFormat;
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
    private Position position;
    private long addressId;
    public String getFullName() {
        return firstName + " " + lastName;
    }
    public String getGenderName() {
        return gender.getName();
    }
    public String getDOBFormat() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(dob);
    }
    public long getPositionId() {
        return position.getId();
    }
}
