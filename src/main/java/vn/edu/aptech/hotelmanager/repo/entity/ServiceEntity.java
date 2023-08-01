package vn.edu.aptech.hotelmanager.repo.entity;

import vn.edu.aptech.hotelmanager.common.entity.IEntity;

public class ServiceEntity implements IEntity {
    private int id;
    private String name;
    private double price;
    private int status; //"1: active, 2: inactive"
    private int flag;
}
