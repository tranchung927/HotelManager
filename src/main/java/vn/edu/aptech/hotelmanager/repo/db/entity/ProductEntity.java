package vn.edu.aptech.hotelmanager.repo.db.entity;

import lombok.Data;
import vn.edu.aptech.hotelmanager.common.entity.IEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
@Data
public class ProductEntity implements IEntity {
    private int id;
    private int categoryId;
    private String name;
    private String sku;
    private String barcode;
    private String description;
    private int status;
    private String type;
    private Date createdAt;
    private Date modifiedAt;
    private int flag;
    private String unit;
    public ProductEntity(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.categoryId = rs.getInt("category_id");
        this.name = rs.getString("name");
        this.sku = rs.getString("sku");
        this.barcode = rs.getString("barcode");
        this.description = rs.getString("description");
        this.status = rs.getInt("status");
        this.type = rs.getString("type");
        this.createdAt = rs.getDate("created_at");
        this.modifiedAt = rs.getDate("modified_at");
        this.flag = rs.getInt("flag");
        this.unit = rs.getString("unit");
    }
}