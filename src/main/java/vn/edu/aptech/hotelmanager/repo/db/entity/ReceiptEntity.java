package vn.edu.aptech.hotelmanager.repo.db.entity;

import lombok.Data;
import vn.edu.aptech.hotelmanager.common.entity.IEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@Data
public class ReceiptEntity implements IEntity {
    private int id;
    private int assigneeId;
    private int  checkInId;
    private Date createdAt;
    private Date modifiedAt;

    public ReceiptEntity(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.assigneeId = rs.getInt("assignee_id");
        this.checkInId = rs.getInt("check_in_id");
        this.createdAt = rs.getDate("created_at");
        this.modifiedAt = rs.getDate("modified_at");
    }
}
