package vn.edu.aptech.hotelmanager.domain.model;

import lombok.Data;

@Data
public class Service {
    private long id;
    private String name;
    private double price;
    private long status; //"1: active, 2: inactive"
}
