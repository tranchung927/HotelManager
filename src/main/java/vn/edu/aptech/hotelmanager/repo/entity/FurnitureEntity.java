package vn.edu.aptech.hotelmanager.repo.entity;

import vn.edu.aptech.hotelmanager.common.entity.IEntity;

public class FurnitureEntity implements IEntity {
    private int id;
    private String name;
    private int status;//"1: active, 2: inactive"]
    private int flag;
}
