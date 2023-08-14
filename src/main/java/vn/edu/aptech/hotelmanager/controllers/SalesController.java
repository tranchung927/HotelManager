package vn.edu.aptech.hotelmanager.controllers;

import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.virtualizedfx.utils.VSPUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import vn.edu.aptech.hotelmanager.HMResourcesLoader;
import vn.edu.aptech.hotelmanager.common.entity.MyListener;
import vn.edu.aptech.hotelmanager.domain.REPO_TYPE;
import vn.edu.aptech.hotelmanager.domain.RepoFactory;
import vn.edu.aptech.hotelmanager.domain.dto.ProductDTO;
import vn.edu.aptech.hotelmanager.domain.model.Inventory;
import vn.edu.aptech.hotelmanager.domain.model.Product;
import vn.edu.aptech.hotelmanager.domain.model.Receipt;
import vn.edu.aptech.hotelmanager.domain.model.ReceiptType;
import vn.edu.aptech.hotelmanager.domain.repo.IProductRepo;
import vn.edu.aptech.hotelmanager.domain.repo.IRoomRepo;
import vn.edu.aptech.hotelmanager.domain.repo.IWareHouseRepo;
import vn.edu.aptech.hotelmanager.repo.db.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class SalesController implements Initializable {
   private final IWareHouseRepo wareHouseRepo = RepoFactory.getInstance().getRepo(REPO_TYPE.WAREHOUSE);
    private final IProductRepo productRepo = RepoFactory.getInstance().getRepo(REPO_TYPE.PRODUCT);
    private Stage stage;
    @FXML
    private MFXTableView billTableView;
    @FXML
    private GridPane grid;
    @FXML
    private TextField totalPriceTextField;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private MFXDatePicker txtDateReceipt;

    @FXML
    private MFXTextField txtUserWriteReceipt;
    @FXML
    private Label lblMaRandom;
    @FXML
    private Label nameProductWantBuy;

    //Lấy dữ liệu vảo receipt
    public static Double totalReceipt;
    // thao tac để import du lieu vào bảng
    public static ObservableList<Product> dataOfTableReceipt;
    private MyListener myListener;
    private List<Product> listProductShows = new ArrayList<>();
    private List<Product> getDataInTableShow;
    int maHd = 1;
    boolean isClickRefesh = false;

    // code lại
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Random random = new Random();
        lblMaRandom.setText(random.nextInt(99999)+ "");
        setupTable();
        listProductShows.addAll(getData());
        dataOfTableReceipt = FXCollections.observableArrayList(
        );
        if (listProductShows.size() > 0) {
            myListener = new MyListener() {
                @Override
                public void onclickProductListener(Product productBill) {
                    setChosenProduct(productBill);
                }
            };
        }
        showProduct();
    }
    public  void showProduct(){
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < listProductShows.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader(HMResourcesLoader.loadURL("fxml/item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                ItemController itemController = fxmlLoader.getController();
                itemController.setData(listProductShows.get(i), myListener);
                if (column == 3) {
                    column = 0;
                    row++;
                }
                grid.add(anchorPane, column++, row); // (child,column,row)
                //set grid with
                grid.setMaxWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);

                // set grid height
                grid.setMaxHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void deleteTableItem(ActionEvent event) {
        Product selected = (Product) billTableView.getSelectionModel().getSelectedValue();
        System.out.println("select: " + selected);
        dataOfTableReceipt.remove(selected);
        Double total = 0.0;
        for (Product p : dataOfTableReceipt) {
            total += p.getQuantity() * p.getInputPrice();
        }
        totalReceipt = total;
        totalPriceTextField.setText(total + " VNĐ");

    }

    //-----------------------------------------
    private List<Product> getData() {
        List<Product> listProduct = productRepo.getListProduct(1, 20, "")
                .stream().map((ProductDTO::getProduct)).toList();
        return listProduct;
    }
    // khi chọn 1 sản phẩm
    float total = 0;
    public static Product getSelectedProduct;

    private void setChosenProduct(Product productBill) {
        Double i = 1.0;
        Product pro = new Product();
        pro.setName(productBill.getName());
        pro.setInputPrice(productBill.getPriceCost());
        pro.setId(productBill.getId());
        pro.setQuantity(i);
        pro.setInventory(productBill.getInventory());
        pro.setPricePolicy(productBill.getPricePolicy());
        if (!checkIfContains(dataOfTableReceipt, productBill.getId())) {
            pro.setQuantity(i);
            dataOfTableReceipt.add(pro);
        } else {
            Product monTangSl = getProducBillByName(dataOfTableReceipt, pro.getId());
            if (monTangSl != null) {
                monTangSl.setQuantity(monTangSl.getQuantity() + 1);
                dataOfTableReceipt.set(dataOfTableReceipt.indexOf(monTangSl), monTangSl);
            }
        }
        billTableView.setItems(dataOfTableReceipt);
        Double total = 0.0;
        for (Product p : dataOfTableReceipt) {
            total += p.getQuantity() * p.getInputPrice();
        }
        totalReceipt = total;
        totalPriceTextField.setText(total + " VNĐ");
    }

    private boolean checkIfContains(ObservableList<Product> productBillList, long id) {
        for (Product p : this.dataOfTableReceipt) {
            if (p.getId() == id) {
                return true;
            }
        }
        return false;
    }

    private Product getProducBillByName(ObservableList<Product> productBillList, long id) {
        for (Product product : productBillList) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    public void showReceipt(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(HMResourcesLoader.loadURL("fxml/receiptProduct.fxml"));
        loader.setControllerFactory(c -> new ReceiptProductController());
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
        Random random = new Random();
        lblMaRandom.setText(random.nextInt(99999)+ "");
        Receipt receipt = new Receipt();
        ReceiptType receiptType = new ReceiptType();
        receiptType.setId(2);
        receiptType.setName("Hoá đơn khách");
        receipt.setType(receiptType);
        receipt.setImporter(txtUserWriteReceipt.getText());
        receipt.setCreateAt(txtDateReceipt.getText());
        receipt.setTotalPayment(totalReceipt);
        List<Receipt> receipts = wareHouseRepo.getListReceiptCustomer(1,2,"");
        for(Receipt r: receipts){
            if (r.getReceiptCode() == Integer.parseInt(lblMaRandom.getText())){
                lblMaRandom.setText(random.nextInt(99999)+ "");
            }else {
                receipt.setReceiptCode(Integer.parseInt(lblMaRandom.getText()));
            }
        }
        receipt.setStatus(1);
        wareHouseRepo.insertReceiptCustomer(receipt);
       
        // set data
        for(Product p :dataOfTableReceipt){

            getData();
            Double quantityUpdate = null;
            long id = p.getInventory().getId();
            for(Product a: getData()){
                if(a.getInventory().getId()== id){
                    quantityUpdate  = a.getInventory().getAvailableQuantity() - p.getQuantity();
                }            }
            productRepo.updateProductInSales(quantityUpdate,id);
        }
    }

    public void refeshReceipt(ActionEvent event) {
        Random random = new Random();
        lblMaRandom.setText(random.nextInt(99999)+ "");
        billTableView.getItems().clear();
        totalReceipt = 0.0;
        txtUserWriteReceipt.setText("");
        txtDateReceipt.setText("");
        totalPriceTextField.setText(totalReceipt + " VNĐ");
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

        billTableView.getTableColumns().addAll(idColumnOfBill, nameColumnOfBill, quantityColumnOfBill, priceColumnOfBill);
    }

}