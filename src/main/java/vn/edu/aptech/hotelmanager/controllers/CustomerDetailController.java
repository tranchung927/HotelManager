package vn.edu.aptech.hotelmanager.controllers;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXDateCell;
import io.github.palexdev.materialfx.utils.DateTimeUtils;
import io.github.palexdev.materialfx.utils.StringUtils;
import io.github.palexdev.materialfx.utils.others.FunctionalStringConverter;
import io.github.palexdev.materialfx.utils.others.dates.DateStringConverter;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DateCell;
import javafx.util.StringConverter;
import vn.edu.aptech.hotelmanager.domain.REPO_TYPE;
import vn.edu.aptech.hotelmanager.domain.RepoFactory;
import vn.edu.aptech.hotelmanager.domain.dto.CustomerDTO;
import vn.edu.aptech.hotelmanager.domain.model.*;
import vn.edu.aptech.hotelmanager.domain.repo.ICustomerRepo;
import vn.edu.aptech.hotelmanager.domain.repo.ILocationRepo;
import vn.edu.aptech.hotelmanager.utils.DateUtils;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class CustomerDetailController implements Initializable {
    private static final PseudoClass INVALID_PSEUDO_CLASS = PseudoClass.getPseudoClass("invalid");
    @FXML
    private MFXTextField firstNameTextField;
    @FXML
    private MFXTextField lastNameTextField;
    @FXML
    private MFXTextField emailTextField;
    @FXML
    private MFXTextField phoneTextField;
    @FXML
    private MFXTextField docValueTextField;
    @FXML
    private MFXComboBox<DOCUMENT_TYPE> docTypeComboBox;
    @FXML
    private MFXFilterComboBox<Country> countryComboBox;
    @FXML
    private MFXFilterComboBox<City> cityComboBox;
    @FXML
    private MFXFilterComboBox<District> districtComboBox;
    @FXML
    private MFXComboBox<GENDER_TYPE> genderComboBox;
    @FXML
    private MFXTextField addressTextField;
    @FXML
    private MFXDatePicker birthDayPicker;
    private final CustomerDTO customerDTO;
    private final ILocationRepo locationRepo;

    public CustomerDetailController(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
        this.locationRepo = RepoFactory.getInstance().getRepo(REPO_TYPE.LOCATION);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupUI();
        updateUI();
        getAddressAndUpdateUI();
        getDocumentAndUpdateUI();
    }

    public boolean validate() {
        boolean isValidEmail = false;
        boolean isValidDoc = false;
        boolean isValidPhone = false;
        if (emailTextField.getText() != null && !emailTextField.getText().isBlank()) {
            Pattern validEmailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
            isValidEmail = validEmailPattern.matcher(emailTextField.getText()).matches();
        }
        emailTextField.pseudoClassStateChanged(INVALID_PSEUDO_CLASS, !isValidEmail);
        if (docValueTextField.getText() != null && !docValueTextField.getText().isBlank()) {
            isValidDoc = docValueTextField.getText().length() >= 10;
        }
        docValueTextField.pseudoClassStateChanged(INVALID_PSEUDO_CLASS, !isValidDoc);

        boolean isValidFirstName = firstNameTextField.getText() != null && !firstNameTextField.getText().isBlank();
        firstNameTextField.pseudoClassStateChanged(INVALID_PSEUDO_CLASS, !isValidFirstName);

        boolean isValidLastName = lastNameTextField.getText() != null && !lastNameTextField.getText().isBlank();
        lastNameTextField.pseudoClassStateChanged(INVALID_PSEUDO_CLASS, !isValidLastName);

        if (phoneTextField.getText() != null && !phoneTextField.getText().isBlank()) {
            Pattern validPhonePattern = Pattern.compile("^0\\d{9,11}$", Pattern.CASE_INSENSITIVE);
            isValidPhone = validPhonePattern.matcher(phoneTextField.getText()).matches();
        }
        phoneTextField.pseudoClassStateChanged(INVALID_PSEUDO_CLASS, !isValidPhone);
        boolean isValidDob = birthDayPicker.getValue() != null;
        birthDayPicker.pseudoClassStateChanged(INVALID_PSEUDO_CLASS, !isValidDob);
        boolean isValidCountry = countryComboBox.getSelectedItem() != null;
        countryComboBox.pseudoClassStateChanged(INVALID_PSEUDO_CLASS, !isValidCountry);
        return isValidEmail && isValidDoc && isValidFirstName && isValidLastName && isValidPhone && isValidDob && isValidCountry;
    }
    private void setupUI() {
        birthDayPicker.setGridAlgorithm(DateTimeUtils::partialIntMonthMatrix);
        birthDayPicker.setConverterSupplier(() -> new DateStringConverter("yyyy-MM-dd", birthDayPicker.getLocale()));
        LocalDate nowDate = LocalDate.now();
        LocalDate minDate = LocalDate.of(nowDate.getYear() - 120, nowDate.getMonth(), nowDate.getDayOfMonth());
        birthDayPicker.setCellFactory(d ->
                new MFXDateCell(birthDayPicker, d) {
                    @Override
                    public void updateItem(LocalDate date) {
                        super.updateItem(date);
                        if (date != null) {
                            setDisable(date.isBefore(minDate));
                        }
                    }
                }
                );
        StringConverter<GENDER_TYPE> genderConverter = FunctionalStringConverter.to(GENDER_TYPE::toString);
        genderComboBox.setConverter(genderConverter);
        List<GENDER_TYPE> genderList = new ArrayList<>();
        genderList.add(GENDER_TYPE.MALE);
        genderList.add(GENDER_TYPE.FEMALE);
        genderList.add(GENDER_TYPE.OTHER);
        ObservableList<GENDER_TYPE> observableGenderList = FXCollections.observableList(genderList);
        genderComboBox.setItems(observableGenderList);
        genderComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (oldValue != newValue) {
                customerDTO.getCustomer().setGender(newValue);
            }
        });

        StringConverter<DOCUMENT_TYPE> documentConverter = FunctionalStringConverter.to(DOCUMENT_TYPE::toString);
        docTypeComboBox.setConverter(documentConverter);
        List<DOCUMENT_TYPE> documentList = new ArrayList<>();
        documentList.add(DOCUMENT_TYPE.ID_CARD);
        documentList.add(DOCUMENT_TYPE.CITIZEN_ID);
        documentList.add(DOCUMENT_TYPE.PASSPORT);
        ObservableList<DOCUMENT_TYPE> observableDocumentList = FXCollections.observableList(documentList);
        docTypeComboBox.setItems(observableDocumentList);
        docTypeComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (oldValue != newValue) {
                customerDTO.getDocument().setType(newValue);
            }
        });
        lastNameTextField.setTextLimit(50);
        emailTextField.setTextLimit(100);
        phoneTextField.setTextLimit(11);
        docValueTextField.setTextLimit(20);
        addressTextField.setTextLimit(100);
        firstNameTextField.setTextLimit(50);
        emailTextField
                .textProperty()
                .addListener((observable, oldValue, newValue) ->
                        customerDTO.getCustomer().setEmail(newValue)
                );
        firstNameTextField
                .textProperty()
                .addListener((observable, oldValue, newValue) ->
                        customerDTO.getCustomer().setFirstName(newValue)
                );
        lastNameTextField
                .textProperty()
                .addListener((observable, oldValue, newValue) ->
                        customerDTO.getCustomer().setLastName(newValue)
                );
        phoneTextField
                .textProperty()
                .addListener((observable, oldValue, newValue) ->
                        customerDTO.getCustomer().setPhoneNumber(newValue)
                );
        docValueTextField
                .textProperty()
                .addListener((observable, oldValue, newValue) ->
                        customerDTO.getDocument().setValue(newValue)
                );
        addressTextField
                .textProperty()
                .addListener((observable, oldValue, newValue) ->
                        customerDTO.getAddress().setFullAddress(newValue)
                );
        birthDayPicker.setAllowEdit(false);
        birthDayPicker.setOnAction(actionEvent ->
                customerDTO.getCustomer().setDob(DateUtils.convertToDate(birthDayPicker.getValue()))
        );
    }

    private void updateUI() {
        genderComboBox.selectItem(customerDTO.getCustomer().getGender());
        firstNameTextField.setText(customerDTO.getCustomer().getFirstName());
        lastNameTextField.setText(customerDTO.getCustomer().getLastName());
        emailTextField.setText(customerDTO.getCustomer().getEmail());
        phoneTextField.setText(customerDTO.getCustomer().getPhoneNumber());
        if (customerDTO.getCustomer().getDob() != null) {
            birthDayPicker.setValue(DateUtils.convertToLocalDate(customerDTO.getCustomer().getDob()));
        } else {
            LocalDate nowDate = LocalDate.now();
            LocalDate defaultValue = LocalDate.of(nowDate.getYear() - 18, nowDate.getMonth(), nowDate.getDayOfMonth());
            customerDTO.getCustomer().setDob(DateUtils.convertToDate(defaultValue));
            birthDayPicker.setValue(defaultValue);
        }
    }

    private void getDocumentAndUpdateUI() {
        if (customerDTO.getDocument().getId() > 0) {
            try {
                ICustomerRepo customerRepo = RepoFactory.getInstance().getRepo(REPO_TYPE.CUSTOMER);
                Document document = customerRepo.getDocumentById(customerDTO.getDocument().getId());
                if (document != null) {
                    customerDTO.setDocument(document);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        docTypeComboBox.selectItem(customerDTO.getDocument().getType());
        if (customerDTO.getDocument().getValue() != null) {
            docValueTextField.setText(customerDTO.getDocument().getValue());
        }
    }

    private void getAddressAndUpdateUI() {
        if (customerDTO.getAddress().getId() > 0) {
            try {
                Address address = locationRepo.getAddressById(customerDTO.getAddress().getId());
                if (address != null) {
                    customerDTO.setAddress(address);
                    getCountries();
                    countryComboBox.selectItem(address.getCountry());
                    getCities();
                    cityComboBox.selectItem(address.getCity());
                    getDistricts();
                    districtComboBox.selectItem(address.getDistrict());
                } else {
                    getCountries();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            getCountries();
        }
        setupAddressComboBox();
    }

    private void setupAddressComboBox() {
        StringConverter<Country> converterCountry = FunctionalStringConverter.to(Country::getName);
        countryComboBox.setConverter(converterCountry);
        Function<String, Predicate<Country>> filterCountryFunction = s -> country -> StringUtils.containsIgnoreCase(converterCountry.toString(country), s);
        countryComboBox.setFilterFunction(filterCountryFunction);

        StringConverter<City> converterCity = FunctionalStringConverter.to(City::getName);
        cityComboBox.setConverter(converterCity);
        Function<String, Predicate<City>> filterCityFunction = s -> city -> StringUtils.containsIgnoreCase(converterCity.toString(city), s);
        cityComboBox.setFilterFunction(filterCityFunction);

        StringConverter<District> converterDistrict = FunctionalStringConverter.to(District::getName);
        districtComboBox.setConverter(converterDistrict);
        Function<String, Predicate<District>> filterDistrictFunction = s -> district -> StringUtils.containsIgnoreCase(converterDistrict.toString(district), s);
        districtComboBox.setFilterFunction(filterDistrictFunction);

        countryComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            cityComboBox.clearSelection();
            cityComboBox.clear();
            cityComboBox.setItems(FXCollections.observableList(new ArrayList<>()));
            districtComboBox.clearSelection();
            districtComboBox.clear();
            districtComboBox.setItems(FXCollections.observableList(new ArrayList<>()));
            customerDTO.getAddress().setCountry(newValue);
            customerDTO.getAddress().setCity(null);
            customerDTO.getAddress().setDistrict(null);
            getCities();
        });
        cityComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            districtComboBox.clearSelection();
            districtComboBox.clear();
            districtComboBox.setItems(FXCollections.observableList(new ArrayList<>()));
            customerDTO.getAddress().setCity(newValue);
            customerDTO.getAddress().setDistrict(null);
            getDistricts();
        });
        districtComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            customerDTO.getAddress().setDistrict(newValue);
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
}
