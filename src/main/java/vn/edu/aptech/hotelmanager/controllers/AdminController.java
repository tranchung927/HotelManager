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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.val;
import vn.edu.aptech.hotelmanager.HMResourcesLoader;
import vn.edu.aptech.hotelmanager.domain.REPO_TYPE;
import vn.edu.aptech.hotelmanager.domain.RepoFactory;
import vn.edu.aptech.hotelmanager.domain.dto.RoomDTO;
import vn.edu.aptech.hotelmanager.domain.model.Account;
import vn.edu.aptech.hotelmanager.domain.model.Room;
import vn.edu.aptech.hotelmanager.domain.repo.IAccountRepo;
import vn.edu.aptech.hotelmanager.domain.repo.IRoomRepo;
import vn.edu.aptech.hotelmanager.utils.CrudUtil;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.*;
import java.util.function.Function;

import static java.sql.DriverManager.println;

public class AdminController implements Initializable {

    /**************************************************
     * Tab - room
     **************************************************/

    @FXML
    private MFXPaginatedTableView<Account> accountTableView;

    @FXML
    private MFXPaginatedTableView<Room> roomTableView;

    private ObservableList<Account> accounts;
    private ObservableList<Room> rooms;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupPaginated();
        getAccountData();
        setUpRoomTableView();
        getRoomData();
        accountTableView.autosizeColumnsOnInitialization();
        When.onChanged(accountTableView.currentPageProperty())
                .then((oldValue, newValue) -> accountTableView.autosizeColumns())
                .listen();

