package vn.edu.aptech.hotelmanager.domain.model;

import lombok.Data;

@Data
public class District {
    private long id = -1;
    private long cityId = -1;
    private String name;
}
