package vn.edu.aptech.hotelmanager.repo.converter;

import lombok.NonNull;
import vn.edu.aptech.hotelmanager.domain.model.Country;
import java.sql.ResultSet;

public class CountryEntityToCountry implements IEntityConverter<Country> {
    @Override
    public Country convert(@NonNull ResultSet source) {
        Country country = new Country();
        try {
            country.setId(source.getLong("id"));
            country.setName(source.getString("name"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return country;
    }
}
