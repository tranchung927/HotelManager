package vn.edu.aptech.hotelmanager.repo.entity;

import vn.edu.aptech.hotelmanager.common.entity.IEntity;

import java.util.Date;

public class ReceiptEntity implements IEntity {
    private int id;
    private int assignee_id;
    private int  check_in_id;
    private Date created_at;
    private Date modified_at;
}
