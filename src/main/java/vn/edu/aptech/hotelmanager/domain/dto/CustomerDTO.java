package vn.edu.aptech.hotelmanager.domain.dto;

import lombok.Data;
import vn.edu.aptech.hotelmanager.domain.model.*;

@Data
public class CustomerDTO {
    private Customer customer;
    private Document document;
    private Address address;

    public CustomerDTO() {
        this.customer = new Customer();
        this.document = new Document();
        this.document.setId(customer.getDocumentId());
        this.address = new Address();
        this.address.setId(customer.getAddressId());
    }
}
