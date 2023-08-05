package vn.edu.aptech.hotelmanager.domain.repo;

import vn.edu.aptech.hotelmanager.common.repo.IRepo;
import vn.edu.aptech.hotelmanager.domain.model.Account;

import java.util.List;

public interface IAccountRepo extends IRepo {
    List<Account> getListAccount(int page, int pageSize);
    Account createOrUpdateAccount(Account account) throws Exception;
}
