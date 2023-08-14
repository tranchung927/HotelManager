package vn.edu.aptech.hotelmanager.domain.repo;

import vn.edu.aptech.hotelmanager.common.repo.IRepo;
import vn.edu.aptech.hotelmanager.domain.dto.ProductDTO;
import vn.edu.aptech.hotelmanager.domain.model.*;

import java.util.List;

public interface IProductRepo extends IRepo {
    List<ProductDTO> getListProduct(int page, int pageSize, String searchKey);
    //ProductDTO createOrUpdate(ProductDTO productDTO) throws Exception;
   List<PricePolicy> getListPricePolicy(int page,int pageSize,String searchKey);
   List<Inventory> getListInventory(int page,int paSize,String searchKey);
   void insertPricePolicy(PricePolicy pricePolicy) throws Exception;
   void insertProducts(Product product) throws Exception;
   void insertInventory(Inventory inventory) throws Exception;
   void updateProductInWareHouse(Double quatity,long id) throws Exception;
    void updateProductInSales(Double quatity,long id) throws Exception;
    void deleteProduct(Long selected) throws Exception;
}
