package vn.edu.aptech.hotelmanager.repo;

import vn.edu.aptech.hotelmanager.domain.dto.AccountDTO;
import vn.edu.aptech.hotelmanager.domain.model.Account;
import vn.edu.aptech.hotelmanager.domain.model.Address;
import vn.edu.aptech.hotelmanager.domain.model.GENDER_TYPE;
import vn.edu.aptech.hotelmanager.domain.model.Position;
import vn.edu.aptech.hotelmanager.domain.repo.IAccountRepo;
import vn.edu.aptech.hotelmanager.repo.converter.AccountEntityToAccount;
import vn.edu.aptech.hotelmanager.repo.converter.PositionEntityToPosition;
import vn.edu.aptech.hotelmanager.repo.db.DBConnection;
import vn.edu.aptech.hotelmanager.utils.CrudUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AccountRepoImpl implements IAccountRepo {
    @Override
    public List<Account> getListAccount(int page, int pageSize) {
        List<Account> accountList =  new ArrayList<>();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT accounts.id, first_name, last_name, email, phone_number, " +
                    "dob, accounts.code, sex, accounts.status, accounts.created_at, accounts.modified_at, description, role," +
                    " username, password, position_id, positions.name AS position_name,addresses.id AS address_id " +
                    "FROM accounts " +
                    "INNER JOIN positions ON positions.id = accounts.position_id " +
                    "INNER JOIN addresses ON addresses.id = accounts.address_id");
            while (resultSet.next()) {
                accountList.add(new AccountEntityToAccount().convert(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accountList;
    }

    @Override
    public AccountDTO createOrUpdate(AccountDTO accountDTO) throws Exception {
        Address address = accountDTO.getAddress();
        ResultSet addressRst = CrudUtil.execute("SELECT * FROM addresses WHERE id=?", address.getId());
        if (addressRst.next()) {
            String sql = "UPDATE addresses SET country_id =?, city_id = ?, district_id = ?, full_address = ? WHERE addresses.id = ?";
            CrudUtil.execute(sql, address.getCountry().getId(), address.getCity().getId(), address.getDistrict().getId(), address.getFullAddress(), address.getId());
        } else {
            String sql = "INSERT INTO addresses(country_id,city_id,district_id,full_address) VALUES (?,?,?,?)";
            CrudUtil.execute(sql, address.getCountry().getId(), address.getCity().getId(), address.getDistrict().getId(), address.getFullAddress());
            ResultSet rst = CrudUtil.execute("SELECT * FROM addresses ORDER BY id DESC LIMIT 1");
            address.setId(rst.getLong("id"));
        }
        ResultSet accountRst = CrudUtil.execute("SELECT * FROM accounts WHERE id=?", accountDTO.getAccount().getId());
        String sql;
        if (accountRst.next()) {
            sql = "UPDATE accounts SET first_name = ?, last_name = ?," +
                    " email = ?, phone_number = ?, dob = ?, sex = ?," +
                    " role = ?, username = ?, password = ?, position_id = ?, address_id = ?" +
                    " WHERE accounts.id = ?";
        } else {
            sql = "INSERT INTO accounts(first_name,last_name,email,phone_number,dob,sex,role,username,password,position_id,address_id)" +
                    " VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            ResultSet rst = CrudUtil.execute("SELECT * FROM accounts ORDER BY id DESC LIMIT 1");
            accountDTO.getAccount().setId(rst.getLong("id"));
        }
        CrudUtil.execute(sql, accountDTO.getAccount().getFirstName(),
                accountDTO.getAccount().getLastName(),
                accountDTO.getAccount().getEmail(),
                accountDTO.getAccount().getPhoneNumber(),
                java.sql.Date.valueOf(accountDTO.getAccount().getDOBFormat()),
                accountDTO.getAccount().getGender().toStatus(),
                accountDTO.getAccount().getRole().toName(),
                accountDTO.getAccount().getUsername(),
                accountDTO.getAccount().getPassword(),
                accountDTO.getPosition().getId(), address.getId(),
                accountDTO.getAccount().getId());
        return accountDTO;
    }
    @Override
    public String getLastAccountId() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM accounts ORDER BY id DESC LIMIT 1");
        if (!rst.next()) {
            return null;
        } else {
            return  rst.getString(1);
        }
    }

    @Override
    public List<Position> getListPosition() {
        List<Position> positionList =  new ArrayList<>();
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM positions");
            while (resultSet.next()) {
                positionList.add(new PositionEntityToPosition().convert(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return positionList;
    }

    @Override
    public Boolean deleteAccount(long id) throws Exception {
        String url = "DELETE FROM accounts WHERE id = ?";
        CrudUtil.execute(url, id);
        return true;
    }
}