package vn.edu.aptech.hotelmanager.domain.model;

import lombok.Data;

@Data
public class City {
    private long id = -1;
    private String name;
    private long countryId = -1;
}
