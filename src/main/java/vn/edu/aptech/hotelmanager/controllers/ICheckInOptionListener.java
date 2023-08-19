package vn.edu.aptech.hotelmanager.controllers;

import vn.edu.aptech.hotelmanager.domain.model.ROOM_STATUS_TYPE;

public interface ICheckInOptionListener {
    void onUpdateStatus(ROOM_STATUS_TYPE status);
    void onCheckIn();
}
