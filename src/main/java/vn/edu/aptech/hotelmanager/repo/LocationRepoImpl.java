package vn.edu.aptech.hotelmanager.repo;

import vn.edu.aptech.hotelmanager.domain.model.Address;
import vn.edu.aptech.hotelmanager.domain.model.City;
import vn.edu.aptech.hotelmanager.domain.model.Country;
import vn.edu.aptech.hotelmanager.domain.model.District;
import vn.edu.aptech.hotelmanager.domain.repo.ILocationRepo;
import vn.edu.aptech.hotelmanager.repo.converter.AddressEntityToAddress;
import vn.edu.aptech.hotelmanager.repo.converter.CityEntityToCity;
import vn.edu.aptech.hotelmanager.repo.converter.CountryEntityToCountry;;
import vn.edu.aptech.hotelmanager.repo.converter.DistrictEntityToDistrict;
import vn.edu.aptech.hotelmanager.utils.CrudUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LocationRepoImpl implements ILocationRepo {
    @Override
    public List<Country> getListCountries() {
        List<Country> countryList =  new ArrayList<>();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM countries");
            while (resultSet.next()) {
                countryList.add(new CountryEntityToCountry().convert(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return countryList;
    }

    @Override
    public List<City> getListCitiesByCountryId(Long id) {
        List<City> cityList = new ArrayList<>();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM cities WHERE country_id=?", id);
            while (resultSet.next()) {
                cityList.add(new CityEntityToCity().convert(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cityList;
    }

    @Override
    public List<District> getListDistrictsByCityId(Long id) {
        List<District> districtList =  new ArrayList<>();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM districts WHERE city_id=?", id);
            while (resultSet.next()) {
                districtList.add(new DistrictEntityToDistrict().convert(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return districtList;
    }

    @Override
    public Address getAddressById(Long id) {
        try {
            String sql = "SELECT A.id, A.full_address, A.label, A.created_at, A.modified_at," +
                    "B.id AS country_id, B.name AS country_name," +
                    "C.id AS city_id, C.name AS city_name," +
                    "D.id AS district_id, D.name AS district_name" +
                    "FROM addresses AS A" +
                    "INNER JOIN cities AS C ON A.city_id = C.id" +
                    "INNER JOIN countries AS B ON C.country_id = B.id" +
                    "INNER JOIN districts AS D ON A.district_id = D.id" +
                    "WHERE A.id=?";
            ResultSet resultSet = CrudUtil.execute(sql, id);
            if (resultSet.next()) {
                return new AddressEntityToAddress().convert(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
