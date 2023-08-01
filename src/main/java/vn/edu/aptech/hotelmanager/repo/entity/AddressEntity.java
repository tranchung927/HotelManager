package vn.edu.aptech.hotelmanager.repo.entity;

import vn.edu.aptech.hotelmanager.common.entity.IEntity;

import java.util.Date;

public class AddressEntity implements IEntity {
    private int id;
    private int country_id;
    private int city_id;
    private int district_id;
    private String full_address;
    private String label;
    private int status;
    private int flag;
    private Date created_at;
    private Date modified_at;
}
