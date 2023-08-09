package vn.edu.aptech.hotelmanager.domain.repo;

import vn.edu.aptech.hotelmanager.common.repo.IRepo;
import vn.edu.aptech.hotelmanager.domain.dto.AccountDTO;
import vn.edu.aptech.hotelmanager.domain.model.Account;
import vn.edu.aptech.hotelmanager.domain.model.Position;

import java.util.List;

public interface IAccountRepo extends IRepo {
    List<Account> getListAccount(int page, int pageSize);
    String getLastAccountId() throws Exception;
    AccountDTO createOrUpdate(AccountDTO accountDTO) throws Exception;
    List<Position> getListPosition();
    Boolean deleteAccount(long id) throws Exception;
}
