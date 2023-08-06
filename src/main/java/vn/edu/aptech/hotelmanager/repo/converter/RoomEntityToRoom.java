package vn.edu.aptech.hotelmanager.repo.converter;

import lombok.NonNull;
import vn.edu.aptech.hotelmanager.domain.model.ROOM_STATUS_TYPE;
import vn.edu.aptech.hotelmanager.domain.model.Room;

import java.sql.ResultSet;

public class RoomEntityToRoom implements IEntityConverter<Room> {
    @Override
    public Room convert(@NonNull ResultSet source) {
        Room room = new Room();
        try {
            room.setId(source.getLong("id"));
            room.setName(source.getString("name"));
            room.setStatus(ROOM_STATUS_TYPE.valueOfStatus(source.getInt("status")));
            room.setNumberOfBeds(source.getInt("number_of_beds"));
            room.setPrice(source.getDouble("price"));
            room.setCategoryId(source.getLong("category_id"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return room;
    }
}
