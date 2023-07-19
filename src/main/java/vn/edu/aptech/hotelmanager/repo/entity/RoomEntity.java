package vn.edu.aptech.hotelmanager.repo.entity;

import vn.edu.aptech.hotelmanager.common.entity.IEntity;

public class RoomEntity implements IEntity {

    private String roomNumber;
    private int typeId;
    private String roomStatus;

    public RoomEntity() {
    }

    public RoomEntity(String roomNumber, int typeId, String roomStatus) {
        this.setRoomNumber(roomNumber);
        this.setTypeId(typeId);
        this.setRoomStatus(roomStatus);
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomNumber='" + roomNumber + '\'' +
                ", typeId=" + typeId +
                ", roomStatus='" + roomStatus + '\'' +
                '}';
    }
}

