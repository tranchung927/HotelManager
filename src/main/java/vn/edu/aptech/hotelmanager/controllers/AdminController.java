package vn.edu.aptech.hotelmanager.controllers;

import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import io.github.palexdev.materialfx.utils.others.observables.When;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import vn.edu.aptech.hotelmanager.HMResourcesLoader;
import vn.edu.aptech.hotelmanager.domain.model.Account;
import vn.edu.aptech.hotelmanager.utils.CrudUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.*;

public class AdminController implements Initializable {

    private final Stage stage;
    /**************************************************
     * Tab - room
     **************************************************/

    @FXML
    private MFXPaginatedTableView<Account> accountTableView;
    private ObservableList<Account> accounts;

    public AdminController(Stage stage) {
        this.stage = stage;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupPaginated();
        getAccountData();
        accountTableView.autosizeColumnsOnInitialization();
        When.onChanged(accountTableView.currentPageProperty())
                .then((oldValue, newValue) -> accountTableView.autosizeColumns())
                .listen();
    }

    public void addBtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(HMResourcesLoader.loadURL("fxml/Account.fxml"));
            AccountController accountController = new AccountController(stage, null);
            accountController.setListener(new IAccountControllerListener() {
                @Override
                public void addNewAccount(Account account) {
                    accounts.add(account);
                }
                @Override
                public void updateAccount(Account account) {

                }
            });
            loader.setControllerFactory(c -> accountController);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.setTitle("Account");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void selectRow() {
        Account currentSelected = accountTableView.getSelectionModel().getSelectedValue();
        int index = accountTableView.getSelectionModel().getSelectedValues().indexOf(currentSelected);
        try {
            FXMLLoader loader = new FXMLLoader(HMResourcesLoader.loadURL("fxml/Account.fxml"));
            AccountController accountController = new AccountController(stage, currentSelected);
            accountController.setListener(new IAccountControllerListener() {
                @Override
                public void addNewAccount(Account account) {}
                @Override
                public void updateAccount(Account account) {
                    accounts.set(index, account);
                }
            });
            loader.setControllerFactory(c -> accountController);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.setTitle("Account");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void setupPaginated() {
        accounts = FXCollections.observableArrayList();
        accounts.addListener(new ListChangeListener<Account>() {
            @Override
            public void onChanged(Change<? extends Account> change) {

            }
        });
        MFXTableColumn<Account> idColumn = new MFXTableColumn<>("ID", false, Comparator.comparing(Account::getId));
        MFXTableColumn<Account> nameColumn = new MFXTableColumn<>("Name", false, Comparator.comparing(Account::getName));
        MFXTableColumn<Account> emailColumn = new MFXTableColumn<>("Email", false, Comparator.comparing(Account::getEmail));
        MFXTableColumn<Account> phoneColumn = new MFXTableColumn<>("Phone Number", false, Comparator.comparing(Account::getPhone));
        MFXTableColumn<Account> dobColumn = new MFXTableColumn<>("DOB", false, Comparator.comparing(Account::getSex));
        MFXTableColumn<Account> sexColumn = new MFXTableColumn<>("Sex", false, Comparator.comparing(Account::getPosition));
        MFXTableColumn<Account> positionColumn = new MFXTableColumn<>("Position", false, Comparator.comparing(Account::getPosition));
        MFXTableColumn<Account> userNColumn = new MFXTableColumn<>("User Name", false, Comparator.comparing(Account::getUserName));
        MFXTableColumn<Account> passColumn = new MFXTableColumn<>("Password", false, Comparator.comparing(Account::getPassword));

        idColumn.setRowCellFactory(account -> new MFXTableRowCell<>(Account::getId));
        nameColumn.setRowCellFactory(account -> new MFXTableRowCell<>(Account::getName));
        emailColumn.setRowCellFactory(account -> new MFXTableRowCell<>(Account::getEmail));
        phoneColumn.setRowCellFactory(account -> new MFXTableRowCell<>(Account::getPhone));
        dobColumn.setRowCellFactory(account -> new MFXTableRowCell<>(Account::getDob));
        sexColumn.setRowCellFactory(account -> new MFXTableRowCell<>(Account::getSex));
        positionColumn.setRowCellFactory(account -> new MFXTableRowCell<>(Account::getPosition));
        userNColumn.setRowCellFactory(account -> new MFXTableRowCell<>(Account::getUserName));
        passColumn.setRowCellFactory(account -> new MFXTableRowCell<>(Account::getPassword));

        accountTableView.getTableColumns().addAll(idColumn, nameColumn, emailColumn, phoneColumn, dobColumn, sexColumn, positionColumn, userNColumn, passColumn);
        accountTableView.getFilters().addAll(
                new IntegerFilter<>("ID", Account::getId),
                new StringFilter<>("Name", Account::getName),
                new StringFilter<>("Email", Account::getEmail),
                new StringFilter<>("Phone", Account::getPhone)
        );
    }
    private void getAccountData() {
        ResultSet resultSet = null;
        String url = "SELECT accounts.id,first_name,last_name,email,phone_number,dob,sex,position.name,username,password " +
                "FROM accounts " +
                "INNER JOIN position ON position.id = accounts.position_id";
        try {
            Account account;
            resultSet = CrudUtil.execute(url);
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String first_name = resultSet.getString(2);
                String last_name = resultSet.getString(3);
                String name = first_name + " " + last_name;
                String email = resultSet.getString(4);
                String phone = resultSet.getString(5);
                Date dob = resultSet.getDate(6);
                String sex = resultSet.getString(7);
                String position_name = resultSet.getString(8);
                String user_name = resultSet.getString(9);
                String pass = resultSet.getString(10);
                account = new Account(id, name, email, phone, dob, sex, position_name, user_name, pass);
                accounts.add(account);
                accountTableView.setItems(accounts);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


