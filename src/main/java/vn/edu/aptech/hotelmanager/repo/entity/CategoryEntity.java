package vn.edu.aptech.hotelmanager.repo.entity;

import vn.edu.aptech.hotelmanager.common.entity.IEntity;

import java.util.Date;

public class CategoryEntity implements IEntity {
    private int id;
    private String name;
    private String code;
    private String description;
    private int status;
    private Date created_at;
    private Date modified_at;
    private int type;//"1: Product, 2: Room"
    private int flag;
    private int parent_id;
}
