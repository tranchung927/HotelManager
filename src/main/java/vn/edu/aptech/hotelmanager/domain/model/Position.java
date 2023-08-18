package vn.edu.aptech.hotelmanager.domain.model;

import lombok.Data;

@Data
public class Position {
    private long id;
    private String name;
    private ACCOUNT_ROLE_TYPE role;
}
