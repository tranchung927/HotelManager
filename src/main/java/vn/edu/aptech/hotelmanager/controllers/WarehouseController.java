package vn.edu.aptech.hotelmanager.controllers;

import fr.brouillard.oss.cssfx.CSSFX;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import vn.edu.aptech.hotelmanager.HMResourcesLoader;
import vn.edu.aptech.hotelmanager.utils.ProductBill;
import vn.edu.aptech.hotelmanager.utils.ProductWareHouse;
import vn.edu.aptech.hotelmanager.utils.Unit;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WarehouseController implements Initializable {
    private Stage stage;

    @FXML
    private TableColumn<ProductWareHouse, Integer> tcId;
    @FXML
    private TableColumn<ProductWareHouse, String> tcName;

    @FXML
    private TableColumn<ProductWareHouse, Double> tcPriceInput;

    @FXML
    private TableColumn<ProductWareHouse, Integer> tcQuantity;

    @FXML
    private TableColumn<ProductWareHouse, Unit> tcUnit;

    @FXML
    private TableView<ProductWareHouse> wareHouseTableView;



    @FXML
    void addProduct(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(HMResourcesLoader.loadURL("fxml/Product.fxml"));
        loader.setControllerFactory(c -> new ProductController());
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
// dùng để hiển thị thông tin ở table
    public static ObservableList<ProductWareHouse> productWareHouses;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productWareHouses = FXCollections.observableArrayList(
                new ProductWareHouse("Thăng long",1,5,null, 25000.0,null,Unit.Lon)
        );
        tcId.setCellValueFactory(new PropertyValueFactory<ProductWareHouse,Integer>("id"));
       tcName.setCellValueFactory(new PropertyValueFactory<ProductWareHouse,String>("name"));
       tcQuantity.setCellValueFactory(new PropertyValueFactory<ProductWareHouse,Integer>("quantityImport"));
        tcPriceInput.setCellValueFactory(new PropertyValueFactory<ProductWareHouse,Double>("priceInput"));
        tcUnit.setCellValueFactory(new PropertyValueFactory<ProductWareHouse,Unit>("unit"));
        wareHouseTableView.setItems(productWareHouses);
    }

}
