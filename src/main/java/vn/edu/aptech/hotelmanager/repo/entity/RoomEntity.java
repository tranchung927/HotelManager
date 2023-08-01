package vn.edu.aptech.hotelmanager.repo.entity;

import vn.edu.aptech.hotelmanager.common.entity.IEntity;

public class RoomEntity implements IEntity {
    private int id;
    private String name;
    private int status;//"1: Available, 2: Occupied, 3: Repair, 4: Dirty, 5: Reserve"
    private int number_of_beds;
    private double price;
    private int category_id;
    private int flag;

    public RoomEntity(int id, String name, int status, int number_of_beds, double price, int category_id, int flag) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.number_of_beds = number_of_beds;
        this.price = price;
        this.category_id = category_id;
        this.flag = flag;
    }
}

