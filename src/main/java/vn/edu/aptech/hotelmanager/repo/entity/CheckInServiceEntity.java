package vn.edu.aptech.hotelmanager.repo.entity;

import vn.edu.aptech.hotelmanager.common.entity.IEntity;

public class CheckInServiceEntity implements IEntity {
    private int id;
    private int check_in_id;
    private int service_id;
    private int status;
    private double quantity;
    private double price;//"Default set from service"
    private String note;
}
