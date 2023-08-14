package vn.edu.aptech.hotelmanager.controllers;

import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import vn.edu.aptech.hotelmanager.domain.model.Product;

import java.net.URL;
import java.util.ResourceBundle;

public class ReceiptProductController implements Initializable {
    @FXML
    private MFXTableView<Product> showReceipt;

    @FXML
    private Label showTotalBill;
    private ObservableList<Product> productBillListReceipt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       setupTable();
       productBillListReceipt = FXCollections.observableArrayList(
               SalesController.dataOfTableReceipt
       );
       showTotalBill.setText(String.valueOf(SalesController.totalReceipt));
       showReceipt.setItems(productBillListReceipt);
    }
    private void setupTable() {
        MFXTableColumn<Product> idColumnOfBill = new MFXTableColumn<>("Id", true);
        MFXTableColumn<Product> nameColumnOfBill = new MFXTableColumn<>("Name", true);
        MFXTableColumn<Product> quantityColumnOfBill = new MFXTableColumn<>("Quantity", true);
        MFXTableColumn<Product> priceColumnOfBill = new MFXTableColumn<>("Price", true);

        nameColumnOfBill.setRowCellFactory(product -> new MFXTableRowCell<>(Product::getName));
        idColumnOfBill.setRowCellFactory(product -> new MFXTableRowCell<>(Product::getId));
        quantityColumnOfBill.setRowCellFactory(product -> new MFXTableRowCell<>(Product::getQuantity));
        priceColumnOfBill.setRowCellFactory(product -> new MFXTableRowCell<>(Product::getInputPrice));

        showReceipt.getTableColumns().addAll(idColumnOfBill,nameColumnOfBill,quantityColumnOfBill,priceColumnOfBill);
    }
}
