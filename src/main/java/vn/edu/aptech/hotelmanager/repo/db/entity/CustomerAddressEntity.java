package vn.edu.aptech.hotelmanager.repo.db.entity;

import lombok.Data;
import vn.edu.aptech.hotelmanager.common.entity.IEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class CustomerAddressEntity implements IEntity {
    private int id;
    private int customerId;
    private int addressId;

    public CustomerAddressEntity(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.customerId = rs.getInt("customer_id");
        this.addressId = rs.getInt("address_id");
    }
}
