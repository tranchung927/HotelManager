package vn.edu.aptech.hotelmanager.repo.entity;

import vn.edu.aptech.hotelmanager.common.entity.IEntity;

import java.util.Date;

public class AccountEntity implements IEntity {
    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String phone_number;
    private Date dob;
    private String code;
    private int sex; // "1: male, 2: female, 3: other"
    private int status;
    private Date created_at;
    private Date modified_at;
    private String description;
    private String role;
    private String username;
    private String password;
    private int position_id;
    private int flag;
    private int address_id;
}
