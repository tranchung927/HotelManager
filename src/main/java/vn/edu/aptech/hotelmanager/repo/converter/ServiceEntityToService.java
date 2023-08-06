package vn.edu.aptech.hotelmanager.repo.converter;

import lombok.NonNull;
import vn.edu.aptech.hotelmanager.domain.model.Service;

import java.sql.ResultSet;

public class ServiceEntityToService implements IEntityConverter<Service> {
    @Override
    public Service convert(@NonNull ResultSet source) {
        Service service = new Service();
        try {
            service.setId(source.getLong("id"));
            service.setName(source.getString("name"));
            service.setPrice(source.getDouble("price"));
            service.setStatus(source.getInt("status"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return service;
    }
}
