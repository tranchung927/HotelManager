package vn.edu.aptech.hotelmanager.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import vn.edu.aptech.hotelmanager.common.entity.MyListener;
import vn.edu.aptech.hotelmanager.domain.model.Product;


public class ItemController {
    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private void clickProduct(MouseEvent mouseEvent){
      myListener.onclickProductListener(productBill);
    }

    private Product productBill;
    private MyListener myListener;


    public void setData(Product productBill, MyListener myListener){
        this.productBill = productBill;
        this.myListener = myListener;
        nameLabel.setText(productBill.getName());
        priceLabel.setText(productBill.getPriceCost()+ " VNĐ");
    }

}
