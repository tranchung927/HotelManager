package vn.edu.aptech.hotelmanager.repo.converter;

import lombok.NonNull;
import vn.edu.aptech.hotelmanager.domain.model.CheckIn;

import java.sql.ResultSet;

public class CheckInEntityToCheckIn implements IEntityConverter<CheckIn>{
    @Override
    public CheckIn convert(@NonNull ResultSet source) {
        CheckIn checkIn = new CheckIn();
        try {
            checkIn.setId(source.getLong("id"));
            checkIn.setCustomerId(source.getLong("customer_id"));
            checkIn.setRoomId(source.getLong("room_id"));
            checkIn.setCheckInAt(source.getDate("check_in_at"));
            checkIn.setCheckOutAt(source.getDate("check_out_at"));
            checkIn.setStatus(source.getInt("status"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return checkIn;
    }
}
