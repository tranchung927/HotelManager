package vn.edu.aptech.hotelmanager.repo.converter;

import lombok.NonNull;
import vn.edu.aptech.hotelmanager.domain.model.PricePolicy;

import java.sql.ResultSet;

public class PricePolicyEntityToPricePolicy implements IEntityConverter<PricePolicy>{
    @Override
    public PricePolicy convert(@NonNull ResultSet source) {
        PricePolicy pricePolicy = new PricePolicy();
        try{
            pricePolicy.setId(source.getLong("id"));
            pricePolicy.setInitPrice(source.getInt("init_price"));
            pricePolicy.setCostPrice(source.getInt("cost_price"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return pricePolicy;
    }
}
