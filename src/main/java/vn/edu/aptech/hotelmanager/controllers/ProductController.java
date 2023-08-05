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
import vn.edu.aptech.hotelmanager.utils.ProductWareHouse;
import vn.edu.aptech.hotelmanager.utils.Unit;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductController implements Initializable {
    @FXML
    private MFXButton btnSaveProduct;

    @FXML
    private MFXDatePicker txtDataInputProduct;

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
        ObservableList<Unit>list = FXCollections.observableArrayList(Unit.Lon,Unit.vé,Unit.bao,Unit.kg);
        txtInitProduct.setItems(list);
    }

    public void saveProduct(ActionEvent event) {
        String unit =  txtInitProduct.getSelectionModel().getSelectedItem().toString();
        ProductWareHouse productWareHouse = new ProductWareHouse();
        productWareHouse.setId(Integer.parseInt(txtIdProduct.getText()));
        productWareHouse.setName(txtNameProduct.getText());
        productWareHouse.setPriceInput(Double.parseDouble(txtInputPrice.getText()));
        productWareHouse.setQuantityImport(Integer.parseInt(txtQuantityProduct.getText()));
        productWareHouse.setUnit(Unit.valueOf(unit));
        WarehouseController.productWareHouses.add(productWareHouse);
    }
}
