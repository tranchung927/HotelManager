package vn.edu.aptech.hotelmanager.domain.repo;

import vn.edu.aptech.hotelmanager.common.repo.IRepo;
import vn.edu.aptech.hotelmanager.domain.dto.CustomerDTO;

import java.util.List;

public interface ICustomerRepo extends IRepo {
    List<CustomerDTO> getListCustomer(String searchText);
}
