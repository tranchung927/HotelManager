package vn.edu.aptech.hotelmanager.repo.db.entity;

import lombok.Data;
import vn.edu.aptech.hotelmanager.common.entity.IEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@Data
public class CategoryEntity implements IEntity {
    private int id;
    private String name;
    private String code;
    private String description;
    private int status;
    private Date createdAt;
    private Date modifiedAt;
    private int type;//"1: Product, 2: Room"
    private int flag;
    private int parentId;

    public CategoryEntity(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.name = rs.getString("name");
        this.code = rs.getString("code");
        this.description = rs.getString("description");
        this.status = rs.getInt("status");
        this.createdAt = rs.getDate("created_at");
        this.modifiedAt = rs.getDate("modified_at");
        this.type = rs.getInt("type");
        this.flag = rs.getInt("flag");
        this.parentId = rs.getInt("parent_id");
    }
}