        roomTableView.autosizeColumnsOnInitialization();
        When.onChanged(roomTableView.currentPageProperty())
                .then((oldValue, newValue) -> roomTableView.autosizeColumns())
                .listen();


    }

    public void addBtn(ActionEvent event) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(HMResourcesLoader.loadURL("fxml/Account.fxml"));
            AccountController accountController = new AccountController(stage, new Account());
            accountController.setListener(new IAccountControllerListener() {
                @Override
                public void addNewAccount(Account account) {
                    accounts.add(account);
                }

                @Override
                public void updateAccount(Account account) {

                }

                @Override
                public void deleteAccount(Account account) {

                }
            });
            loader.setControllerFactory(c -> accountController);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
            stage.setScene(scene);
            stage.setTitle("Account");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void selectedAccount(Account account) {

        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(HMResourcesLoader.loadURL("fxml/Account.fxml"));
            AccountController accountController = new AccountController(stage, account);
            accountController.setListener(new IAccountControllerListener() {
                @Override
                public void addNewAccount(Account account) {
                }

                @Override
                public void updateAccount(Account account) {
                    for (int i = 0; i < accounts.size(); i++) {
                        if (account.getId() == accounts.get(i).getId()) {
                            accounts.set(i, account);
                            break;
                        }
                    }
                }

                @Override
                public void deleteAccount(Account account) {
                    accounts.remove(account);
                }
            });
            loader.setControllerFactory(c -> accountController);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
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
        MFXTableColumn<Account> nameColumn = new MFXTableColumn<>("Name", false, Comparator.comparing(Account::getFullName));
        MFXTableColumn<Account> emailColumn = new MFXTableColumn<>("Email", false, Comparator.comparing(Account::getEmail));
        MFXTableColumn<Account> phoneColumn = new MFXTableColumn<>("Phone Number", false, Comparator.comparing(Account::getPhoneNumber));
        MFXTableColumn<Account> dobColumn = new MFXTableColumn<>("DOB", false, Comparator.comparing(Account::getDOBFormat));
        MFXTableColumn<Account> sexColumn = new MFXTableColumn<>("Sex", false, Comparator.comparing(Account::getGenderName));
        MFXTableColumn<Account> positionColumn = new MFXTableColumn<>("Position", false, Comparator.comparing(Account::getPositionName));
        MFXTableColumn<Account> userNColumn = new MFXTableColumn<>("Username", false, Comparator.comparing(Account::getUsername));
        MFXTableColumn<Account> passColumn = new MFXTableColumn<>("Password", false, Comparator.comparing(Account::getPassword));


        idColumn.setRowCellFactory(account -> new MFXTableRowCell<>(Account::getId) {{
            setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (account != null)) {
                    selectedAccount(account);

                }
            });
        }});
        nameColumn.setRowCellFactory(account -> new MFXTableRowCell<>(Account::getFullName) {{
            setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (account != null)) {
                    selectedAccount(account);

                }
            });
        }});
        emailColumn.setRowCellFactory(account -> new MFXTableRowCell<>(Account::getEmail) {{
            setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (account != null)) {
                    selectedAccount(account);

                }
            });
        }});
        phoneColumn.setRowCellFactory(account -> new MFXTableRowCell<>(Account::getPhoneNumber) {{
            setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (account != null)) {
                    selectedAccount(account);
                }
            });
        }});
        dobColumn.setRowCellFactory(account -> new MFXTableRowCell<>(Account::getDob) {{
            setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (account != null)) {
                    selectedAccount(account);
                }
            });
        }});
        sexColumn.setRowCellFactory(account -> new MFXTableRowCell<>(Account::getGenderName) {{
            setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (account != null)) {
                    selectedAccount(account);
                }
            });
        }});
        positionColumn.setRowCellFactory(account -> new MFXTableRowCell<>(Account::getPositionName) {{
            setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (account != null)) {
                    selectedAccount(account);
                }
            });
        }});
        userNColumn.setRowCellFactory(account -> new MFXTableRowCell<>(Account::getUsername) {{
            setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (account != null)) {
                    selectedAccount(account);
                }
            });
        }});
        passColumn.setRowCellFactory(account -> new MFXTableRowCell<>(Account::getPassword) {{
            setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (account != null)) {
                    selectedAccount(account);
                }
            });
        }});


        accountTableView.getTableColumns().addAll(
                idColumn, nameColumn, emailColumn,
                phoneColumn, dobColumn, sexColumn,
                positionColumn, userNColumn, passColumn
        );
        accountTableView.getFilters().addAll(
                new StringFilter<>("Name", Account::getFullName),
                new StringFilter<>("Email", Account::getEmail),
                new StringFilter<>("Phone", Account::getPhoneNumber)
        );
    }

    private void getAccountData() {
        IAccountRepo repo = RepoFactory.getInstance().getRepo(REPO_TYPE.ACCOUNT);
        accounts.addAll(repo.getListAccount(1, 30));
        accountTableView.setItems(accounts);
    }


    public void addRoomBtn() {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(HMResourcesLoader.loadURL("fxml/Room.fxml"));
            RoomController roomController = new RoomController(stage,null);
            roomController.setListener(new IRoomController() {

                @Override
                public void addNewRoom(Room room) {
                    rooms.add(room);
                }

                @Override
                public void updateRoom(Room room) {

                }

                @Override
                public void deleteRoom(Room room) {

                }
            });

            loader.setControllerFactory(c -> roomController);
            Parent root = loader.
                    load();
            Scene scene = new Scene(root);
            MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
            stage.setScene(scene);
            stage.setTitle("Room");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setUpRoomTableView(){
        rooms = FXCollections.observableArrayList();
        rooms.addListener(new ListChangeListener<Room>() {
            @Override
            public void onChanged(Change<? extends Room> change) {

            }
        });

        MFXTableColumn<Room> idRoomColumn = new MFXTableColumn<>("Id",false,Comparator.comparing(Room::getId));
        MFXTableColumn<Room> nameRoomColumn = new MFXTableColumn<>("Name",false,Comparator.comparing(Room::getName));
        MFXTableColumn<Room> statusColumn = new MFXTableColumn<>("Status",false,Comparator.comparing(Room::getStatus));
        MFXTableColumn<Room> nobColumn = new MFXTableColumn<>("Number of bed",false,Comparator.comparing(Room::getNumberOfBeds));
        MFXTableColumn<Room> priceColumn = new MFXTableColumn<>("Price",false,Comparator.comparing(Room::getPrice));
        MFXTableColumn<Room> categoryColumn = new MFXTableColumn<>("Categoty",false,Comparator.comparing(Room::getCategoryId));
        idRoomColumn.setRowCellFactory(room -> new MFXTableRowCell<>(Room::getId) {{
            setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (room != null)) {
                    selectedRoom(room);

                }
            });
        }});
        nameRoomColumn.setRowCellFactory(room -> new MFXTableRowCell<>(Room::getName) {{
            setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (room != null)) {
                    selectedRoom(room);

                }
            });
        }});
        statusColumn.setRowCellFactory(room -> new MFXTableRowCell<>(Room::getStatus) {{
            setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (room != null)) {
                    selectedRoom(room);

                }
            });
        }});
        nobColumn.setRowCellFactory(room -> new MFXTableRowCell<>(Room::getNumberOfBeds) {{
            setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (room != null)) {
                    selectedRoom(room);

                }
            });
        }});
        priceColumn.setRowCellFactory(room -> new MFXTableRowCell<>(Room::getPrice) {{
            setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (room != null)) {
                    selectedRoom(room);

                }
            });
        }});
        categoryColumn.setRowCellFactory(room -> new MFXTableRowCell<>(Room::getCategoryId) {{
            setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (room != null)) {
                    selectedRoom(room);

                }
            });
        }});

        roomTableView.getTableColumns().addAll(idRoomColumn, nameRoomColumn, statusColumn, nobColumn, priceColumn, categoryColumn);

//        roomTableView.getFilters().addAll(
//                new StringFilter<>("Name", Room::getName),
//                new StringFilter<>("Status", Room::getStatus)
//
//        );
    }
    private void getRoomData() {

        IRoomRepo repo = RepoFactory.getInstance().getRepo(REPO_TYPE.ROOM);
        rooms.addAll(repo.getListRoom(1, 30).stream().map(RoomDTO::getRoom).toList());
        roomTableView.setItems(rooms);
    }




    private void selectedRoom(Room room) {
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(HMResourcesLoader.loadURL("fxml/Room.fxml"));
            RoomController roomController = new RoomController(stage,room);
            roomController.setListener(new IRoomController() {

                @Override
                public void addNewRoom(Room room) {

                }

                @Override
                public void updateRoom(Room room) {
                    for (int i = 0; i < rooms.size(); i++) {
                        if (room.getId() == rooms.get(i).getId()) {
                            rooms.set(i, room);
                            break;
                        }
                    }

                }

                @Override
                public void deleteRoom(Room room) {
                    rooms.remove(room);

                }
            });

            loader.setControllerFactory(c -> roomController);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
            stage.setScene(scene);
            stage.setTitle("Room");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}


