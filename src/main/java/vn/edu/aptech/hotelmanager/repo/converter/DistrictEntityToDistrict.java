package vn.edu.aptech.hotelmanager.repo.converter;

import lombok.NonNull;
import vn.edu.aptech.hotelmanager.domain.model.District;

import java.sql.ResultSet;

public class DistrictEntityToDistrict implements IEntityConverter<District> {
    @Override
    public District convert(@NonNull ResultSet source) {
        District district = new District();
        try {
            district.setId(source.getLong("id"));
            district.setName(source.getString("name"));
            district.setCityId(source.getLong("city_id"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return district;
    }
}