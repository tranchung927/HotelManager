package vn.edu.aptech.hotelmanager.repo;

import vn.edu.aptech.hotelmanager.domain.dto.CustomerDTO;
import vn.edu.aptech.hotelmanager.domain.model.Customer;
import vn.edu.aptech.hotelmanager.domain.repo.ICustomerRepo;
import vn.edu.aptech.hotelmanager.repo.converter.CustomerEntityToCustomer;
import vn.edu.aptech.hotelmanager.utils.CrudUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepoImpl implements ICustomerRepo {
    @Override
    public List<CustomerDTO> getListCustomer(String searchText) {
        List<CustomerDTO> customers =  new ArrayList<>();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM customers");
            while (resultSet.next()) {
                Customer customer = new CustomerEntityToCustomer().convert(resultSet);
                CustomerDTO customerDTO = new CustomerDTO();
                customerDTO.setCustomer(customer);
                customers.add(customerDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customers;
    }
}
