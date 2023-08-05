package vn.edu.aptech.hotelmanager.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import vn.edu.aptech.hotelmanager.utils.ProductBill;

import java.net.URL;
import java.util.ResourceBundle;

public class ReceiptProduct implements Initializable {
    @FXML
    private TableColumn<ProductBill, String> ProductNameCol;

    @FXML
    private TableView<ProductBill> billTableView;

    @FXML
    private TableColumn<ProductBill, Integer> idProductCol;

    @FXML
    private TableColumn<ProductBill, Double> priceCol;

    @FXML
    private TableColumn<ProductBill, Integer> quantityCol;

    @FXML
    private TextField totalPriceTextField;
    private ObservableList<ProductBill> productBillListReceipt;

    public ObservableList<ProductBill> getProductBillListReceipt() {
        return productBillListReceipt;
    }

    public void setProductBillListReceipt(ObservableList<ProductBill> productBillListReceipt) {
        this.productBillListReceipt = productBillListReceipt;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idProductCol.setCellValueFactory(new PropertyValueFactory<ProductBill,Integer>("id"));
        ProductNameCol.setCellValueFactory(new PropertyValueFactory<ProductBill,String>("name"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<ProductBill,Integer>("quantity"));
        priceCol.setCellValueFactory(new PropertyValueFactory<ProductBill,Double>("price"));
        totalPriceTextField.setText(SalesController.totalReceipt+ " VNĐ");
        billTableView.setItems(SalesController.productBillList);
    }
}
