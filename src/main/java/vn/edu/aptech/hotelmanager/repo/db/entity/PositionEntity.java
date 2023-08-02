package vn.edu.aptech.hotelmanager.repo.db.entity;

import lombok.Data;
import vn.edu.aptech.hotelmanager.common.entity.IEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class PositionEntity implements IEntity {
    private int id;
    private String code;
    private String name;
    private int flag;

    public PositionEntity(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.code = rs.getString("code");
        this.name = rs.getString("name");
        this.flag = rs.getInt("flag");
    }
}
