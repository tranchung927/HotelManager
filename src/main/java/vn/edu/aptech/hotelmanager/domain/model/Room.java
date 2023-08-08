package vn.edu.aptech.hotelmanager.domain.model;

public class Room {
    private long id;
    private String name;
    private ROOM_STATUS_TYPE status;
    private int numberOfBeds;
    private double price;
    private long categoryId;

    public Room(long id, String name, ROOM_STATUS_TYPE status, int numberOfBeds, double price, long categoryID) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.numberOfBeds = numberOfBeds;
        this.price = price;
        this.categoryId = categoryId;
    }

    public Room() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ROOM_STATUS_TYPE getStatus() {
        return status;
    }

    public void setStatus(ROOM_STATUS_TYPE status) {
        this.status = status;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public void setNumberOfBeds(int numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }
}
