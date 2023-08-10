package vn.edu.aptech.hotelmanager.domain.model;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.scene.control.Button;
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
    private ACCOUNT_ROLE_TYPE role;
    private String username;
    private String password;
    private long positionId;
    private Position position;
    private long addressId;

    public String getFullName() {
        return firstName + " " + lastName;
    }
    public String getGenderName() {
        return gender.toString();
    }
    public String getDOBFormat() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(dob);
    }
    public String getPositionName() {
        return position.getName();
    }
}
