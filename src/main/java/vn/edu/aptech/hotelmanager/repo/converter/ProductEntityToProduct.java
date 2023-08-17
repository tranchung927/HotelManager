package vn.edu.aptech.hotelmanager.repo.converter;

import lombok.NonNull;
import vn.edu.aptech.hotelmanager.domain.model.Product;
import vn.edu.aptech.hotelmanager.domain.model.UNIT;

import java.sql.ResultSet;

public class ProductEntityToProduct implements IEntityConverter<Product> {
    @Override
    public Product convert(@NonNull ResultSet source) {
        Product product = new Product();
        try {
            product.setId(source.getLong("id"));
            product.setCategoryId(source.getInt("category_id"));
            product.setName(source.getString("name"));
            product.setDescription(source.getString("description"));
            product.setCreatedAt(source.getDate("created_at"));
            product.setUnit(UNIT.valueOfStatus(source.getString("unit")));
        } catch (Exception e){
            e.printStackTrace();
        }
        return product;
    }

}
