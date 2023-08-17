package vn.edu.aptech.hotelmanager.domain.model;

import lombok.Data;

import java.util.Date;

@Data
public class Customer {
    private long id = -1;
    private long groupId;
    private long assigneeId = -1;
    private long documentId = -1;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Date dob;
    private String code;
    private GENDER_TYPE gender = GENDER_TYPE.MALE;//"1: male, 2: female, 3: other"
    private String description;
    private int status;
    private Date createdAt;
    private Date modifiedAt;
    private long addressId = -1;
}
