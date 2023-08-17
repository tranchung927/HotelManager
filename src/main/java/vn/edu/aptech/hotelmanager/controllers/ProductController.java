package vn.edu.aptech.hotelmanager.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import vn.edu.aptech.hotelmanager.domain.REPO_TYPE;
import vn.edu.aptech.hotelmanager.domain.RepoFactory;
import vn.edu.aptech.hotelmanager.domain.model.*;
import vn.edu.aptech.hotelmanager.domain.repo.IProductRepo;
import vn.edu.aptech.hotelmanager.domain.repo.IWareHouseRepo;
import vn.edu.aptech.hotelmanager.domain.model.UNIT_TYPE;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

public class ProductController implements Initializable {
    private final IProductRepo productRepo = RepoFactory.getInstance().getRepo(REPO_TYPE.PRODUCT);
    private final IWareHouseRepo wareHouseRepo = RepoFactory.getInstance().getRepo(REPO_TYPE.WAREHOUSE);
    List<PricePolicy> listPricePolicy = productRepo.getListPricePolicy(1,20,"");
    List<Inventory> inventoryList = productRepo.getListInventory(1,20,"");
    @FXML
    private MFXButton btnSaveProduct;

    @FXML
    private DatePicker txtDateInputProduct;

    @FXML
    private ComboBox<UNIT_TYPE> txtInitProduct;

    @FXML
    private TextField txtInputPrice;

    @FXML
    private TextField txtNameProduct;

    @FXML
    private TextField txtOutputPrice;

    @FXML
    private TextField txtQuantityProduct;

    @FXML
    private TextField txtUserInputProduct;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<UNIT_TYPE>list = FXCollections.observableArrayList(UNIT_TYPE.Can,
                UNIT_TYPE.Bag, UNIT_TYPE.Bottle, UNIT_TYPE.Package);
        txtInitProduct.setItems(list);
        if(WarehouseController.isSelectedImportProductInWareHouse){
            Product product = new Product();
            product = WarehouseController.selectedProductInTable;
            txtNameProduct.setText(product.getName());
            txtInitProduct.setValue(product.getUnit());
            txtInputPrice.setText(String.valueOf(product.getPricePolicy().getInitPrice()));
            txtOutputPrice.setText(String.valueOf(product.getPricePolicy().getCostPrice()));
        }
    }
    public void saveProduct(ActionEvent event) throws Exception {
        if(!WarehouseController.isSelectedImportProductInWareHouse){
            insertProduct();
        }else {
            //sql upvdate
            Double quantityUpdate =WarehouseController.selectedProductInTable.getInventory().getAvailableQuantity()+Double.parseDouble(txtQuantityProduct.getText());
            long idUpdate =WarehouseController.selectedProductInTable.getInventory().getId();
            productRepo.updateProductInWareHouse(quantityUpdate,idUpdate);
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
            Product spTangSl = WarehouseController.selectedProductInTable;
            spTangSl.getInventory().setAvailableQuantity(WarehouseController.selectedProductInTable.getQuantityAvailable()+ Integer.parseInt(txtQuantityProduct.getText()));
            try {
                spTangSl.setDateInput(formatter1.parse(txtDateInputProduct.getValue().toString()));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

        }
        addReceiptInDb();
    }
    private void  insertProduct() throws Exception {
        PricePolicy pricePolicy = new PricePolicy();
        pricePolicy.setInitPrice(Integer.parseInt(txtInputPrice.getText()));
        pricePolicy.setCostPrice(Integer.parseInt(txtOutputPrice.getText()));
        productRepo.insertPricePolicy(pricePolicy);

        Inventory inventory = new Inventory();
        inventory.setAvailableQuantity(Double.parseDouble(txtQuantityProduct.getText()));
        productRepo.insertInventory(inventory);

        // get id inven,policy
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
        Product product = new Product();
        String unit =  txtInitProduct.getSelectionModel().getSelectedItem().toString();
        product.setName(txtNameProduct.getText());
        product.setUnit(UNIT_TYPE.valueOfStatus(unit));
        PricePolicy pricePolicy1 = new PricePolicy();
        pricePolicy1.setId(maxIdPricePolicy+1);
        product.setPricePolicy(pricePolicy1);
        Inventory inventory1 = new Inventory();
        inventory1.setId(maxIdInventory+1);
        product.setInventory(inventory1);
        product.setStatus(1);
        productRepo.insertProducts(product);
    }
    private void addReceiptInDb() throws Exception {
        Receipt receipt = new Receipt();
        ReceiptType receiptType = new ReceiptType();
        receiptType.setId(1);
        receiptType.setName("Hoá đơn nhập");
        receipt.setType(receiptType);
        receipt.setImporter(txtUserInputProduct.getText());
        receipt.setCreateAt(String.valueOf(txtDateInputProduct.getValue()));
        receipt.setQuantityImport(Integer.parseInt(txtQuantityProduct.getText()));
        receipt.setInputPrice(Double.parseDouble(txtInputPrice.getText()));
        receipt.setOutputPrice(Double.parseDouble(txtOutputPrice.getText()));
        receipt.setUnit(String.valueOf(txtInitProduct.getValue()));
        receipt.setProductName(txtNameProduct.getText());
        receipt.setStatus(1);
        wareHouseRepo.insertReceiptWareHouse(receipt);
    }
}
