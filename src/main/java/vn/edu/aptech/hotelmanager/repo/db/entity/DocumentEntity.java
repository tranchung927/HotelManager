package vn.edu.aptech.hotelmanager.repo.db.entity;

import lombok.Data;
import vn.edu.aptech.hotelmanager.common.entity.IEntity;

import java.sql.ResultSet;
import java.sql.SQLException;

@Data
public class DocumentEntity implements IEntity {
    private int id;
    private int type;//"1: Identity Card, 2: Citizen Identification, 3: Passport"
    private String value;
    private String note;

    public DocumentEntity(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.type = rs.getInt("type");
        this.value = rs.getString("value");
        this.note = rs.getString("note");
    }
}
