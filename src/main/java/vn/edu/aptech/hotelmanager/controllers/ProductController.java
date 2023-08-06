package vn.edu.aptech.hotelmanager.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import vn.edu.aptech.hotelmanager.utils.ProductBill;
import vn.edu.aptech.hotelmanager.utils.ProductWareHouse;
import vn.edu.aptech.hotelmanager.utils.Unit;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductController implements Initializable {
    @FXML
    private MFXButton btnSaveProduct;
    @FXML
    private MFXDatePicker txtDateInputProduct;

    @FXML
    private MFXComboBox<Unit> txtInitProduct;

    @FXML
    private MFXTextField txtInputPrice;
    @FXML
    private MFXTextField txtNameProduct;
    @FXML
    private MFXTextField txtIdProduct;

    @FXML
    private MFXTextField txtQuantityProduct;

    @FXML
    private MFXTextField txtUserInputProduct;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Unit>list = FXCollections.observableArrayList(Unit.Lon,Unit.Vé,Unit.Bao,Unit.Lượt);
        txtInitProduct.setItems(list);
        if(WarehouseController.isSelectedImportProductInWareHouse){
            ProductWareHouse productWareHouse = new ProductWareHouse();
            productWareHouse = WarehouseController.selectedProductInTable;
            txtIdProduct.setText(String.valueOf(productWareHouse.getId()));
            txtNameProduct.setText(productWareHouse.getName());
            txtInitProduct.setText(String.valueOf(productWareHouse.getUnit()));
            txtInputPrice.setText(String.valueOf(productWareHouse.getPriceInput()));
        }
    }
    public void saveProduct(ActionEvent event) {
        if(!checkIfContains(WarehouseController.productWareHouses,Integer.parseInt(txtIdProduct.getText()))){
            ProductWareHouse productWareHouse = new ProductWareHouse();
            String unit =  txtInitProduct.getSelectionModel().getSelectedItem().toString();
            productWareHouse.setId(Integer.parseInt(txtIdProduct.getText()));
            productWareHouse.setName(txtNameProduct.getText());
            productWareHouse.setPriceInput(Double.parseDouble(txtInputPrice.getText()));
            productWareHouse.setQuantityImport(Integer.parseInt(txtQuantityProduct.getText()));
            productWareHouse.setUnit(Unit.valueOf(unit));
            productWareHouse.setDateInput(txtDateInputProduct.getValue().toString());
            productWareHouse.setUserImport(txtUserInputProduct.getText());
            WarehouseController.productWareHouses.add(productWareHouse);
            WarehouseController.productWareHousesReceipt.add(productWareHouse);
        }else {
            ProductWareHouse spTangSl = WarehouseController.selectedProductInTable;
            spTangSl.setQuantityImport(WarehouseController.selectedProductInTable.getQuantityImport()+Integer.parseInt(txtQuantityProduct.getText()));
            WarehouseController.productWareHouses.set(WarehouseController.productWareHouses.indexOf(spTangSl),spTangSl);
            spTangSl.setDateInput(txtDateInputProduct.getValue().toString());
            WarehouseController.productWareHousesReceipt.add(spTangSl);
        }
    }
    private boolean checkIfContains(ObservableList<ProductWareHouse> wareHouses, int id) {
        for(ProductWareHouse p: wareHouses){
            if(p.getId()==id){
                return true;
            }
        }
        return false;
    }
}
