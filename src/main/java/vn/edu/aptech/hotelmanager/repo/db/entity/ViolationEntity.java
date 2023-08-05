package vn.edu.aptech.hotelmanager.repo.db.entity;

import lombok.Data;
import vn.edu.aptech.hotelmanager.common.entity.IEntity;
import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class ViolationEntity implements IEntity {
    private int id;
    private String name;
    private String description;
    private double forfeit;

    public ViolationEntity(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.name = rs.getString("name");
        this.description = rs.getString("description");
        this.forfeit = rs.getDouble("forfeit");
    }
}
