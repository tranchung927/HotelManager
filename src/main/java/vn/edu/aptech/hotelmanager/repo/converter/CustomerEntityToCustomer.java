package vn.edu.aptech.hotelmanager.repo.converter;

import lombok.NonNull;
import vn.edu.aptech.hotelmanager.domain.model.Customer;
import vn.edu.aptech.hotelmanager.domain.model.GENDER_TYPE;;import java.sql.ResultSet;

public class CustomerEntityToCustomer implements IEntityConverter<Customer> {
    @Override
    public Customer convert(@NonNull ResultSet source) {
        Customer customer = new Customer();
        try {
            customer.setId(source.getLong("id"));
            customer.setGroupId(source.getLong("group_id"));
            customer.setAssigneeId(source.getLong("assignee_id"));
            customer.setDocumentId(source.getLong("document_id"));
            customer.setFirstName(source.getString("first_name"));
            customer.setLastName(source.getString("last_name"));
            customer.setEmail(source.getString("email"));
            customer.setPhoneNumber(source.getString("phone_number"));
            customer.setDob(source.getDate("dob"));
            customer.setCode(source.getString("code"));
            customer.setGender(GENDER_TYPE.valueOfStatus(source.getInt("sex")));
            customer.setDescription(source.getString("description"));
            customer.setStatus(source.getInt("status"));
            customer.setCreatedAt(source.getDate("created_at"));
            customer.setModifiedAt(source.getDate("modified_at"));
            customer.setDefaultAddressId(source.getLong("default_address_id"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customer;
    }
}
