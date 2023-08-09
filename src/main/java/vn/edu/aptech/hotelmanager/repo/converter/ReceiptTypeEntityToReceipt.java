package vn.edu.aptech.hotelmanager.repo.converter;

import lombok.NonNull;
import vn.edu.aptech.hotelmanager.domain.model.ReceiptType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReceiptTypeEntityToReceipt implements IEntityConverter<ReceiptType> {

    @Override
    public ReceiptType convert(@NonNull ResultSet source) {
        ReceiptType receiptType = new ReceiptType();
        try {
            receiptType.setId(source.getInt("id"));
            receiptType.setName(source.getString("name"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return receiptType;
    }
}
