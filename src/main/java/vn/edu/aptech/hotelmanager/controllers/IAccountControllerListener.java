package vn.edu.aptech.hotelmanager.controllers;

import vn.edu.aptech.hotelmanager.domain.model.Account;

public interface IAccountControllerListener {
    void addNewAccount(Account account);
    void updateAccount(Account account);
}
