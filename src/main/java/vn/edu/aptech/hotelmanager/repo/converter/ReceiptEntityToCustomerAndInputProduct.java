package vn.edu.aptech.hotelmanager.repo.converter;

import lombok.NonNull;
import vn.edu.aptech.hotelmanager.domain.model.Receipt;
import vn.edu.aptech.hotelmanager.domain.model.ReceiptType;

import java.sql.ResultSet;
import java.util.Date;

public class ReceiptEntityToCustomerAndInputProduct implements  IEntityConverter<Receipt>{

    @Override
    public Receipt convert(@NonNull ResultSet source) {
      Receipt receipt = new Receipt();
      try{
          receipt.setId(source.getLong("id"));
         receipt.setCreateAt(source.getString("create_at"));
         receipt.setTotalPayment(source.getDouble("total_payment"));
         receipt.setQuantityImport(source.getInt("quantity_import"));
         receipt.setInputPrice(source.getDouble("input_price"));
         receipt.setOutputPrice(source.getDouble("output_price"));
         receipt.setUnit(source.getString("unit"));
         receipt.setReceiptCode(source.getInt("receipt_code"));
         receipt.setProductName(source.getString("product_name"));
      }catch (Exception e){
          e.printStackTrace();
      }
      return receipt;
    }
}
