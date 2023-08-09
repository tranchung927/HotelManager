package vn.edu.aptech.hotelmanager.controllers;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import vn.edu.aptech.hotelmanager.domain.REPO_TYPE;
import vn.edu.aptech.hotelmanager.domain.RepoFactory;
import vn.edu.aptech.hotelmanager.domain.dto.ProductDTO;
import vn.edu.aptech.hotelmanager.domain.model.Inventory;
import vn.edu.aptech.hotelmanager.domain.model.PricePolicy;
import vn.edu.aptech.hotelmanager.domain.model.Product;
import vn.edu.aptech.hotelmanager.domain.repo.IProductRepo;
import vn.edu.aptech.hotelmanager.repo.db.DBConnection;
import vn.edu.aptech.hotelmanager.utils.ProductBill;
import vn.edu.aptech.hotelmanager.utils.ProductWareHouse;
import vn.edu.aptech.hotelmanager.utils.Unit;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

public class ProductController implements Initializable {
    @FXML
    private MFXButton btnSaveProduct;
    @FXML
    private MFXDatePicker txtDateInputProduct;
    @FXML
    private MFXTextField txtOutputPrice;
    @FXML
    private MFXComboBox<Unit> txtInitProduct;

    @FXML
    private MFXTextField txtInputPrice;
    @FXML
    private MFXTextField txtNameProduct;
    @FXML
    private MFXTextField txtQuantityProduct;

    @FXML
    private MFXTextField txtUserInputProduct;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Unit>list = FXCollections.observableArrayList(Unit.Lon,Unit.Vé,Unit.Bao,Unit.Lượt);
        txtInitProduct.setItems(list);
        if(WarehouseController.isSelectedImportProductInWareHouse){
            Product product = new Product();
            product = WarehouseController.selectedProductInTable;
            txtNameProduct.setText(product.getName());
            txtInitProduct.setText(String.valueOf(product.getUnit()));
            txtInputPrice.setText(String.valueOf(product.getPricePolicy().getInitPrice()));
            txtOutputPrice.setText(String.valueOf(product.getPricePolicy().getCostPrice()));
        }
    }
    Connection connection;

    public void saveProduct(ActionEvent event) throws SQLException {
        IProductRepo productRepo = RepoFactory.getInstance().getRepo(REPO_TYPE.PRODUCT);
        List<PricePolicy> listPricePolicy = productRepo.getListPricePolicy(1,20,"");
        List<Inventory> inventoryList = productRepo.getListInventory(1,20,"");

        PreparedStatement prepared;
        IProductRepo iProductRepo = RepoFactory.getInstance().getRepo(REPO_TYPE.PRODUCT);
        if(!WarehouseController.isSelectedImportProductInWareHouse){
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
            Product productWareHouse = new Product();
            String unit =  txtInitProduct.getSelectionModel().getSelectedItem().toString();
            productWareHouse.setName(txtNameProduct.getText());

            PricePolicy pricePolicy = new PricePolicy();
            pricePolicy.setInitPrice(Integer.parseInt(txtInputPrice.getText()));
            pricePolicy.setCostPrice(Integer.parseInt(txtOutputPrice.getText()));

            Inventory inventory = new Inventory();
            inventory.setAvailableQuantity(Double.parseDouble(txtQuantityProduct.getText()));
            productWareHouse.setUnit(String.valueOf(Unit.valueOf(unit)));
            try {
                productWareHouse.setDateInput(formatter1.parse(txtDateInputProduct.getValue().toString()));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            productWareHouse.setUserImport(txtUserInputProduct.getText());
            //WarehouseController.productWareHouses.add(productWareHouse);

            try {
                String sqlPricePolicy = "INSERT INTO price_policies(init_price,cost_price) VALUES (?,?)";
                String sqlProduct = "INSERT INTO `products`(name,unit,price_policy_id,inventory_id) VALUES (?,?,?,?)";
                String sqlInventory = "INSERT INTO `inventories`(available_quantity) VALUES(?)";
                connection = DBConnection.getInstance().getConnection();
                // insert in db
                prepared = connection.prepareStatement(sqlPricePolicy);
                prepared.setDouble(1,pricePolicy.getInitPrice());
                prepared.setDouble(2,pricePolicy.getCostPrice());
                prepared.executeUpdate();
                prepared.close();

                //insert inventory
                prepared = connection.prepareStatement(sqlInventory);
                prepared.setDouble(1,inventory.getAvailableQuantity());
                prepared.executeUpdate();
                prepared.close();

                long maxIdPricePolicy = listPricePolicy.get(0).getId();
                for (int i = 0;i<listPricePolicy.size();i++){
                    if(listPricePolicy.get(i).getId()>maxIdPricePolicy){
                        maxIdPricePolicy = listPricePolicy.get(i).getId();
                    }
                }

                long maxIdInventory = inventoryList.get(0).getId();
                for (int i = 0;i<inventoryList.size();i++){
                    if(inventoryList.get(i).getId()>maxIdInventory){
                        maxIdInventory = inventoryList.get(i).getId();
                    }
                }
                System.out.println("inventorymaxid"+ maxIdInventory + " maxPrice" + maxIdPricePolicy);

                // insert product
                prepared = connection.prepareStatement(sqlProduct);
                prepared.setString(1,productWareHouse.getName());
                prepared.setString(2,productWareHouse.getUnit());
                prepared.setLong(3,maxIdPricePolicy+1);
                prepared.setLong(4,maxIdInventory+1);
                prepared.executeUpdate();
                prepared.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            //sql
            System.out.println("choose product"+ WarehouseController.selectedProductInTable);
            String updateQuanity = "UPDATE `inventories` SET `available_quantity`= ? WHERE id = ?";
            connection = DBConnection.getInstance().getConnection();
            prepared = connection.prepareStatement(updateQuanity);
            prepared.setDouble(2,WarehouseController.selectedProductInTable.getInventory().getId());
            prepared.setDouble(1,WarehouseController.selectedProductInTable.getInventory().getAvailableQuantity()+Double.parseDouble(txtQuantityProduct.getText()));
            prepared.executeUpdate();
            prepared.close();
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
            Product spTangSl = WarehouseController.selectedProductInTable;
            spTangSl.getInventory().setAvailableQuantity(WarehouseController.selectedProductInTable.getQuantityAvailable()+ Integer.parseInt(txtQuantityProduct.getText()));
            try {
                spTangSl.setDateInput(formatter1.parse(txtDateInputProduct.getValue().toString()));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            //WarehouseController.productWareHousesReceipt.add(spTangSl);
        }
    }
    private boolean checkIfContains(ObservableList<Product> wareHouses, long id) {
        for(Product p: wareHouses){
            if(p.getId()==id){
                return true;
            }
        }
        return false;
    }
    public void showReceiptInTable(){
        // insert into db
       // String  insertSql = ""


    }
}
