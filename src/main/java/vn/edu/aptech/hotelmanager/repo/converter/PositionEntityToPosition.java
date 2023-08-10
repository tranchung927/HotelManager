package vn.edu.aptech.hotelmanager.repo.converter;

import lombok.NonNull;
import vn.edu.aptech.hotelmanager.domain.model.Position;

import java.sql.ResultSet;

public class PositionEntityToPosition implements IEntityConverter<Position>{
    @Override
    public Position convert(@NonNull ResultSet source) {
        Position position = new Position();
        try {
            position.setId(source.getLong("id"));
            position.setName(source.getString("name"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return position;
    }
}
