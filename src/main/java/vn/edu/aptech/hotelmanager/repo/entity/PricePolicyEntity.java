package vn.edu.aptech.hotelmanager.repo.entity;

import vn.edu.aptech.hotelmanager.common.entity.IEntity;

import java.util.Date;

public class PricePolicyEntity implements IEntity {
    private int id;
    private String name;
    private String code;
    private int status;
    private int is_cost;
    private int is_init;
    private Date created_at;
    private Date modified_at;
    private int flag;
}
