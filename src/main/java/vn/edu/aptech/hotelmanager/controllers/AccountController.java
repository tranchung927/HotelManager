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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import vn.edu.aptech.hotelmanager.domain.model.Account;
import vn.edu.aptech.hotelmanager.repo.db.DBConnection;
import vn.edu.aptech.hotelmanager.utils.CrudUtil;

public class AccountController implements Initializable {
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
    private MFXTextField codeTextField;

    @FXML
    private MFXTextField e_email;

    @FXML
    private MFXTextField e_phone;

    @FXML
    private MFXTextField e_role;

    @FXML
    private MFXTextField e_status;

    @FXML
    private MFXTextField employee_id;

    @FXML
    private MFXTextField first_name;

    @FXML
    private MFXTextField lastNameTextField;

    @FXML
    private MFXPasswordField passwordTextField;

    @FXML
    private MFXTextField usernameTextField;

    @FXML
    private MFXTextField descTextField;

    @FXML
    private MFXComboBox<?> sex;

    private final Stage stage;
    private double x = 0;
    private double y = 0;
    private IAccountControllerListener listener;
    private Account account;
    private String[] sexs = {"Male", "Female", "Other"};

    public AccountController(Stage stage, Account account) {
        this.stage = stage;
        this.account = account == null ? new Account() : account;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hideBtn();
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
        sex.setItems(observabledList);
    }

    /////////////////////////////////////////////////
    @FXML
    private MFXComboBox<?> position;
    private String[] positions = {"Reception", "Housekeeping", "Manager", "Security"};

    @FXML
    public void addValuePositions() {


        List<String> listPositions = new ArrayList<>();

        for (String item : positions) {
            listPositions.add(item);

        }
        ObservableList list = FXCollections.observableList(listPositions);
        position.setItems(list);
    }


    @FXML
    private MFXComboBox<?> country;

    @FXML
    private MFXComboBox<?> city;

    @FXML
    private MFXComboBox<?> district;

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
            country.setItems(observableList);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//////////////////////////////////////////////////
        String country_select = (String) country.getSelectionModel().getSelectedItem();
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
            city.setItems(observableList);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ///////////////////////////////////////////////

        String city_select = (String) city.getSelectionModel().getSelectedItem();
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
            district.setItems(observableList);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private MFXButton clearBtn;

    @FXML
    private MFXButton saveBtn;

    @FXML
    private MFXButton updateBtn;
    
    
    public void hideBtn(){
        saveBtn.setVisible(true);
        updateBtn.setVisible(false);
    }
    public void showBtn(){
        saveBtn.setVisible(false);
        updateBtn.setVisible(true);
    }
    public String getAddressId(String ci, String dis) {

        String id = null;
        String cities = (String) city.getSelectionModel().getSelectedItem();
        String districts = (String) district.getSelectionModel().getSelectedItem();

        String url = "SELECT id " +
                "FROM addresses" +
                " WHERE addresses.city_id = (SELECT id From cities ci WHERE ci.name= '" + cities + "') " +
                "AND addresses.district_id = (SELECT id FROM districts di WHERE di.name = '" + districts + "')";

        try {
            ResultSet resultSet = CrudUtil.execute(url);
            while (resultSet.next()) {
                id = resultSet.getString(1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return id;
    }

    private Connection connection;
    private PreparedStatement prepared;
    private ResultSet result;


    public void saveBtn() throws SQLException {
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

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy ");

            // Khi bâm save thì fill các trường trên UI vào biến account
            account.setFirstName(first_name.getText());
            account.setLastName(lastNameTextField.getText());
            Date dob = Date.from(dobDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
            account.setDob(dob);
            account.setEmail(e_email.getText());
            /// Bổ sung thêm các trường còn lai

            String idEmployee = employee_id.getText();
            String fname = first_name.getText();
            String lname = lastNameTextField.getText();
            String email = e_email.getText();
            String phone = e_phone.getText().toLowerCase();
            LocalDate dob1 = dobDatePicker.getValue();
            String code = codeTextField.getText();
            String getSex = (String) sex.getSelectionModel().getSelectedItem();
            int sex = 0;
            for (int i = 0; i < sexs.length; i++) {
                if (Objects.equals(sexs[i], getSex)) {
                    sex = i + 1;
                }
            }
            String status = e_status.getText();
            Calendar cal = Calendar.getInstance();
            Date date = cal.getTime();
            String todaysdate = dateFormat.format(date);
            Date localDate = dateFormat.parse(todaysdate);
            String description = descTextField.getText();
            String role = e_role.getText();
            String username = usernameTextField.getText();
            String password = passwordTextField.getText();
            String getPosition = (String) position.getSelectionModel().getSelectedItem();
            int position_id = 0;
            for (int j = 0; j < positions.length; j++) {
                if (Objects.equals(positions[j], getPosition)) {
                    position_id = j + 1;
                }
            }
            String city_select = (String) city.getSelectionModel().getSelectedItem();
            String district_select = (String) district.getSelectionModel().getSelectedItem();
            String address_id = getAddressId(city_select, district_select);
            Alert alert;
            if (idEmployee.isEmpty()
                    || fname.isEmpty()
                    || lname.isEmpty()
                    || email.isEmpty()
                    || phone.isEmpty()
                    || dob1 == null
                    || code.isEmpty()
                    || sex == 0
                    || status.isEmpty()
                    || description.isEmpty()
                    || role.isEmpty()
                    || username.isEmpty()
                    || password.isEmpty()
                    || position_id == 0
                    || address_id.isEmpty()) {

                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                prepared = connection.prepareStatement(url);
                prepared.setString(1, idEmployee);
                prepared.setString(2, fname);
                prepared.setString(3, lname);
                prepared.setString(4, email);
                prepared.setString(5, phone);
                prepared.setDate(6, java.sql.Date.valueOf(dob1));
                prepared.setString(7, code);
                prepared.setInt(8, sex);
                prepared.setString(9, status);

                prepared.setDate(10, null);
                prepared.setDate(11, null);

                prepared.setString(12, description);
                prepared.setString(13, role);
                prepared.setString(14, username);
                prepared.setString(15, password);
                prepared.setInt(16, position_id);
                prepared.setInt(17, 1);
                prepared.setInt(18, Integer.parseInt(address_id));


                prepared.executeUpdate();


                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Add Successfully");
                alert.showAndWait();
                // khi thêm mới thì gọi accNewAccount còn sửa thì gọi updateAccount
                this.listener.addNewAccount(account);
                clearBtn();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void clearBtn() {
        employee_id.setText("");
        first_name.setText("");
        lastNameTextField.setText("");
        e_email.setText("");
        e_phone.setText("");
        dobDatePicker.setText("");
        codeTextField.setText("");
        sex.getSelectionModel().clearSelection();
        e_status.setText("");
        descTextField.setText("");
        e_role.setText("");
        usernameTextField.setText("");
        passwordTextField.setText("");
        position.getSelectionModel().clearSelection();
        country.getSelectionModel().clearSelection();
        city.getSelectionModel().clearSelection();
        district.getSelectionModel().clearSelection();
    }
    public void updateUI(){
       employee_id.setText(String.valueOf(account.getId()));
        first_name.setText(account.getFirstName());
        lastNameTextField.setText(account.getLastName());
        e_email.setText(account.getEmail());
        e_phone.setText(account.getPhoneNumber());
        usernameTextField.setText(account.getUsername());
        passwordTextField.setText(account.getPassword());



    }
}


