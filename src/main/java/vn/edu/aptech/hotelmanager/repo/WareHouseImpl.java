package vn.edu.aptech.hotelmanager.repo;

import javafx.scene.control.Alert;
import vn.edu.aptech.hotelmanager.domain.model.Receipt;
import vn.edu.aptech.hotelmanager.domain.repo.IWareHouseRepo;
import vn.edu.aptech.hotelmanager.repo.converter.ReceiptEntityToCustomerAndInputProduct;
import vn.edu.aptech.hotelmanager.repo.converter.ReceiptTypeEntityToReceipt;
import vn.edu.aptech.hotelmanager.utils.CrudUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class WareHouseImpl implements IWareHouseRepo {
    @Override
    public List<Receipt> getListReceiptCustomer(int paga, int pageSize, String searchKey) {
        List<Receipt> receipts = new ArrayList<>();
        try{
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM `product_receipts` WHERE status = 1");
            while (resultSet.next()){
                Receipt receipt = new ReceiptEntityToCustomerAndInputProduct().convert(resultSet);
                long typeProductId = resultSet.getInt("type");
                ResultSet resultSetType = CrudUtil.execute("SELECT * FROM `receipt_type` WHERE id = ?",typeProductId);
                if(resultSetType.next()){
                    receipt.setType(new ReceiptTypeEntityToReceipt().convert(resultSetType));
                }
                receipts.add(receipt);
            }
        }catch (Exception e){

        }
        return receipts;
    }

    @Override
    public List<Receipt> getListReceiptImportInWareHouse(int paga, int pageSize, String searchKey) {
        return null;
    }

    @Override
    public void insertReceiptCustomer(Receipt receipt) throws Exception {
        String sql = "INSERT INTO `product_receipts`(`type`, `user_name_import`, `create_at`, " +
                "`total_payment`, `receipt_code`,`status`) " +
                "VALUES ('"+receipt.getType().getId()+"'," +
                "'"+receipt.getImporter()+"'," +
                "'"+receipt.getCreateAt()+"'," +
                "'"+receipt.getTotalPayment()+"'," +
                "'"+receipt.getReceiptCode()+"'," +
                "'"+receipt.getStatus()+"')";
        CrudUtil.execute(sql);
    }

    @Override
    public void insertReceiptWareHouse(Receipt receipt) throws Exception {
        String sql = "INSERT INTO `product_receipts`(`type`,`user_name_import`, `create_at`," +
                "`quantity_import`, `input_price`, " +
                "`output_price`, `unit`, " +
                "`product_name`,`status`) " +
                "VALUES ('"+receipt.getType().getId()+"'," +
                "'"+receipt.getImporter()+"'," +
                "'"+receipt.getCreateAt()+"'," +
                "'"+receipt.getQuantityImport()+"'," +
                "'"+receipt.getInputPrice()+"'," +
                "'"+receipt.getOutputPrice()+"'," +
                "'"+receipt.getUnit()+"'," +
                "'"+receipt.getProductName()+"'," +
                "'"+receipt.getStatus()+"')";
        CrudUtil.execute(sql);
    }
    @Override
    public void deleteRececeipt(Long selectedReceipt) throws Exception {
        String sql = "UPDATE `product_receipts` SET status = 2 WHERE id = '"+selectedReceipt+"'";
        Alert alert;
        try {
            boolean a = CrudUtil.execute(sql);
            if(a){

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Delete Successfully");
                alert.showAndWait();
            }else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
