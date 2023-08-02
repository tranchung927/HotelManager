package vn.edu.aptech.hotelmanager.repo.db.entity;

import lombok.Data;
import vn.edu.aptech.hotelmanager.common.entity.IEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class DistrictEntity implements IEntity {
    private int id;
    private int cityId;
    private String name;

    public DistrictEntity(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.cityId = rs.getInt("city_id");
        this.name = rs.getString("name");
    }
}
