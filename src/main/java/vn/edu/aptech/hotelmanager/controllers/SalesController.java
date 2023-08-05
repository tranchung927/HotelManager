package vn.edu.aptech.hotelmanager.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import vn.edu.aptech.hotelmanager.HMResourcesLoader;
import vn.edu.aptech.hotelmanager.common.entity.MyListener;
import vn.edu.aptech.hotelmanager.utils.ProductBill;

import javax.swing.*;
import java.awt.geom.Arc2D;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SalesController implements Initializable {
    private Stage stage;
    private  String nameProduct;

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    @FXML
    private TableView <ProductBill> billTableView; //table;
    @FXML
    private TableColumn<ProductBill,Integer> idProductCol;
    @FXML
    private TableColumn<ProductBill,String> ProductNameCol;
    @FXML
    private TableColumn<ProductBill,Integer> quantityCol;
    @FXML
    private TableColumn<ProductBill,Double> priceCol;
    @FXML
    private GridPane grid;
    @FXML
    private TextField totalPriceTextField;
    @FXML
    private ScrollPane scrollPane;
    public  static Float totalReceipt;
   public static ObservableList<ProductBill> productBillList;

    public ObservableList<ProductBill> getProductBillList() {
        return productBillList;
    }

    public SalesController() {
    }
    private MyListener myListener;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productBillList = FXCollections.observableArrayList(

        );
        idProductCol.setCellValueFactory(new PropertyValueFactory<ProductBill,Integer>("id"));
        ProductNameCol.setCellValueFactory(new PropertyValueFactory<ProductBill,String>("name"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<ProductBill,Integer>("quantity"));
        priceCol.setCellValueFactory(new PropertyValueFactory<ProductBill,Double>("price"));
        billTableView.setItems(productBillList);
        productBills.addAll(getData());
        if(productBills.size() > 0){
            //setChosenProduct(productBills.get(0));
            myListener = new MyListener() {
                @Override
                public void onclickProductListener(ProductBill productBill) {
                    setChosenProduct(productBill);
                }
            };
        }
        int column = 0;
        int row = 0;
        try {
            for (int i = 0; i < productBills.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader(HMResourcesLoader.loadURL("fxml/item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                ItemController itemController = fxmlLoader.getController();
                itemController.setData(productBills.get(i),myListener);

                if(column == 3){
                    column = 0;
                    row++;
                }
                grid.add(anchorPane,column++,row); // (child,column,row)
                GridPane.setMargin(anchorPane,new Insets(10));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void deleteTableItem(ActionEvent event) {
        ProductBill selected = billTableView.getSelectionModel().getSelectedItem();
        System.out.println("select: " + selected);
        productBillList.remove(selected);
        float total = 0;
        for(ProductBill p:productBillList){
            total += p.getQuantity()*p.getPrice();
        }
        totalReceipt = total;
        totalPriceTextField.setText(total + " VNĐ");

    }

    //-----------------------------------------
    private List<ProductBill> productBills = new ArrayList<>();
    private List<ProductBill> getData(){
        List<ProductBill> productBills = new ArrayList<>();
        ProductBill productBill;

        productBill = new ProductBill();
        productBill.setId(1);
        productBill.setName("Thăng long");
        productBill.setPrice(25000.0);
        productBills.add(productBill);

        productBill = new ProductBill();
        productBill.setId(2);
        productBill.setName("Vina");
        productBill.setPrice(30000.0);
        productBills.add(productBill);

        productBill = new ProductBill();
        productBill.setId(3);
        productBill.setName("555");
        productBill.setPrice(50000.0);
        productBills.add(productBill);

        productBill = new ProductBill();
        productBill.setId(4);
        productBill.setName("Coca cola");
        productBill.setPrice(20000.0);
        productBills.add(productBill);

        productBill = new ProductBill();
        productBill.setId(5);
        productBill.setName("Nước suối");
        productBill.setPrice(10000.0);
        productBills.add(productBill);

        productBill = new ProductBill();
        productBill.setId(6);
        productBill.setName("Giặt là");
        productBill.setPrice(40000.0);
        productBills.add(productBill);

        productBill = new ProductBill();
        productBill.setId(7);
        productBill.setName("Thuê xe máy");
        productBill.setPrice(120000.0);
        productBills.add(productBill);

        productBill = new ProductBill();
        productBill.setId(8);
        productBill.setName("Tour nội thành");
        productBill.setPrice(300000.0);
        productBills.add(productBill);

        productBill = new ProductBill();
        productBill.setId(9);
        productBill.setName("Cơm rang");
        productBill.setPrice(40000.0);
        productBills.add(productBill);

        productBill = new ProductBill();
        productBill.setId(10);
        productBill.setName("Mì xào");
        productBill.setPrice(40000.0);
        productBills.add(productBill);

        productBill = new ProductBill();
        productBill.setId(11);
        productBill.setName("Nước cam");
        productBill.setPrice(50000.0);
        productBills.add(productBill);

        productBill = new ProductBill();
        productBill.setId(12);
        productBill.setName("Nước yến");
        productBill.setPrice(25000.0);
        productBills.add(productBill);

        productBill = new ProductBill();
        productBill.setId(13);
        productBill.setName("Bia tiger");
        productBill.setPrice(20000.0);
        productBills.add(productBill);

        productBill = new ProductBill();
        productBill.setId(14);
        productBill.setName("Nước heniken");
        productBill.setPrice(25000.0);
        productBills.add(productBill);
        return productBills;
    }
    // khi chọn 1 sản phẩm
    float total = 0;

    private void setChosenProduct(ProductBill productBill){
        int i = 1;
        ProductBill pro = new ProductBill();
        pro.setName(productBill.getName());
        pro.setPrice(productBill.getPrice());
        pro.setId(productBill.getId());

            if(!checkIfContains(productBillList,productBill.getId())){
                pro.setQuantity(i);
                productBillList.add(pro);
            }else {
                ProductBill monTangSl = getProducBillByName(productBillList,pro.getId());
                if(monTangSl != null){
                    monTangSl.setQuantity(monTangSl.getQuantity()+1);
                    productBillList.set(productBillList.indexOf(monTangSl),monTangSl);
                }
            }
            float total = 0;
            for(ProductBill p:productBillList){
                total += p.getQuantity()*p.getPrice();
            }
            totalReceipt = total;
        totalPriceTextField.setText(total + " VNĐ");

    }

    private boolean checkIfContains(ObservableList<ProductBill> productBillList, int id) {
        for(ProductBill p: this.productBillList){
            if(p.getId()==id){
                return true;
            }
        }
        return false;
    }

    private ProductBill getProducBillByName(ObservableList<ProductBill> productBillList, int id) {
        for(ProductBill product: productBillList){
            if(product.getId() == id){
                return product;
            }
        }
        return null;
    }

    public void showReceipt(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(HMResourcesLoader.loadURL("fxml/receiptProduct.fxml"));
        loader.setControllerFactory(c -> new ReceiptProduct());
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
}