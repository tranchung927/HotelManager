package vn.edu.aptech.hotelmanager.repo.db.entity;

import lombok.Data;
import vn.edu.aptech.hotelmanager.common.entity.IEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@Data
public class PricePolicyEntity implements IEntity {
    private int id;
    private String name;
    private String code;
    private int status;
    private int isCost;
    private int isInit;
    private Date createdAt;
    private Date modifiedAt;
    private int flag;

    public PricePolicyEntity(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.name = rs.getString("name");
        this.code = rs.getString("code");
        this.status = rs.getInt("status");
        this.isCost = rs.getInt("is_cost");
        this.isInit = rs.getInt("is_init");
        this.createdAt = rs.getDate("created_at");
        this.modifiedAt = rs.getDate("modified_at");
        this.flag = rs.getInt("flag");
    }
}