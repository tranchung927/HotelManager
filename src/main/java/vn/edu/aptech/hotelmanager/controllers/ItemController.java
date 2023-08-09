package vn.edu.aptech.hotelmanager.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import vn.edu.aptech.hotelmanager.HMResourcesLoader;
import vn.edu.aptech.hotelmanager.common.entity.MyListener;
import vn.edu.aptech.hotelmanager.domain.model.Product;
import vn.edu.aptech.hotelmanager.utils.ProductBill;


public class ItemController {
    private Stage stage;
    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;
    @FXML
    private void clickProduct(MouseEvent mouseEvent){
      myListener.onclickProductListener(productBill);
//      FXMLLoader loader = new FXMLLoader(HMResourcesLoader.loadURL("fxml/addProductReceipt.fxml"));
//        loader.setControllerFactory(c -> new addProductReceiptController());
//        Parent root = null;
//        try {
//            root = loader.load();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        stage = new Stage();
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
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
