package vn.edu.aptech.hotelmanager.repo;

import vn.edu.aptech.hotelmanager.domain.dto.CustomerDTO;
import vn.edu.aptech.hotelmanager.domain.model.Address;
import vn.edu.aptech.hotelmanager.domain.model.Customer;
import vn.edu.aptech.hotelmanager.domain.model.Document;
import vn.edu.aptech.hotelmanager.domain.repo.ICustomerRepo;
import vn.edu.aptech.hotelmanager.repo.converter.CustomerEntityToCustomer;
import vn.edu.aptech.hotelmanager.repo.converter.DocumentEntityToDocument;
import vn.edu.aptech.hotelmanager.utils.CrudUtil;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
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
                customerDTO.getDocument().setId(customer.getDocumentId());
                customerDTO.getAddress().setId(customer.getAddressId());
                customers.add(customerDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public CustomerDTO createOrUpdate(CustomerDTO customerDTO) throws Exception {
        Address address = customerDTO.getAddress();
        ResultSet addressRst = CrudUtil.execute("SELECT * FROM addresses WHERE id=?", address.getId());
        if (addressRst.next()) {
            String sql = "UPDATE addresses SET country_id =?, city_id = ?, district_id = ?, full_address = ? WHERE addresses.id = ?";
            CrudUtil.execute(sql, address.getCountry().getId(),address.getCity().getId(), address.getDistrict().getId(), address.getFullAddress(), address.getId());
        } else {
            String sql = "INSERT INTO addresses(country_id,city_id,district_id,full_address) VALUES (?,?,?,?)";
            CrudUtil.execute(sql, address.getCountry().getId(), address.getCity().getId(), address.getDistrict().getId(), address.getFullAddress());
            ResultSet newAddressRst = CrudUtil.execute("SELECT * FROM addresses ORDER BY id DESC LIMIT 1");
            if (newAddressRst.next()) {
                customerDTO.getCustomer().setAddressId(newAddressRst.getLong("id"));
                customerDTO.getAddress().setId(newAddressRst.getLong("id"));
            }
        }

        Document document = customerDTO.getDocument();
        ResultSet documentRst = CrudUtil.execute("SELECT * FROM documents WHERE id=?", document.getId());
        if (documentRst.next()) {
            String sql = "UPDATE documents SET type = ?, value = ? WHERE documents.id = ?";
            CrudUtil.execute(sql, document.getType().toStatus(), document.getValue(), document.getId());
        } else {
            String sql = "INSERT INTO documents(type,value) VALUES (?,?)";
            CrudUtil.execute(sql, document.getType().toStatus(), document.getValue());
            ResultSet newDocumentRst = CrudUtil.execute("SELECT * FROM documents ORDER BY id DESC LIMIT 1");
            if (newDocumentRst.next()) {
                customerDTO.getCustomer().setDocumentId(newDocumentRst.getLong("id"));
                customerDTO.getDocument().setId(newDocumentRst.getLong("id"));
            }
        }

        ResultSet customerRst = CrudUtil.execute("SELECT * FROM customers WHERE id=?", customerDTO.getCustomer().getId());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        if (customerRst.next()) {
            String sql = "UPDATE customers SET first_name = ?, last_name = ?," +
                    " email = ?, phone_number = ?, dob = ?, sex = ?," +
                    " document_id = ?, address_id = ?" +
                    " WHERE customers.id = ?";
            CrudUtil.execute(sql, customerDTO.getCustomer().getFirstName(),
                    customerDTO.getCustomer().getLastName(),
                    customerDTO.getCustomer().getEmail(),
                    customerDTO.getCustomer().getPhoneNumber(),
                    java.sql.Date.valueOf(format.format(customerDTO.getCustomer().getDob())),
                    customerDTO.getCustomer().getGender().toStatus(),
                    customerDTO.getCustomer().getDocumentId(),
                    customerDTO.getCustomer().getAddressId(),
                    customerDTO.getCustomer().getId());
        } else {
            String sql = "INSERT INTO customers(first_name,last_name,email,phone_number,dob,sex,document_id,address_id)" +
                    " VALUES (?,?,?,?,?,?,?,?)";
            CrudUtil.execute(sql, customerDTO.getCustomer().getFirstName(),
                    customerDTO.getCustomer().getLastName(),
                    customerDTO.getCustomer().getEmail(),
                    customerDTO.getCustomer().getPhoneNumber(),
                    java.sql.Date.valueOf(format.format(customerDTO.getCustomer().getDob())),
                    customerDTO.getCustomer().getGender().toStatus(),
                    customerDTO.getCustomer().getDocumentId(),
                    customerDTO.getCustomer().getAddressId());
            ResultSet newCustomerRst = CrudUtil.execute("SELECT * FROM accounts ORDER BY id DESC LIMIT 1");
            if (newCustomerRst.next()) {
                customerDTO.getCustomer().setId(newCustomerRst.getLong("id"));
            }
        }

        return customerDTO;
    }

    @Override
    public Document getDocumentById(long id) {
        try {
            String sql = "SELECT * FROM documents WHERE id=?";
            ResultSet resultSet = CrudUtil.execute(sql, id);
            if (resultSet.next()) {
                return new DocumentEntityToDocument().convert(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
