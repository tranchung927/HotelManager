package vn.edu.aptech.hotelmanager.repo;

import vn.edu.aptech.hotelmanager.domain.model.Account;
import vn.edu.aptech.hotelmanager.domain.model.GENDER_TYPE;
import vn.edu.aptech.hotelmanager.domain.model.Position;
import vn.edu.aptech.hotelmanager.domain.repo.IAccountRepo;
import vn.edu.aptech.hotelmanager.repo.converter.AccountEntityToAccount;
import vn.edu.aptech.hotelmanager.utils.CrudUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AccountRepoImpl implements IAccountRepo {
    @Override
    public List<Account> getListAccount(int page, int pageSize) {
        List<Account> accountList =  new ArrayList<>();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT id, first_name, last_name, email, phone_number," +
                    " dob, code, sex, status, created_at, modified_at," +
                    " description, role, username, password, position_id, position.name AS position_name" +
                    " FROM accounts " +
                    "INNER JOIN position ON position.id = accounts.position_id");
            while (resultSet.next()) {
                accountList.add(new AccountEntityToAccount().convert(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accountList;
    }

    @Override
    public Account createOrUpdateAccount(Account account) throws Exception {
        if (account.getId() > 0) {
            // UPDATE
        } else {
            // CREATE
            // TODO
            // SAU KHI CREATE xong thì query account mới nhất
            ResultSet rst = CrudUtil.execute("SELECT * FROM accouts ORDER BY RoomId DESC LIMIT 1");
            if (!rst.next()) {
                return new AccountEntityToAccount().convert(rst);
            }
        }
        return null;
    }
}