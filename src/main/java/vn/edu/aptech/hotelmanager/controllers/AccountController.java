package vn.edu.aptech.hotelmanager.controllers;

import io.github.palexdev.materialfx.controls.*;

import io.github.palexdev.materialfx.utils.DateTimeUtils;
import io.github.palexdev.materialfx.utils.StringUtils;
import io.github.palexdev.materialfx.utils.others.FunctionalStringConverter;
import io.github.palexdev.materialfx.utils.others.dates.DateStringConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import vn.edu.aptech.hotelmanager.common.BaseController;
import vn.edu.aptech.hotelmanager.domain.REPO_TYPE;
import vn.edu.aptech.hotelmanager.domain.RepoFactory;
import vn.edu.aptech.hotelmanager.domain.dto.AccountDTO;
import vn.edu.aptech.hotelmanager.domain.model.*;
import vn.edu.aptech.hotelmanager.domain.repo.IAccountRepo;
import vn.edu.aptech.hotelmanager.domain.repo.ILocationRepo;


public class AccountController extends BaseController implements Initializable {
    @FXML
    private StackPane ownerPane;

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
    private MFXFilterComboBox<Country> countryComboBox;
    @FXML
    private MFXFilterComboBox<City> cityComboBox;
    @FXML
    private MFXFilterComboBox<District> districtComboBox;
    @FXML
    private MFXComboBox<GENDER_TYPE> sexComboBox;
    @FXML
    private MFXComboBox<Position> positionComboBox;
    private IAccountControllerListener listener;
    private final IAccountRepo accountRepo;
    private final ILocationRepo locationRepo;
    private Address address;
    private Account account;

    public AccountController(Stage stage, Account account) {
        this.stage = stage;
        this.account = account == null ? new Account() : account;
        this.accountRepo = RepoFactory.getInstance().getRepo(REPO_TYPE.ACCOUNT);
        this.locationRepo = RepoFactory.getInstance().getRepo(REPO_TYPE.LOCATION);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.ownerNode = ownerPane;
        dobDatePicker.setGridAlgorithm(DateTimeUtils::partialIntMonthMatrix);
        dobDatePicker.setConverterSupplier(() -> new DateStringConverter("dd/MM/yyyy", dobDatePicker.getLocale()));
        updateUI();
        getAddressAndUpdateUI();
        getPositionAndUpdateUI();
    }
    public void setListener(IAccountControllerListener listener) {
        this.listener = listener;
    }

