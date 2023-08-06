package vn.edu.aptech.hotelmanager.repo.converter;

import lombok.NonNull;
import vn.edu.aptech.hotelmanager.domain.model.CATEGORY_TYPE;
import vn.edu.aptech.hotelmanager.domain.model.Category;

import java.sql.ResultSet;

public class CategoryEntityToCategory implements IEntityConverter<Category> {
    @Override
    public Category convert(@NonNull ResultSet source) {
        Category category = new Category();
        try {
            category.setId(source.getLong("id"));
            category.setName(source.getString("name"));
            category.setCode(source.getString("code"));
            category.setDescription(source.getString("description"));
            category.setStatus(source.getInt("status"));
            category.setCreatedAt(source.getDate("created_at"));
            category.setModifiedAt(source.getDate("modified_at"));
            category.setType(CATEGORY_TYPE.valueOfStatus(source.getInt("type")));
            category.setParentId(source.getLong("parent_id"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return category;
    }
}
