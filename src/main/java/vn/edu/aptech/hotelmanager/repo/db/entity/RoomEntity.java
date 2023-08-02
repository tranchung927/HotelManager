package vn.edu.aptech.hotelmanager.repo.db.entity;

import lombok.Data;
import vn.edu.aptech.hotelmanager.common.entity.IEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class RoomEntity implements IEntity {
    private int id;
    private String name;
    private int status;//"1: Available, 2: Occupied, 3: Repair, 4: Dirty, 5: Reserve"
    private int numberOfBeds;
    private double price;
    private int categoryId;
    private int flag;
    public RoomEntity(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.name = rs.getString("name");
        this.status = rs.getInt("status");
        this.numberOfBeds = rs.getInt("number_of_beds");
        this.price = rs.getDouble("price");
        this.categoryId = rs.getInt("category_id");
        this.flag = rs.getInt("flag");
    }
}

