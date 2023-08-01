package vn.edu.aptech.hotelmanager.repo.entity;

import vn.edu.aptech.hotelmanager.common.entity.IEntity;

import java.util.Date;

public class CustomerEntity implements IEntity {
    private int id;
    private int group_id;
    private int assignee_id;
    private int document_id;
    private String first_name;
    private String last_name;
    private String email;
    private String phone_number;
    private Date dob;
    private String code;
    private int sex;//"1: male, 2: female, 3: other"
    private String description;
    private int status;
    private Date created_at;
    private Date modified_at;
    private int flag;
    private int default_address_id;
}
