package vn.edu.aptech.hotelmanager.repo.db.entity;

import lombok.Data;
import vn.edu.aptech.hotelmanager.common.entity.IEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class CheckInViolationEntity implements IEntity {
    private int id;
    private int checkInId;
    private int violationId;
    private int status;
    private double forfeit;//"Default set from violation"
    private String note;

    public CheckInViolationEntity(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.checkInId = rs.getInt("check_in_id");
        this.violationId = rs.getInt("violation_id");
        this.status = rs.getInt("status");
        this.forfeit = rs.getDouble("forfeit");
        this.note = rs.getString("note");
    }
}