    private void setupAddressComboBox() {

        StringConverter<Country> converterCountry = FunctionalStringConverter.to(Country::getName);
        countryComboBox.setConverter(converterCountry);
        Function<String, Predicate<Country>> filterCountryFunction = s -> country -> StringUtils.containsIgnoreCase(converterCountry.toString(country), s);
        countryComboBox.setFilterFunction(filterCountryFunction);

        StringConverter<City> converterCity = FunctionalStringConverter.to(City::getName);
        cityComboBox.setConverter(converterCity);
        Function<String, Predicate<City>> filterCityFunction = s -> country -> StringUtils.containsIgnoreCase(converterCity.toString(country), s);
        cityComboBox.setFilterFunction(filterCityFunction);

        StringConverter<District> converterDistrict = FunctionalStringConverter.to(District::getName);
        districtComboBox.setConverter(converterDistrict);
        Function<String, Predicate<District>> filterDistrictFunction = s -> country -> StringUtils.containsIgnoreCase(converterDistrict.toString(country), s);
        districtComboBox.setFilterFunction(filterDistrictFunction);

        countryComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            cityComboBox.clearSelection();
            cityComboBox.clear();
            cityComboBox.setItems(FXCollections.observableList(new ArrayList<>()));
            districtComboBox.clearSelection();
            districtComboBox.clear();
            districtComboBox.setItems(FXCollections.observableList(new ArrayList<>()));
            address.setCountry(newValue);
            address.setCity(null);
            address.setDistrict(null);
            getCities();
        });
        cityComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            districtComboBox.clearSelection();
            districtComboBox.clear();
            districtComboBox.setItems(FXCollections.observableList(new ArrayList<>()));
            address.setCity(newValue);
            address.setDistrict(null);
            getDistricts();
        });
        districtComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            address.setDistrict(newValue);
        });
    }

    private void updateUI() {
        firstNameTextField.setText(account.getFirstName());
        lastNameTextField.setText(account.getLastName());
        emailTextField.setText(account.getEmail());
        phoneTextField.setText(account.getPhoneNumber());
        usernameTextField.setText(account.getUsername());
        passwordTextField.setText(account.getPassword());
        List<GENDER_TYPE> genderList = new ArrayList<>();
        genderList.add(GENDER_TYPE.MALE);
        genderList.add(GENDER_TYPE.FEMALE);
        genderList.add(GENDER_TYPE.OTHER);
        ObservableList<GENDER_TYPE> observableGenderList = FXCollections.observableList(genderList);
        sexComboBox.setItems(observableGenderList);
        sexComboBox.selectItem(account.getGender());
        sexComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (oldValue != newValue) {
                account.setGender(newValue);
            }
        });
    }
    private void getAddressAndUpdateUI() {
        if (account.getAddressId() > 0) {
           try {
               address = locationRepo.getAddressById(account.getAddressId());
               getCountries();
               countryComboBox.selectItem(address.getCountry());
               getCities();
               cityComboBox.selectItem(address.getCity());
               getDistricts();
               districtComboBox.selectItem(address.getDistrict());
           } catch (Exception e) {
               e.printStackTrace();
           }
        } else  {
            address = new Address();
        }
        setupAddressComboBox();
    }

    private void getPositionAndUpdateUI() {
        StringConverter<Position> converter = FunctionalStringConverter.to(Position::getName);
        List<Position> listPositions = accountRepo.getListPosition();
        ObservableList<Position> positionObservableList = FXCollections.observableList(listPositions);
        positionComboBox.setItems(positionObservableList);
        positionComboBox.setConverter(converter);
        if (account.getPosition() != null) {
            positionComboBox.selectItem(account.getPosition());
        }
        positionComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (oldValue.getId() != newValue.getId()) {
                account.setPosition(newValue);
            }
        });
    }

    private void getCountries() {
        if (countryComboBox.getItems().isEmpty()) {
            List<Country> countryList = locationRepo.getListCountries();
            ObservableList<Country> countries = FXCollections.observableArrayList(countryList);
            countryComboBox.setItems(countries);
        }
    }
    private void getCities() {
        if (countryComboBox.getSelectionModel() == null) {
            return;
        }
        if (cityComboBox.getItems().isEmpty()) {
            long countryId = countryComboBox.getSelectionModel().getSelectedItem().getId();
            List<City> cityList = locationRepo.getListCitiesByCountryId(countryId);
            ObservableList<City> cities = FXCollections.observableArrayList(cityList);
            cityComboBox.setItems(cities);
        }
    }

    private void getDistricts() {
        if (cityComboBox.getSelectionModel() == null) {
            return;
        }
        if (districtComboBox.getItems().isEmpty()) {
            long cityId = cityComboBox.getSelectionModel().getSelectedItem().getId();
            List<District> districtList = locationRepo.getListDistrictsByCityId(cityId);
            ObservableList<District> districts = FXCollections.observableArrayList(districtList);
            districtComboBox.setItems(districts);
        }
    }

    private void deleteAccount() {
        Boolean isSuccess = false;
        try {
            isSuccess = accountRepo.deleteAccount(account.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (isSuccess) {
            this.listener.deleteAccount(account);
            showInfoDialog("Success", "Delete Successfully!", event -> {
                hiddenDialog();
                // Đóng màn account
            });
            return;
        }
        this.showErrorDialog("Error", "An error occurred, please try again!");
    }
    @FXML
    private void onClickedSave() {
        if (account.getPosition() == null ||
                address.getCountry() == null ||
                address.getCity() == null ||
                address.getDistrict() == null) {
            showErrorDialog("Error", "Please fill all blank fields");
        } else {
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setAccount(account);
            accountDTO.setPosition(account.getPosition());
            accountDTO.setAddress(address);
            try {
                AccountDTO result = accountRepo.createOrUpdate(accountDTO);
                if (account.getId() > 0) {
                    listener.updateAccount(result.getAccount());
                } else {
                    listener.addNewAccount(result.getAccount());
                }
                showInfoDialog("Success", account.getId() > 0 ? "Updated Successfully!" : "Created Successfully!", event -> {
                    hiddenDialog();
                });
            } catch (Exception e) {
                e.printStackTrace();
                showErrorDialog("Error", "An error occurred, please try again!");
            }
        }
//
//
//
//
//
//                // Khi bâm save thì fill các trường trên UI vào biến account
//                account.setFirstName(firstNameTextField.getText());
//                account.setLastName(lastNameTextField.getText());
//                account.setEmail(emailTextField.getText());
//                account.setPhoneNumber(phoneTextField.getText());
//                Date dob = Date.from(dobDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
//                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy ");
//                String format = dateFormat.format(dob);
//                Date gDate = dateFormat.parse(format);
//                LocalDate date = dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//                account.setDob(gDate);
//                // TODO:
////                account.setGender(GENDER_TYPE.getGenderType((String) sexComboBox.getSelectionModel().getSelectedItem()));
//                Position position = new Position();
//                position.setName((String) positionComboBox.getSelectionModel().getSelectedItem());
//                account.setPosition(position);
//
//                int position_id = 0;
//                for (int j = 0; j < positions.length; j++) {
//                    if (positions[j] == (String) positionComboBox.getSelectionModel().getSelectedItem()) {
//                        position_id = j + 1;
//                    }
//                }
//                account.setUsername(usernameTextField.getText());
//                account.setPassword(passwordTextField.getText());
//
//                String city_select = (String) cityComboBox.getSelectionModel().getSelectedItem();
//                String district_select = (String) districtComboBox.getSelectionModel().getSelectedItem();
//
//                account.setAddressId((getAddressId(city_select, district_select)));
//
//
//                Alert alert;
//                if (firstNameTextField.getText().isEmpty()
//                        || lastNameTextField.getText().isEmpty()
//                        || emailTextField.getText().isEmpty()
//                        || phoneTextField.getText().isEmpty()
//                        || dob == null
//                        || sexComboBox.getSelectionModel().getSelectedItem() == null
//                        || positionComboBox.getSelectionModel().getSelectedItem() == null
//                        || usernameTextField.getText().isEmpty()
//                        || passwordTextField.getText().isEmpty()
//                        || cityComboBox.getSelectionModel().getSelectedItem() == null
//                        || districtComboBox.getSelectionModel().getSelectedItem() == null) {
//
//                    alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setTitle("Error Message");
//                    alert.setHeaderText(null);
//                    alert.setContentText("Please fill all blank fields");
//                    alert.showAndWait();
//                } else {
//
//
//                    prepared = connection.prepareStatement(url);
//
//                    AccountRepoImpl accountRepo = RepoFactory.getInstance().getRepo(REPO_TYPE.ACCOUNT);
//                    int id = Integer.parseInt(accountRepo.getLastAccountId()) + 1;
//
//
//                    prepared.setInt(1, id);
//                    prepared.setString(2, account.getFirstName());
//                    prepared.setString(3, account.getLastName());
//                    prepared.setString(4, account.getEmail());
//                    prepared.setString(5, account.getPhoneNumber());
//                    prepared.setDate(6, java.sql.Date.valueOf(date));
//                    prepared.setString(7, null);
//                    prepared.setInt(8, GENDER_TYPE.getGenderID((String) sexComboBox.getSelectionModel().getSelectedItem()));
//                    prepared.setString(9, null);
//                    prepared.setDate(10, null);
//                    prepared.setDate(11, null);
//                    prepared.setString(12, null);
//                    prepared.setString(13, null);
//                    prepared.setString(14, account.getUsername());
//                    prepared.setString(15, account.getPassword());
//                    prepared.setInt(16, position_id);
//                    prepared.setInt(17, 0);
//                    prepared.setString(18, account.getAddressId());
//
//                    prepared.executeUpdate();
//
//                    alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setTitle("Information Message");
//                    alert.setHeaderText(null);
//                    alert.setContentText("Add Successfully");
//                    alert.showAndWait();
//                    // khi thêm mới thì gọi accNewAccount còn sửa thì gọi updateAccount
//                    this.listener.addNewAccount(account);
////                    ((Stage) rootPane.getScene().getWindow()).close();
//
//                }
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//
//        } else {
//            account.setFirstName(firstNameTextField.getText());
//            account.setLastName(lastNameTextField.getText());
//            account.setEmail(emailTextField.getText());
//            account.setPhoneNumber(phoneTextField.getText());
//            Date dob = Date.from(dobDatePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
//            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy ");
//            String format = dateFormat.format(dob);
//            Date gDate = dateFormat.parse(format);
//            LocalDate date = dob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//            account.setDob(gDate);
//            String gender = (String) sexComboBox.getSelectionModel().getSelectedItem();
//            account.setGender(GENDER_TYPE.getGenderType(gender));
//            Position position = new Position();
//            position.setName((String) positionComboBox.getSelectionModel().getSelectedItem());
//            account.setPosition(position);
//
//            int position_id = 0;
//            for (int j = 0; j < positions.length; j++) {
//                if (positions[j] == (String) positionComboBox.getSelectionModel().getSelectedItem()) {
//                    position_id = j + 1;
//                }
//            }
//            account.setUsername(usernameTextField.getText());
//            account.setPassword(passwordTextField.getText());
//
//            String city_select = (String) cityComboBox.getSelectionModel().getSelectedItem();
//            String district_select = (String) districtComboBox.getSelectionModel().getSelectedItem();
//
//            account.setAddressId((getAddressId(city_select, district_select)));
//
//
//            try {
//                Alert alert;
//                if (firstNameTextField.getText().isEmpty()
//                        || lastNameTextField.getText().isEmpty()
//                        || emailTextField.getText().isEmpty()
//                        || phoneTextField.getText().isEmpty()
//                        || dob == null
//                        || sexComboBox.getSelectionModel().getSelectedItem() == null
//                        || positionComboBox.getSelectionModel().getSelectedItem() == null
//                        || usernameTextField.getText().isEmpty()
//                        || passwordTextField.getText().isEmpty()
//                        || cityComboBox.getSelectionModel().getSelectedItem() == null
//                        || districtComboBox.getSelectionModel().getSelectedItem() == null) {
//
//                    alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setTitle("Error Message");
//                    alert.setHeaderText(null);
//                    alert.setContentText("Please fill all blank fields");
//                    alert.showAndWait();
//                } else {
//
//                    prepared = connection.prepareStatement(urlUpdate);
//
//                    prepared.executeUpdate();
//
//                    alert = new Alert(Alert.AlertType.INFORMATION);
//                    alert.setTitle("Information Message");
//                    alert.setHeaderText(null);
//                    alert.setContentText("Update Successfully");
//                    alert.showAndWait();
//                    // khi thêm mới thì gọi accNewAccount còn sửa thì gọi updateAccount
//                    this.listener.updateAccount(account);
////                    ((Stage) rootPane.getScene().getWindow()).close();
//                }
//
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
////        } catch (Exception e) {
////            throw new RuntimeException(e);
////        }

    }
    @FXML
    private void onClickedDelete() {
        this.showWarningDialog("Confirmation", "Are you sure to delete?",
                Map.entry(new MFXButton("Confirm"), event -> {
                    hiddenDialog();
                    deleteAccount();
                }),
                Map.entry(new MFXButton("Cancel"), event -> hiddenDialog()));
    }
}


