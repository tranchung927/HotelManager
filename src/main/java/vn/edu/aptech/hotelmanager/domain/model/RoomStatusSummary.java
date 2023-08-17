package vn.edu.aptech.hotelmanager.domain.model;

import lombok.Data;

@Data
public class RoomStatusSummary {
    private ROOM_STATUS_TYPE status;
    private int count = 0;
}
