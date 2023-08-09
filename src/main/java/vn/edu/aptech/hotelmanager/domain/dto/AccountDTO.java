package vn.edu.aptech.hotelmanager.domain.dto;

import lombok.Data;
import vn.edu.aptech.hotelmanager.domain.model.Account;
import vn.edu.aptech.hotelmanager.domain.model.Address;
import vn.edu.aptech.hotelmanager.domain.model.Position;

@Data
public class AccountDTO {
    private Account account;
    private Position position;
    private Address address;
}
