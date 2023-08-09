package vn.edu.aptech.hotelmanager.controllers;

import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import vn.edu.aptech.hotelmanager.HMResourcesLoader;
import vn.edu.aptech.hotelmanager.domain.REPO_TYPE;
import vn.edu.aptech.hotelmanager.domain.RepoFactory;
import vn.edu.aptech.hotelmanager.domain.dto.ProductDTO;
import vn.edu.aptech.hotelmanager.domain.model.Product;
import vn.edu.aptech.hotelmanager.domain.model.Receipt;
import vn.edu.aptech.hotelmanager.domain.repo.IProductRepo;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class WarehouseController implements Initializable {
    @FXML
    private MFXTableView<Receipt> infoImportProduct;
    @FXML
    private MFXTableView<Receipt> infoReceipt;
    @FXML
    private MFXTableView tableProduct;
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
   public static ObservableList<Product> productWareHouses;
    private ObservableList<ProductDTO> productWareHousesReceipt;
    public static Product selectedProductInTable;
    private ObservableList<Receipt> tableReceiptCustomer;
    private ObservableList<Receipt> tableImportProductInWarehouse;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupTable();
        infoReceiptImportProductUI();
        infoReceiptOfCus();
       getData();
       IProductRepo receipt = RepoFactory.getInstance().getRepo(REPO_TYPE.PRODUCT);
       List<Receipt> receiptList = receipt.getListReceiptCustomer(1,2,"");
        System.out.println("receipt: " + receiptList);
        tableReceiptCustomer = FXCollections.observableArrayList(
        );
        for(Receipt r:receiptList){
            if(r.getType().getId() == 2){
                tableReceiptCustomer.add(r);
            }else {
                tableImportProductInWarehouse = FXCollections.observableArrayList();
            }
        }

        infoReceipt.setItems(tableReceiptCustomer);
    }
    public void importProductInWareHouse(ActionEvent event) {
        isSelectedImportProductInWareHouse = true;
        selectedProductInTable = new Product();
        selectedProductInTable = (Product) tableProduct.getSelectionModel().getSelectedValue();
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
//      ProductWareHouse selectedImportProduct = wareHouseTableView.getSelectionModel().getSelectedItem();
//        productWareHouses.remove(selectedImportProduct);
//        ProductWareHouse selectedImportProductReceipt = wareHouseTableViewRecipt.getSelectionModel().getSelectedItem();
//        productWareHousesReceipt.remove(selectedImportProductReceipt);
    }
    private void getData(){
        IProductRepo productRep = RepoFactory.getInstance().getRepo(REPO_TYPE.PRODUCT);
        List<Product> listProduct = productRep.getListProduct(1, 20, "")
                .stream().map((ProductDTO::getProduct)).toList();
        productWareHouses = FXCollections.observableArrayList(listProduct);
        tableProduct.setItems(productWareHouses);

    }
    private void infoReceiptImportProductUI(){
        MFXTableColumn<Receipt> colId = new MFXTableColumn<>("Id", true);
        MFXTableColumn<Receipt> colName = new MFXTableColumn<>("Tên", true);
        MFXTableColumn<Receipt> colQuantity = new MFXTableColumn<>("Số lượng", true);
        MFXTableColumn<Receipt> colProductImporter = new MFXTableColumn<>("Người nhập", true);
        MFXTableColumn<Receipt> colPriceInput = new MFXTableColumn<>("Giá nhập", true);
        MFXTableColumn<Receipt> colPriceCost = new MFXTableColumn<>("Giá bán", true);
        MFXTableColumn<Receipt> colUnit = new MFXTableColumn<>("Đơn vị tính", true);
        MFXTableColumn<Receipt> colDateImport = new MFXTableColumn<>("Ngày nhập", true);

        colId.setRowCellFactory(receipt -> new MFXTableRowCell<>(Receipt::getId));
        colName.setRowCellFactory(receipt -> new MFXTableRowCell<>(Receipt::getProductName));
        colQuantity.setRowCellFactory(receipt -> new MFXTableRowCell<>(Receipt::getQuantityImport));
        colProductImporter.setRowCellFactory(receipt -> new MFXTableRowCell<>(Receipt::getImporter));
        colPriceInput.setRowCellFactory(receipt -> new MFXTableRowCell<>(Receipt::getInputPrice));
        colPriceCost.setRowCellFactory(receipt -> new MFXTableRowCell<>(Receipt::getOutputPrice));
        colUnit.setRowCellFactory(receipt -> new MFXTableRowCell<>(Receipt::getUnit));
        colDateImport.setRowCellFactory(receipt -> new MFXTableRowCell<>(Receipt::getCreateAt));

        infoImportProduct.getTableColumns().addAll(colId,colName,colQuantity,colProductImporter,colPriceInput,colPriceCost,colUnit,colDateImport);

    }
    private void infoReceiptOfCus(){
        MFXTableColumn<Receipt> colCode = new MFXTableColumn<>("Mã", true);
        MFXTableColumn<Receipt> colCreateAt = new MFXTableColumn<>("Ngày nhập", true);
        MFXTableColumn<Receipt> colPayment = new MFXTableColumn<>("Tổng tiền", true);
        MFXTableColumn<Receipt> colImporter = new MFXTableColumn<>("Người tạo", true);

        colCode.setRowCellFactory(receipt -> new MFXTableRowCell<>(Receipt::getId));
        colCreateAt.setRowCellFactory(receipt -> new MFXTableRowCell<>(Receipt::getCreateAt));
        colPayment.setRowCellFactory(receipt -> new MFXTableRowCell<>(Receipt::getTotalPayment));
        colImporter.setRowCellFactory(receipt -> new MFXTableRowCell<>(Receipt::getImporter));

        infoReceipt.getTableColumns().addAll(colCode,colCreateAt,colPayment,colImporter);
    }
    private void setupTable() {
        MFXTableColumn<Product> idColumn = new MFXTableColumn<>("Id", true);
        MFXTableColumn<Product> nameColumn = new MFXTableColumn<>("Name", true);
        MFXTableColumn<Product> quantityColumn = new MFXTableColumn<>("Quantity", true);
        MFXTableColumn<Product> unitColumn = new MFXTableColumn<>("unit", true);
        MFXTableColumn<Product> inputPrice = new MFXTableColumn<>("inputPrice", true);
        MFXTableColumn<Product> categoryColumn = new MFXTableColumn<>("Category", true);

        nameColumn.setRowCellFactory(product -> new MFXTableRowCell<>(Product::getName));
        idColumn.setRowCellFactory(product -> new MFXTableRowCell<>(Product::getId));
       quantityColumn.setRowCellFactory(product -> new MFXTableRowCell<>(Product::getQuantityAvailable));
        unitColumn.setRowCellFactory(product -> new MFXTableRowCell<>(Product::getUnit));
        inputPrice.setRowCellFactory(product -> new MFXTableRowCell<>(Product::getPriceInit));
        categoryColumn.setRowCellFactory(product -> new MFXTableRowCell<>(Product::getInputPrice));
        tableProduct.getTableColumns().addAll(idColumn,nameColumn,quantityColumn,unitColumn,inputPrice);
    }

    public void refeshData(ActionEvent event) {
        getData();
    }
}
