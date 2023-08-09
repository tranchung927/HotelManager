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
   List<ReceiptType> getListReceiptType();
   List<Receipt> getListReceiptCustomer(int paga,int pageSize,String searchKey);
    List<Receipt> getListReceiptImportInWareHouse(int paga,int pageSize,String searchKey);
}
