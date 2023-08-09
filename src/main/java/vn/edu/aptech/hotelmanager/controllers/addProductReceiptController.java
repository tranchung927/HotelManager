package vn.edu.aptech.hotelmanager.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import vn.edu.aptech.hotelmanager.domain.model.Product;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class addProductReceiptController implements Initializable{
    @FXML
    private Label nameProductWantBuy;

    @FXML
    private Label priceLabel;

    @FXML
    private MFXTextField quantiyProductWantToBuy;

    @FXML
    private MFXButton removeQuantity;
    public  static List<Product>lisProductBuy = new ArrayList<>();
//    public void setProduct(Product product){
//        nameProductWantBuy.setText(product.getName());
//        priceLabel.setText(String.valueOf(product.getPriceCost()));
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameProductWantBuy.setText(SalesController.getSelectedProduct.getName());
        priceLabel.setText(String.valueOf(SalesController.getSelectedProduct.getInputPrice()));
    }
    public  void addProductChoose(ActionEvent event) {
        Product product = new Product();
        product.setName(nameProductWantBuy.getText());
        product.setInputPrice(Double.parseDouble(priceLabel.getText()));
        product.setQuantity(Double.valueOf(quantiyProductWantToBuy.getText()));
//        SalesController.dataOfTableReceipt.add(product);
//        System.out.println(SalesController.dataOfTableReceipt);
        lisProductBuy.add(product);
    }
}
