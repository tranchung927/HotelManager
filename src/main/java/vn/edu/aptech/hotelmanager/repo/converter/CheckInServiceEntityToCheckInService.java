package vn.edu.aptech.hotelmanager.repo.converter;

import lombok.NonNull;
import vn.edu.aptech.hotelmanager.domain.model.CheckInService;

import java.sql.ResultSet;

public class CheckInServiceEntityToCheckInService implements IEntityConverter<CheckInService>{
    @Override
    public CheckInService convert(@NonNull ResultSet source) {
        CheckInService checkInService = new CheckInService();
        try {
            checkInService.setId(source.getLong("id"));
            checkInService.setCheckInId(source.getLong("check_in_id"));
            checkInService.setServiceId(source.getLong("service_id"));
            checkInService.setStatus(source.getInt("status"));
            checkInService.setQuantity(source.getDouble("quantity"));
            checkInService.setPrice(source.getDouble("price"));
            checkInService.setNote(source.getString("note"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return checkInService;
    }
}
