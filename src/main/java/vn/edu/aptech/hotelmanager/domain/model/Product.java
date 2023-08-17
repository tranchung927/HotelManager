package vn.edu.aptech.hotelmanager.domain.model;

import lombok.Data;

import java.util.Date;

@Data
public class Product {
    private long id;
    private long categoryId;
    private String name;
    private String description;
    private Date createdAt;
    private UNIT_TYPE unit;
    private Double quantity;
    private Double inputPrice;
    private Category category;
    private PricePolicy pricePolicy;
    private Inventory inventory;
    private Date dateInput;
    private String UserImport;
    private int status;
    public Double getPriceInit(){
        return pricePolicy.getInitPrice();
    }
    public Double getQuantityAvailable(){
        return inventory.getAvailableQuantity();
    }
    public Double getPriceCost(){
        return pricePolicy.getCostPrice();
    }
}
