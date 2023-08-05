package vn.edu.aptech.hotelmanager.repo.converter;

import lombok.NonNull;
import vn.edu.aptech.hotelmanager.domain.model.Address;
import vn.edu.aptech.hotelmanager.domain.model.City;
import vn.edu.aptech.hotelmanager.domain.model.Country;
import vn.edu.aptech.hotelmanager.domain.model.District;

import java.sql.ResultSet;

public class AddressEntityToAddress implements IEntityConverter<Address> {
    @Override
    public Address convert(@NonNull ResultSet source) {
        Address address = new Address();
        try {
            address.setId(source.getLong("id"));
            address.setFullAddress(source.getString("full_address"));
            address.setLabel(source.getString("label"));
            address.setCreatedAt(source.getDate("created_at"));
            address.setModifiedAt(source.getDate("modified_at"));
            City city = new City();
            city.setId(source.getLong("city_id"));
            city.setName(source.getString("city_name"));
            city.setCountryId(source.getLong("country_id"));
            District district = new District();
            district.setId(source.getLong("district_id"));
            district.setName(source.getString("district_name"));
            district.setCityId(source.getLong("city_id"));
            Country country = new Country();
            country.setId(source.getLong("country_id"));
            country.setName(source.getString("country_name"));
            address.setCountry(country);
            address.setCity(city);
            address.setDistrict(district);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return address;
    }
}
