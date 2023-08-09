package vn.edu.aptech.hotelmanager.repo.converter;

import lombok.NonNull;
import vn.edu.aptech.hotelmanager.domain.model.Inventory;
import vn.edu.aptech.hotelmanager.domain.model.PricePolicy;

import java.sql.ResultSet;

public class InventoryEntityToInventory implements IEntityConverter<Inventory>{
    @Override
    public Inventory convert(@NonNull ResultSet source) {
       Inventory inventory = new Inventory();
       try{
           inventory.setId(source.getLong("id"));
           inventory.setAvailableQuantity(source.getDouble("available_quantity"));
       }catch (Exception e){
           e.printStackTrace();
       }
       return inventory;
    }
}
