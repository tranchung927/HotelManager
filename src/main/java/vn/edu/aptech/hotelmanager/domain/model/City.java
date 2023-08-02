package vn.edu.aptech.hotelmanager.domain.model;

import lombok.Data;

@Data
public class City {
    private Long id;
    private String name;
    private Long countryId;
}
