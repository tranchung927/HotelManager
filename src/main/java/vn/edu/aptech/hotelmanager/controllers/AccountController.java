package vn.edu.aptech.hotelmanager.controllers;

import io.github.palexdev.materialfx.controls.*;

import io.github.palexdev.materialfx.utils.DateTimeUtils;
import io.github.palexdev.materialfx.utils.others.dates.DateStringConverter;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Supplier;
import java.util.regex.Pattern;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import vn.edu.aptech.hotelmanager.domain.REPO_TYPE;
import vn.edu.aptech.hotelmanager.domain.RepoFactory;
import vn.edu.aptech.hotelmanager.domain.model.Account;
import vn.edu.aptech.hotelmanager.domain.model.GENDER_TYPE;
import vn.edu.aptech.hotelmanager.domain.model.Position;
import vn.edu.aptech.hotelmanager.repo.AccountRepoImpl;
import vn.edu.aptech.hotelmanager.repo.db.DBConnection;
import vn.edu.aptech.hotelmanager.utils.CrudUtil;

import static vn.edu.aptech.hotelmanager.domain.model.Model.accounts;


public class AccountController implements Initializable {
    private static final PseudoClass INVALID_PSEUDO_CLASS = PseudoClass.getPseudoClass("invalid");
    @FXML
    private MFXFontIcon alwaysOnTopIcon;

    @FXML
    private MFXFontIcon closeIcon;

    @FXML
    private MFXFontIcon minimizeIcon;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private MFXDatePicker dobDatePicker;

    @FXML
    private MFXTextField emailTextField;

    @FXML
    private MFXTextField phoneTextField;

    @FXML
    private MFXTextField firstNameTextField;

    @FXML
    private MFXTextField lastNameTextField;

    @FXML
    private MFXPasswordField passwordTextField;

    @FXML
    private MFXTextField usernameTextField;

    @FXML
    private MFXButton saveBtn;

    @FXML
    private MFXComboBox<?> countryComboBox;

    @FXML
    private MFXComboBox<?> cityComboBox;

    @FXML
    private MFXComboBox<?> districtComboBox;


    @FXML
    private MFXComboBox<?> sexComboBox;

    @FXML
    private MFXComboBox<?> positionComboBox;


    private final Stage stage;
    private double x = 0;
    private double y = 0;
    private IAccountControllerListener listener;
    private Account account;
    private String[] sexs = {"Male", "Female", "Other"};
    private String[] positions = {"Reception", "Housekeeping", "Manager", "Security"};

