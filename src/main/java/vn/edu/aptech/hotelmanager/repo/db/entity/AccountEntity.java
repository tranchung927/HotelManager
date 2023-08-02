package vn.edu.aptech.hotelmanager.repo.db.entity;

import lombok.Data;
import vn.edu.aptech.hotelmanager.common.entity.IEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@Data
public class AccountEntity implements IEntity {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Date dob;
    private String code;
    private int sex; // "1: male, 2: female, 3: other"
    private int status;
    private Date createdAt;
    private Date modifiedAt;
    private String description;
    private String role;
    private String username;
    private String password;
    private int positionId;
    private int flag;
    private int addressId;

    public AccountEntity(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.firstName = rs.getString("first_name");
        this.lastName = rs.getString("last_name");
        this.email = rs.getString("email");
        this.phoneNumber = rs.getString("phone_number");
        this.dob = rs.getDate("dob");
        this.code = rs.getString("code");
        this.sex = rs.getInt("sex");
        this.status = rs.getInt("status");
        this.createdAt = rs.getDate("created_at");
        this.modifiedAt = rs.getDate("modified_at");
        this.description = rs.getString("description");
        this.role = rs.getString("role");
        this.username = rs.getString("username");
        this.password = rs.getString("password");
        this.positionId = rs.getInt("position_id");
        this.flag = rs.getInt("flag");
        this.addressId = rs.getInt("address_id");
    }
}
