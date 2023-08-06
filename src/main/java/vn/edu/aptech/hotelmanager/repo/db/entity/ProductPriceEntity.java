package vn.edu.aptech.hotelmanager.repo.db.entity;

import lombok.Data;
import vn.edu.aptech.hotelmanager.common.entity.IEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class ProductPriceEntity implements IEntity {
    private int id;
    private int productId;
    private int pricePolicyId;
    private double value;
    public ProductPriceEntity(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.productId = rs.getInt("product_id");
        this.pricePolicyId = rs.getInt("price_policy_id");
        this.value = rs.getDouble("value");
    }
}
