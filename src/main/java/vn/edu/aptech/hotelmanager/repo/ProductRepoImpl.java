package vn.edu.aptech.hotelmanager.repo;

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
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM products");
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
    public List<ReceiptType> getListReceiptType() {
        List<ReceiptType> receipttypeList = new ArrayList<>();
        try{
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM `receipt_type`");
            while (resultSet.next()){
                ReceiptType receiptType = new ReceiptTypeEntityToReceipt().convert(resultSet);
                receipttypeList.add(receiptType);
            }
        }catch (Exception e){
        e.printStackTrace();
        }
        return receipttypeList;
    }
    @Override
    public List<Receipt> getListReceiptCustomer(int paga, int pageSize, String searchKey) {
        List<Receipt> receipts = new ArrayList<>();
        try{
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM `product_receipts`");
            while (resultSet.next()){
                Receipt receipt = new ReceiptEntityToCustomerAndInputProduct().convert(resultSet);
                long typeProductId = resultSet.getInt("type");
                ResultSet resultSetType = CrudUtil.execute("SELECT * FROM `receipt_type` WHERE id = ?",typeProductId);
                if(resultSetType.next()){
                    receipt.setType(new ReceiptTypeEntityToReceipt().convert(resultSetType));
                }
                receipts.add(receipt);
            }
        }catch (Exception e){

        }
        return receipts;
    }

    @Override
    public List<Receipt> getListReceiptImportInWareHouse(int paga, int pageSize, String searchKey) {
        return null;
    }



//    @Override
//    public ProductDTO createOrUpdate(ProductDTO productDTO) throws Exception {
//
//    }
}
