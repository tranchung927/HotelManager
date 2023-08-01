package vn.edu.aptech.hotelmanager.repo.entity;

import vn.edu.aptech.hotelmanager.common.entity.IEntity;

import java.util.Date;

public class CheckInEntity implements IEntity {
    private int id;
    private int customer_id;
    private int room_id;
    private Date check_in_at;
    private Date check_out_at;
    private int status;//"1: active, 2: inactive"]
}
