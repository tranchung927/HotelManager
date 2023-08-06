package vn.edu.aptech.hotelmanager.repo.converter;

import lombok.NonNull;
import vn.edu.aptech.hotelmanager.common.converter.IConverter;
import vn.edu.aptech.hotelmanager.domain.model.City;

import java.sql.ResultSet;

public class CityEntityToCity implements IConverter<ResultSet, City> {
    @Override
    public City convert(@NonNull ResultSet source) {
        City city = new City();
        try {
            city.setId(source.getLong("id"));
            city.setName(source.getString("name"));
            city.setCountryId(source.getLong("country_id"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return city;
    }
}