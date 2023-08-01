package vn.edu.aptech.hotelmanager.repo.entity;

import vn.edu.aptech.hotelmanager.common.entity.IEntity;

import java.util.Date;

public class ProductEntity implements IEntity {
    private int id;
    private int category_id;
    private String name;
    private String sku;
    private String barcode;
    private String description;
    private int status;
    private String type;
    private Date created_at;
    private Date modified_at;
    private int flag;
    private String unit;
}
