package vn.edu.aptech.hotelmanager.repo.db.entity;

import lombok.Data;
import vn.edu.aptech.hotelmanager.common.entity.IEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@Data
public class AddressEntity implements IEntity {
    private int id;
    private int countryId;
    private int cityId;
    private int districtId;
    private String fullAddress;
    private String label;
    private int status;
    private int flag;
    private Date createdAt;
    private Date modifiedAt;

    public AddressEntity(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.countryId = rs.getInt("country_id");
        this.cityId = rs.getInt("city_id");
        this.districtId = rs.getInt("district_id");
        this.fullAddress = rs.getString("full_address");
        this.label = rs.getString("label");
        this.status = rs.getInt("status");
        this.flag = rs.getInt("flag");
        this.createdAt = rs.getDate("created_at");
        this.modifiedAt = rs.getDate("modified_at");
    }
}
