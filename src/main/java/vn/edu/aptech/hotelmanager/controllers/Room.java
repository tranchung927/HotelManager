package vn.edu.aptech.hotelmanager.controllers;

import vn.edu.aptech.hotelmanager.common.entity.IEntity;

public class Room  {

    private int roomId;
    private String roomName;
    private String roomType;
    private String bedType;
    private double price;
    private String status;
    private int numberOfBed;

    public Room() {
    }

    public Room(int roomId, String roomName, String roomType, String bedType, double price, String status, int numberOfBed) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomType = roomType;
        this.bedType = bedType;
        this.price = price;
        this.status = status;
        this.numberOfBed = numberOfBed;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNumberOfBed() {
        return numberOfBed;
    }

    public void setNumberOfBed(int numberOfBed) {
        this.numberOfBed = numberOfBed;
    }

    @Override
    public String toString() {
        return "RoomEntity{" +
                "roomId=" + roomId +
                ", roomName='" + roomName + '\'' +
                ", roomType='" + roomType + '\'' +
                ", bedType='" + bedType + '\'' +
                ", price=" + price +
                ", status='" + status + '\'' +
                ", numberOfBed=" + numberOfBed +
                '}';
    }


}