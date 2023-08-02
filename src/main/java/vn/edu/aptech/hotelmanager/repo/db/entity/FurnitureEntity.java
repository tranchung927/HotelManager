package vn.edu.aptech.hotelmanager.repo.db.entity;

import lombok.Data;
import vn.edu.aptech.hotelmanager.common.entity.IEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class FurnitureEntity implements IEntity {
    private int id;
    private String name;
    private int status;//"1: active, 2: inactive"]
    private int flag;

    public FurnitureEntity(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.name = rs.getString("name");
        this.status = rs.getInt("status");
        this.flag = rs.getInt("flag");
    }
}
