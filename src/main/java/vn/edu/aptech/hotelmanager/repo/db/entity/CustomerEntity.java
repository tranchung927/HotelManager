package vn.edu.aptech.hotelmanager.repo.db.entity;

import lombok.Data;
import vn.edu.aptech.hotelmanager.common.entity.IEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@Data
public class CustomerEntity implements IEntity {
    private int id;
    private int groupId;
    private int assigneeId;
    private int documentId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Date dob;
    private String code;
    private int sex;//"1: male, 2: female, 3: other"
    private String description;
    private int status;
    private Date createdAt;
    private Date modifiedAt;
    private int flag;
    private int defaultAddressId;

    public CustomerEntity(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.groupId = rs.getInt("group_id");
        this.assigneeId = rs.getInt("assignee_id");
        this.documentId = rs.getInt("document_id");
        this.firstName = rs.getString("first_name");
        this.lastName = rs.getString("last_name");
        this.email = rs.getString("email");
        this.phoneNumber = rs.getString("phone_number");
        this.dob = rs.getDate("dob");
        this.code = rs.getString("code");
        this.sex = rs.getInt("sex");
        this.description = rs.getString("description");
        this.status = rs.getInt("status");
        this.createdAt = rs.getDate("created_at");
        this.modifiedAt = rs.getDate("modified_at");
        this.flag = rs.getInt("flag");
        this.defaultAddressId = rs.getInt("default_address_id");
    }
}
