package vn.edu.aptech.hotelmanager.repo.converter;

import lombok.NonNull;
import vn.edu.aptech.hotelmanager.domain.model.DOCUMENT_TYPE;
import vn.edu.aptech.hotelmanager.domain.model.Document;

import java.sql.ResultSet;

public class DocumentEntityToDocument implements IEntityConverter<Document> {
    @Override
    public Document convert(@NonNull ResultSet source) {
        Document document = new Document();
        try {
            document.setId(source.getLong("id"));
            document.setValue(source.getString("value"));
            document.setType(DOCUMENT_TYPE.valueOfStatus(source.getInt("type")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return document;
    }
}
