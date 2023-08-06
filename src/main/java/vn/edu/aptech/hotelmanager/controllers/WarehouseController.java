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
import java.util.Date;
import java.util.ResourceBundle;

public class WarehouseController implements Initializable {
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
    private TableView<ProductWareHouse> wareHouseTableViewRecipt;
    @FXML
    private TableColumn<ProductWareHouse, Date> tcDateInputReceipt;

    @FXML
    private TableColumn<ProductWareHouse, Integer> tcIdReceipt;

    @FXML
    private TableColumn<ProductWareHouse, String> tcNameReceipt;


    @FXML
    private TableColumn<ProductWareHouse, Double> tcPriceInputReceipt;

    @FXML
    private TableColumn<ProductWareHouse, Integer> tcQuantityReceipt;

    @FXML
    private TableColumn<ProductWareHouse, Unit> tcUnitReceipt;
    @FXML
    private TableColumn<ProductWareHouse, String> tcUserInputProductReceipt;
    private Stage stage;
    public static boolean isSelectedImportProductInWareHouse = false;
    @FXML
    void addProduct(ActionEvent event) {
        isSelectedImportProductInWareHouse = false;
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
    public static ObservableList<ProductWareHouse> productWareHousesReceipt;
    public static ProductWareHouse selectedProductInTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productWareHouses = FXCollections.observableArrayList(
                new ProductWareHouse("Thang long",1,5,"vi",10000.0,null,Unit.Bao),
                new ProductWareHouse("555",2,5,"vi",10000.0,null,Unit.Bao),
                new ProductWareHouse("coca cola",3,5,"vi",10000.0,null,Unit.Bao)
        );
        tcId.setCellValueFactory(new PropertyValueFactory<ProductWareHouse,Integer>("id"));
       tcName.setCellValueFactory(new PropertyValueFactory<ProductWareHouse,String>("name"));
       tcQuantity.setCellValueFactory(new PropertyValueFactory<ProductWareHouse,Integer>("quantityImport"));
        tcPriceInput.setCellValueFactory(new PropertyValueFactory<ProductWareHouse,Double>("priceInput"));
        tcUnit.setCellValueFactory(new PropertyValueFactory<ProductWareHouse,Unit>("unit"));
        wareHouseTableView.setItems(productWareHouses);

        //receipt
        productWareHousesReceipt= FXCollections.observableArrayList();
        tcIdReceipt.setCellValueFactory(new PropertyValueFactory<ProductWareHouse,Integer>("id"));
        tcNameReceipt.setCellValueFactory(new PropertyValueFactory<ProductWareHouse,String>("name"));
        tcQuantityReceipt.setCellValueFactory(new PropertyValueFactory<ProductWareHouse,Integer>("quantityImport"));
        tcPriceInputReceipt.setCellValueFactory(new PropertyValueFactory<ProductWareHouse,Double>("priceInput"));
        tcUnitReceipt.setCellValueFactory(new PropertyValueFactory<ProductWareHouse,Unit>("unit"));
        tcDateInputReceipt.setCellValueFactory(new PropertyValueFactory<ProductWareHouse,Date>("dateInput"));
        tcUserInputProductReceipt.setCellValueFactory(new PropertyValueFactory<ProductWareHouse,String>("userImport"));
        wareHouseTableViewRecipt.setItems(productWareHousesReceipt);

    }
    public void importProductInWareHouse(ActionEvent event) {
        isSelectedImportProductInWareHouse = true;
        selectedProductInTable = new ProductWareHouse();
        selectedProductInTable = wareHouseTableView.getSelectionModel().getSelectedItem();
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

    public void deleteProductInWareHouse(ActionEvent event) {
      ProductWareHouse selectedImportProduct = wareHouseTableView.getSelectionModel().getSelectedItem();
        productWareHouses.remove(selectedImportProduct);
        ProductWareHouse selectedImportProductReceipt = wareHouseTableViewRecipt.getSelectionModel().getSelectedItem();
        productWareHousesReceipt.remove(selectedImportProductReceipt);
    }
}
