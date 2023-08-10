package vn.edu.aptech.hotelmanager.domain.dto;

import lombok.Data;
import vn.edu.aptech.hotelmanager.domain.model.Address;
import vn.edu.aptech.hotelmanager.domain.model.Customer;
import vn.edu.aptech.hotelmanager.domain.model.Document;

@Data
public class CustomerDTO {
    private Customer customer;
    private Document document;
    private Address address;

    public String getFullName() {
        return customer.getFirstName() + " " + customer.getLastName();
    }
}
