package vn.edu.aptech.hotelmanager.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import vn.edu.aptech.hotelmanager.HMResourcesLoader;
import vn.edu.aptech.hotelmanager.common.entity.MyListener;
import vn.edu.aptech.hotelmanager.utils.ProductBill;

import java.io.IOException;


public class ItemController {
    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;
    @FXML
    private void clickProduct(MouseEvent mouseEvent){
      myListener.onclickProductListener(productBill);
//        FXMLLoader loader = new FXMLLoader(HMResourcesLoader.loadURL("fxml/addProductReceipt.fxml"));
//        loader.setControllerFactory(c -> new addProductReceiptController());
//        Parent root = null;
//        try {
//            root = loader.load();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        stage = new Stage();
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
    }
    private ProductBill productBill;

    private MyListener myListener;


    public void setData(ProductBill productBill,MyListener myListener){
        this.productBill = productBill;
        this.myListener = myListener;
        nameLabel.setText(productBill.getName());
        priceLabel.setText(productBill.getPrice() + "VNĐ");
    }

}
