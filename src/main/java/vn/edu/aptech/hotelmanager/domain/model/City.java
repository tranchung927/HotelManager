package vn.edu.aptech.hotelmanager.domain.model;

import lombok.Data;

@Data
public class City {
    private long id;
    private String name;
    private long countryId;
}
