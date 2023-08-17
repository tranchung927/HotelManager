package vn.edu.aptech.hotelmanager.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import vn.edu.aptech.hotelmanager.domain.model.Product;


public class ItemController {
    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private void clickProduct(MouseEvent mouseEvent){
      myListenerOfproduct.onclickProductListener(productBill);
    }

    private Product productBill;
    private IProductControllerListener myListenerOfproduct;


    public void setData(Product productBill, IProductControllerListener myListener){
        this.productBill = productBill;
        this.myListenerOfproduct = myListener;
        nameLabel.setText(productBill.getName());
        priceLabel.setText(productBill.getPriceCost()+ " VNĐ");
    }

}
