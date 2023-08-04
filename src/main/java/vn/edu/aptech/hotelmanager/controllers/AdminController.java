package vn.edu.aptech.hotelmanager.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPaginatedTableView;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import io.github.palexdev.materialfx.enums.ScrimPriority;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import io.github.palexdev.materialfx.utils.others.observables.When;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        setupPaginated();

        paginated.autosizeColumnsOnInitialization();
        When.onChanged(paginated.currentPageProperty())
                .then((oldValue, newValue) -> paginated.autosizeColumns())
                .listen();


    }

    @FXML
    private MFXPaginatedTableView<Account> paginated;

    private void setupPaginated() {

        ObservableList<Account> accounts = FXCollections.observableArrayList();
        ResultSet resultSet = null;
        String url = "SELECT account.id,first_name,last_name,email,phone_number,dob,sex,position.name,username,password " +
                "FROM account " +
                "INNER JOIN position ON position.id = account.position_id";


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

                account = new Account(id, name, email, phone, dob, sex, position_name, user_name,pass);
                accounts.add(account);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


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



        paginated.getTableColumns().addAll(idColumn, nameColumn, emailColumn, phoneColumn, dobColumn,sexColumn,positionColumn,userNColumn,passColumn);
        paginated.getFilters().addAll(
                new IntegerFilter<>("ID", Account::getId),
                new StringFilter<>("Name", Account::getName),
                new StringFilter<>("Email", Account::getEmail),
                new StringFilter<>("Phone", Account::getPhone)

        );
        paginated.setItems(accounts);
    }

    public AdminController(Stage stage) {
        this.stage = stage;
    }


    public void addBtn(ActionEvent event) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("fxml/Account.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
//                scene.setFill(Color.TRANSPARENT);
//            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(scene);
            stage.setTitle("Account Managerment ");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}


