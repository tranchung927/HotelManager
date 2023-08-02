package vn.edu.aptech.hotelmanager.domain.model;

import lombok.Data;

@Data
public class District {
    private long id;
    private long cityId;
    private String name;
}
