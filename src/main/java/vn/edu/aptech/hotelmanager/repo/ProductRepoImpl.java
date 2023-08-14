package vn.edu.aptech.hotelmanager.repo;

import javafx.scene.control.Alert;
import vn.edu.aptech.hotelmanager.domain.dto.ProductDTO;
import vn.edu.aptech.hotelmanager.domain.model.*;
import vn.edu.aptech.hotelmanager.domain.repo.IProductRepo;
import vn.edu.aptech.hotelmanager.repo.converter.*;
import vn.edu.aptech.hotelmanager.utils.CrudUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductRepoImpl implements IProductRepo {
    @Override
    public List<ProductDTO> getListProduct(int page, int pageSize, String searchKey) {
        List<ProductDTO> productDTOList =  new ArrayList<>();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM products WHERE status = 1");
            while (resultSet.next()) {
                Product product = new ProductEntityToProduct().convert(resultSet);
                long pricePolicyId = resultSet.getLong("price_policy_id");
                long inventory = resultSet.getLong("inventory_id");
                //System.out.println(inventory);
                ResultSet ppResultSet = CrudUtil.execute("SELECT * FROM price_policies WHERE id=?", pricePolicyId);

                ResultSet invenResultSet = CrudUtil.execute("SELECT * FROM inventories WHERE id = ?",inventory);
                if (ppResultSet.next()){
                    product.setPricePolicy(new PricePolicyEntityToPricePolicy().convert(ppResultSet));
                }
                if(invenResultSet.next()){
                    product.setInventory(new InventoryEntityToInventory().convert(invenResultSet));
                }
                ProductDTO productDTO = new ProductDTO();
                productDTO.setProduct(product);
                productDTOList.add(productDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productDTOList;
    }

    @Override
    public List<PricePolicy> getListPricePolicy(int page, int pageSize, String searchKey) {
        List<PricePolicy> pricePolicies = new ArrayList<>();
        try{
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM `price_policies`");
            while(resultSet.next()){
                PricePolicy pricePolicy = new PricePolicyEntityToPricePolicy().convert(resultSet);
                pricePolicies.add(pricePolicy);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return pricePolicies;
    }

    @Override
    public List<Inventory> getListInventory(int page, int paSize, String searchKey) {
        List<Inventory> inventories = new ArrayList<>();
        try{
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM `inventories`");
            while (resultSet.next()){
               Inventory inventory = new InventoryEntityToInventory().convert(resultSet);
               inventories.add(inventory);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return inventories;
    }

    @Override
    public void insertPricePolicy(PricePolicy pricePolicy) throws Exception {
        String sqlPricePolicy = "INSERT INTO price_policies(init_price,cost_price) " +
                "VALUES ('"+pricePolicy.getInitPrice()+"','"+pricePolicy.getCostPrice()+"')";
        CrudUtil.execute(sqlPricePolicy);
    }

    @Override
    public void insertProducts(Product product) throws Exception {
        String sqlProduct = "INSERT INTO `products`(name,unit,price_policy_id,inventory_id,status)" +
                " VALUES ('"+product.getName()+"'" +
                ",'"+product.getUnit()+"'" +
                ",'"+product.getPricePolicy().getId()+"'" +
                ",'"+product.getInventory().getId()+"'" +
                ",'"+product.getStatus()+"')";
        Alert alert;
        try {
            boolean a = CrudUtil.execute(sqlProduct);
            if(a){

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Add Successfully");
                alert.showAndWait();
            }else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertInventory(Inventory inventory) throws Exception {
        String sqlInventory = "INSERT INTO `inventories`(available_quantity) VALUES('"+inventory.getAvailableQuantity()+"')";
        CrudUtil.execute(sqlInventory);
    }

    @Override
    public void updateProductInWareHouse(Double quatity, long id) throws Exception {
        String updateQuanity = "UPDATE `inventories` SET `available_quantity`= '"+quatity+"' WHERE id = '"+id+"'";
        Alert alert;
        try {
            boolean a = CrudUtil.execute(updateQuanity);
            if(a){

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Update Successfully");
                alert.showAndWait();
            }else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateProductInSales(Double quatity, long id) throws Exception {
        String updateQuanity = "UPDATE `inventories` SET `available_quantity`= '"+quatity+"' WHERE id = '"+id+"'";
        CrudUtil.execute(updateQuanity);
    }

    @Override
    public void deleteProduct(Long selected) throws Exception {
        String sql = "UPDATE products SET status = 2 WHERE id = '"+selected+"'";
        Alert alert;
        try {
            boolean a = CrudUtil.execute(sql);
            if(a){

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Delete Successfully");
                alert.showAndWait();
            }else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    @Override
//    public ProductDTO createOrUpdate(ProductDTO productDTO) throws Exception {
//
//    }
}
