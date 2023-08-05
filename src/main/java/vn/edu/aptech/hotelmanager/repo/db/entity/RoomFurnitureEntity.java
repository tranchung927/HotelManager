package vn.edu.aptech.hotelmanager.repo.db.entity;

import lombok.Data;
import vn.edu.aptech.hotelmanager.common.entity.IEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class RoomFurnitureEntity implements IEntity {
    private int id;
    private int roomId;
    private int furnitureId;
    private int status;//"1: intact, 2: damage"
    private double quantity;
    private String note;
    public RoomFurnitureEntity(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.roomId = rs.getInt("room_id");
        this.furnitureId = rs.getInt("furniture_id");
        this.status = rs.getInt("status");
        this.quantity = rs.getInt("quantity");
        this.note = rs.getString("note");
    }
}
