package vn.edu.aptech.hotelmanager.repo.entity;

import vn.edu.aptech.hotelmanager.common.entity.IEntity;

import java.util.Date;

public class InventoryEntity implements IEntity {
    private int id;
    private int product_id;
    private double available_quantity;
    private Date created_at;
    private Date modified_at;
}
