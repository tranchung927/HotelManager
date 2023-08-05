package vn.edu.aptech.hotelmanager.repo.converter;

import lombok.NonNull;
import vn.edu.aptech.hotelmanager.domain.model.*;

import java.sql.ResultSet;

public class AccountEntityToAccount implements IEntityConverter<Account>{
    @Override
    public Account convert(@NonNull ResultSet source) {
        Account account = new Account();
        try {
            account.setId(source.getLong("id"));
            account.setFirstName(source.getString("first_name"));
            account.setLastName(source.getString("last_name"));
            account.setEmail(source.getString("email"));
            account.setPhoneNumber(source.getString("phone_number"));
            account.setDob(source.getDate("dob"));
            account.setCode(source.getString("code"));
            GENDER_TYPE genderType = GENDER_TYPE.valueOfStatus(source.getInt("sex"));
            account.setGender(genderType);
            account.setStatus(source.getInt("status"));
            account.setCreatedAt(source.getDate("created_at"));
            account.setModifiedAt(source.getDate("modified_at"));
            account.setDescription(source.getString("description"));
            account.setRole(source.getString("role"));
            account.setUsername(source.getString("username"));
            account.setPassword(source.getString("password"));
            Position position = new Position();
            position.setId(source.getLong("position_id"));
            position.setName(source.getString("position_name"));
            account.setPosition(position);
            account.setAddressId(source.getLong("address_id"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }
}
