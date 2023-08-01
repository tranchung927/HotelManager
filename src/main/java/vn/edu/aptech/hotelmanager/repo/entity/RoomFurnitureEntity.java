package vn.edu.aptech.hotelmanager.repo.entity;

import vn.edu.aptech.hotelmanager.common.entity.IEntity;

public class RoomFurnitureEntity implements IEntity {
    private int id;
    private int room_id;
    private int furniture_id;
    private int status;//"1: intact, 2: damage"
    private double quantity;
    private String note;
}
