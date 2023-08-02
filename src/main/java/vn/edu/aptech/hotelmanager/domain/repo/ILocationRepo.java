package vn.edu.aptech.hotelmanager.domain.repo;

import vn.edu.aptech.hotelmanager.common.repo.IRepo;
import vn.edu.aptech.hotelmanager.domain.model.Address;
import vn.edu.aptech.hotelmanager.domain.model.City;
import vn.edu.aptech.hotelmanager.domain.model.Country;
import vn.edu.aptech.hotelmanager.domain.model.District;

import java.util.List;

public interface ILocationRepo extends IRepo {
    List<Country> getListCountries();
    List<City> getListCitiesByCountryId(Long id);
    List<District> getListDistrictsByCityId(Long id);
    Address getAddressById(Long id);
}