    public AccountController(Stage stage, Account account) {
        this.stage = stage;
        this.account = account == null ? new Account() : account;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        dobDatePicker.setGridAlgorithm(DateTimeUtils::partialIntMonthMatrix);
        dobDatePicker.setConverterSupplier(() -> new DateStringConverter("dd/MM/yyyy", dobDatePicker.getLocale()));
        closeIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> ((Stage) rootPane.getScene().getWindow()).close());
        minimizeIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> ((Stage) rootPane.getScene().getWindow()).setIconified(true));
        alwaysOnTopIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            boolean newVal = !stage.isAlwaysOnTop();
            alwaysOnTopIcon.pseudoClassStateChanged(PseudoClass.getPseudoClass("always-on-top"), newVal);
            stage.setAlwaysOnTop(newVal);
        });

        addValueSex();
        addValuePositions();
        addValueCCD();

        updateUI();
    }

    public void setListener(IAccountControllerListener listener) {
        this.listener = listener;
    }





    ///////////////////////////////////////////////////////////////

    @FXML
    public void addValueSex() {


        List<String> list = new ArrayList<>();

        for (String item : sexs) {
            list.add(item);

        }
        ObservableList observabledList = FXCollections.observableList(list);
        sexComboBox.setItems(observabledList);
    }

    /////////////////////////////////////////////////


    @FXML
    public void addValuePositions() {


        List<String> listPositions = new ArrayList<>();

        for (String item : positions) {
            listPositions.add(item);

        }
        ObservableList list = FXCollections.observableList(listPositions);
        positionComboBox.setItems(list);
    }


    @FXML
    public void addValueCCD() {

        String url = "SELECT name FROM countries";
        try {
            ResultSet resultSet = CrudUtil.execute(url);

            List<String> list = new ArrayList<>();
            while (resultSet.next()) {
                String countries_name = resultSet.getString(1);
                list.add(countries_name);
            }

            ObservableList observableList = FXCollections.observableList(list);
            countryComboBox.setItems(observableList);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//////////////////////////////////////////////////
        String country_select = (String) countryComboBox.getSelectionModel().getSelectedItem();
//        System.out.println(country_select);
        String url1 = "SELECT id,name FROM cities WHERE cities.country_id = (SELECT countries.id FROM countries WHERE countries.name = '" + country_select + "')";
        try {
            ResultSet resultSet = CrudUtil.execute(url1);
            List<String> list = new ArrayList<>();
            while (resultSet.next()) {
                String cities = resultSet.getString(2);
                list.add(cities);
            }
            ObservableList observableList = FXCollections.observableList(list);
            cityComboBox.setItems(observableList);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ///////////////////////////////////////////////

        String city_select = (String) cityComboBox.getSelectionModel().getSelectedItem();
//        System.out.println(city_select);

        String url2 = "SELECT id,name FROM districts WHERE districts.city_id = (SELECT cities.id FROM cities WHERE cities.name = '" + city_select + "')";
        try {
            ResultSet resultSet = CrudUtil.execute(url2);
            List<String> list = new ArrayList<>();
            while (resultSet.next()) {
                String districts = resultSet.getString(2);
                list.add(districts);
            }

            ObservableList observableList = FXCollections.observableList(list);
            districtComboBox.setItems(observableList);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public int getAddressId(String ci, String dis) {

        int id = 0;

        String url = "SELECT id " +
                "FROM addresses" +
                " WHERE addresses.city_id = (SELECT id From cities ci WHERE ci.name= '" + ci + "') " +
                "AND addresses.district_id = (SELECT id FROM districts di WHERE di.name = '" + dis + "')";

        try {
            ResultSet resultSet = CrudUtil.execute(url);

            while (resultSet.next()) {
                id = resultSet.getInt(1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return id;
    }


    private Connection connection;
    private PreparedStatement prepared;
    private ResultSet result;


    public void saveBtn() throws SQLException, ParseException {

        String url = "INSERT INTO accounts(id" +
                ", first_name" +
                ", last_name" +
                ", email" +
                ", phone_number" +
                ", dob" +
                ", code" +
                ", sex" +
                ", status" +
                ", created_at" +
                ", modified_at" +
                ", description" +
                ", role" +
                ", username" +
                ", password" +
                ", position_id " +
                ", flag " +
                ", address_id  )" +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


        connection = DBConnection.getInstance().getConnection();


        try {


            // Khi bâm save thì fill các trường trên UI vào biến account
            account.setFirstName(firstNameTextField.getText());
            account.setLastName(lastNameTextField.getText());
            account.setEmail(emailTextField.getText());
            account.setPhoneNumber(phoneTextField.getText());
            Date dob = Date.from(dobDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy ");
            String format = dateFormat.format(dob);
            Date gDate = dateFormat.parse(format);
            LocalDate date = dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            account.setDob(gDate);
            account.setGender(GENDER_TYPE.getGenderType((String) sexComboBox.getSelectionModel().getSelectedItem()));
            Position position = new Position();
            position.setName((String) positionComboBox.getSelectionModel().getSelectedItem());
            account.setPosition(position);

            int position_id = 0;
            for (int j = 0; j < positions.length; j++) {
                if (positions[j] == (String) positionComboBox.getSelectionModel().getSelectedItem()) {
                    position_id = j + 1;
                }
            }
            account.setUsername(usernameTextField.getText());
            account.setPassword(passwordTextField.getText());

            String city_select = (String) cityComboBox.getSelectionModel().getSelectedItem();
            String district_select = (String) districtComboBox.getSelectionModel().getSelectedItem();

            account.setAddressId(Long.parseLong(String.valueOf((getAddressId(city_select, district_select)))));


            Alert alert;
            if (firstNameTextField.getText().isEmpty()
                    || lastNameTextField.getText().isEmpty()
                    || emailTextField.getText().isEmpty()
                    || phoneTextField.getText().isEmpty()
                    || dob == null
                    || sexComboBox.getSelectionModel().getSelectedItem() == null
                    || positionComboBox.getSelectionModel().getSelectedItem() == null
                    || usernameTextField.getText().isEmpty()
                    || passwordTextField.getText().isEmpty()
                    || cityComboBox.getSelectionModel().getSelectedItem() == null
                    || districtComboBox.getSelectionModel().getSelectedItem() == null) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {


                prepared = connection.prepareStatement(url);

                AccountRepoImpl accountRepo = RepoFactory.getInstance().getRepo(REPO_TYPE.ACCOUNT);
                int id = (int) (accountRepo.getLastAccountId() + 1);
                prepared.setInt(1, id);
                prepared.setString(2, account.getFirstName());
                prepared.setString(3, account.getLastName());
                prepared.setString(4, account.getEmail());
                prepared.setString(5, account.getPhoneNumber());
                prepared.setDate(6, java.sql.Date.valueOf(date));
                prepared.setString(7, null);
                prepared.setInt(8, GENDER_TYPE.getGenderID((String) sexComboBox.getSelectionModel().getSelectedItem()));
                prepared.setString(9, null);
                prepared.setDate(10, null);
                prepared.setDate(11, null);
                prepared.setString(12, null);
                prepared.setString(13, null);
                prepared.setString(14, account.getUsername());
                prepared.setString(15, account.getPassword());
                prepared.setInt(16, position_id);
                prepared.setInt(17, 0);
                prepared.setString(18, null);

                prepared.executeUpdate();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Add Successfully");
                alert.showAndWait();
                // khi thêm mới thì gọi accNewAccount còn sửa thì gọi updateAccount
                this.listener.addNewAccount(account);
//                    ((Stage) rootPane.getScene().getWindow()).close();

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    public void updateAccount() throws ParseException {
        account.setFirstName(firstNameTextField.getText());
        account.setLastName(lastNameTextField.getText());
        account.setEmail(emailTextField.getText());
        account.setPhoneNumber(phoneTextField.getText());
        Date dob = Date.from(dobDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy ");
        String format = dateFormat.format(dob);
        Date gDate = dateFormat.parse(format);
        LocalDate date = dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        account.setDob(gDate);
        String gender = (String) sexComboBox.getSelectionModel().getSelectedItem();
        account.setGender(GENDER_TYPE.getGenderType(gender));
        Position position = new Position();
        position.setName((String) positionComboBox.getSelectionModel().getSelectedItem());
        account.setPosition(position);

        int position_id = 0;
        for (int j = 0; j < positions.length; j++) {
            if (positions[j] == (String) positionComboBox.getSelectionModel().getSelectedItem()) {
                position_id = j + 1;
            }
        }
        account.setUsername(usernameTextField.getText());
        account.setPassword(passwordTextField.getText());

        String city_select = (String) cityComboBox.getSelectionModel().getSelectedItem();
        String district_select = (String) districtComboBox.getSelectionModel().getSelectedItem();

        account.setAddressId(Long.parseLong(String.valueOf((getAddressId(city_select, district_select)))));


        String urlUpdate = "UPDATE accounts " +
                "SET first_name = '" + account.getFirstName() + "'" +
                ",last_name = '" + account.getLastName() + "'" +
                ",email ='" + account.getEmail() + "'" +
                ",phone_number = '" + account.getPhoneNumber() + "'" +
                ",dob= '" + java.sql.Date.valueOf(account.getDOBFormat()) + "'" +
                ",accounts.code = NULL" +
                ",sex='" + GENDER_TYPE.getGenderID(gender) + "'" +
                ",accounts.status = NULL" +
                ",created_at = NULL" +
                ",modified_at = NULL" +
                ",accounts.description = NULL" +
                ",accounts.role = NULL" +
                ",username = '" + account.getUsername() + "'" +
                ",accounts.password = '" + account.getPassword() + "'" +
                ",position_id ='" + position_id + "'" +
                ",flag=0" +
                ",address_id = '" + getAddressId(city_select, district_select) + "'" +
                "WHERE accounts.id ='" + account.getId() + "'";

        connection = DBConnection.getInstance().getConnection();
        try {
            Alert alert;
            if (firstNameTextField.getText().isEmpty()
                    || lastNameTextField.getText().isEmpty()
                    || emailTextField.getText().isEmpty()
                    || phoneTextField.getText().isEmpty()
                    || dob == null
                    || sexComboBox.getSelectionModel().getSelectedItem() == null
                    || positionComboBox.getSelectionModel().getSelectedItem() == null
                    || usernameTextField.getText().isEmpty()
                    || passwordTextField.getText().isEmpty()
                    || cityComboBox.getSelectionModel().getSelectedItem() == null
                    || districtComboBox.getSelectionModel().getSelectedItem() == null) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {

                prepared = connection.prepareStatement(urlUpdate);

                prepared.executeUpdate();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Update Successfully");
                alert.showAndWait();
                // khi thêm mới thì gọi accNewAccount còn sửa thì gọi updateAccount
                this.listener.updateAccount(account);
//                    ((Stage) rootPane.getScene().getWindow()).close();

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateUI() {

        firstNameTextField.setText(account.getFirstName());
        lastNameTextField.setText(account.getLastName());
        emailTextField.setText(account.getEmail());
        phoneTextField.setText(account.getPhoneNumber());
        usernameTextField.setText(account.getUsername());
        passwordTextField.setText(account.getPassword());


    }

    public void deleteBtn() {
        Alert alert;
        if (account.getId() > 0) {
            String url = "DELETE FROM accounts WHERE id = '" + account.getId() + "'";

            connection = DBConnection.getInstance().getConnection();

            try {


                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure to delete ?");

                Optional<ButtonType> optional = alert.showAndWait();

                if (optional.get().equals(ButtonType.OK)) {
                    prepared = connection.prepareStatement(url);
                    prepared.executeUpdate();


                    this.listener.deleteAccount(account);
                } else {
                    return;
                }

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Delete Successfully !");
                alert.showAndWait();


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } else {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select Employee !");
            alert.showAndWait();
        }

    }
}
