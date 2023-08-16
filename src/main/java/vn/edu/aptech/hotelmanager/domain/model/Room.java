package vn.edu.aptech.hotelmanager.domain.model;

import lombok.Data;

@Data
public class Room {
    private long id;
    private String name;
    private ROOM_STATUS_TYPE status;//"1: Available, 2: Occupied, 3: Repair, 4: Dirty, 5: Reserve"
    private int numberOfBeds;
    private double price;
    private long categoryId;
    public String getStatus(){
        return status.getStatus();
    }
}

