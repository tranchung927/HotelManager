package vn.edu.aptech.hotelmanager.repo.db.entity;

import lombok.Data;
import vn.edu.aptech.hotelmanager.common.entity.IEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class CheckInServiceEntity implements IEntity {
    private int id;
    private int checkInId;
    private int serviceId;
    private int status;
    private double quantity;
    private double price;//"Default set from service"
    private String note;

    public CheckInServiceEntity(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.checkInId = rs.getInt("check_in_id");
        this.serviceId = rs.getInt("service_id");
        this.status = rs.getInt("status");
        this.quantity = rs.getDouble("quantity");
        this.price = rs.getDouble("price");
        this.note = rs.getString("note");
    }
}
