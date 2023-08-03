package vn.edu.aptech.hotelmanager.domain.model;

import lombok.Data;

import java.util.Date;

@Data
public class Category {
    private long id;
    private String name;
    private String code;
    private String description;
    private int status;
    private Date createdAt;
    private Date modifiedAt;
    private CATEGORY_TYPE type;//"1: Product, 2: Room"
    private long parentId;
}
