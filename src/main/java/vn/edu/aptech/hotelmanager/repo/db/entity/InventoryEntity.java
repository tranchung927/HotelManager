package vn.edu.aptech.hotelmanager.repo.db.entity;

import lombok.Data;
import vn.edu.aptech.hotelmanager.common.entity.IEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
@Data
public class InventoryEntity implements IEntity {
    private int id;
    private int productId;
    private double availableQuantity;
    private Date createdAt;
    private Date modifiedAt;

    public InventoryEntity(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.productId = rs.getInt("product_id");
        this.availableQuantity = rs.getDouble("available_quantity");
        this.createdAt = rs.getDate("created_at");
        this.modifiedAt = rs.getDate("modified_at");
    }
}
