package vn.edu.aptech.hotelmanager.repo.db.entity;

import lombok.Data;
import vn.edu.aptech.hotelmanager.common.entity.IEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@Data
public class CheckInEntity implements IEntity {
    private int id;
    private int customerId;
    private int roomId;
    private Date checkInAt;
    private Date checkOutAt;
    private int status;//"1: active, 2: inactive"

    public CheckInEntity(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.customerId = rs.getInt("customer_id");
        this.roomId = rs.getInt("room_id");
        this.checkInAt = rs.getDate("check_in_at");
        this.checkOutAt = rs.getDate("check_out_at");
        this.status = rs.getInt("status");
    }
}
