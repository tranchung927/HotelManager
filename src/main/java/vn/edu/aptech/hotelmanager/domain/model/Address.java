package vn.edu.aptech.hotelmanager.domain.model;

import lombok.Data;

import java.util.Date;

@Data
public class Address {
    private long id;
    private Country country;
    private City city;
    private District district;
    private String fullAddress;
    private String label;
    private Date createdAt;
    private Date modifiedAt;
}
