package vn.edu.aptech.hotelmanager.domain.dto;

import lombok.Data;
import vn.edu.aptech.hotelmanager.domain.model.Category;
import vn.edu.aptech.hotelmanager.domain.model.Product;

@Data
public class ProductDTO {
    private Product product;
    private Category category;
}
