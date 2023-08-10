package vn.edu.aptech.hotelmanager.controllers;

import vn.edu.aptech.hotelmanager.domain.model.Account;
import vn.edu.aptech.hotelmanager.domain.model.Room;

public interface IRoomController {
    void addNewRoom(Room room);
    void updateRoom(Room room);

    void deleteRoom(Room room);
}
