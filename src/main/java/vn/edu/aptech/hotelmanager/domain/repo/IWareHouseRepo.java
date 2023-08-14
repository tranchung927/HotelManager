package vn.edu.aptech.hotelmanager.domain.repo;

import vn.edu.aptech.hotelmanager.common.repo.IRepo;
import vn.edu.aptech.hotelmanager.domain.dto.ProductDTO;
import vn.edu.aptech.hotelmanager.domain.model.Receipt;

import java.util.List;

public interface IWareHouseRepo extends IRepo {
    List<Receipt> getListReceiptCustomer(int paga, int pageSize, String searchKey);
    List<Receipt> getListReceiptImportInWareHouse(int paga,int pageSize,String searchKey);
    void insertReceiptCustomer(Receipt receipt) throws Exception;
    void insertReceiptWareHouse(Receipt receipt) throws Exception;
    void deleteRececeipt(Long selected) throws Exception;
}
