package vn.edu.aptech.hotelmanager.repo.entity;

import vn.edu.aptech.hotelmanager.common.entity.IEntity;

public class RoomEntity implements IEntity {

    private String name ;
    private int categoryId;
    private int  status;
    private int numberOfBed;
    private double price;
    private int flag;
    public RoomEntity() {
    }

    public RoomEntity( String name, int categoryId, int status, int numberOfBed, double price, int flag) {
        this.name = name;
        this.categoryId = categoryId;
        this.status = status;
        this.numberOfBed = numberOfBed;
        this.price = price;
        this.flag = flag;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getNumberOfBed() {
        return numberOfBed;
    }

    public void setNumberOfBed(int numberOfBed) {
        this.numberOfBed = numberOfBed;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "RoomEntity{" +
                ", name='" + name + '\'' +
                ", categoryId=" + categoryId +
                ", status=" + status +
                ", numberOfBed=" + numberOfBed +
                ", price=" + price +
                ", flag=" + flag +
                '}';
    }
}

