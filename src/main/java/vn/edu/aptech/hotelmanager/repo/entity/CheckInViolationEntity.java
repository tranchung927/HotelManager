package vn.edu.aptech.hotelmanager.repo.entity;

import vn.edu.aptech.hotelmanager.common.entity.IEntity;

public class CheckInViolationEntity implements IEntity {
    private int id;
    private int check_in_id;
    private int violation_id;
    private int status;
    private double forfeit;//"Default set from violation"
    private String note;
}
