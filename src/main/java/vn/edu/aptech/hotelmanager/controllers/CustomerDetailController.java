package vn.edu.aptech.hotelmanager.controllers;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.utils.DateTimeUtils;
import io.github.palexdev.materialfx.utils.StringUtils;
import io.github.palexdev.materialfx.utils.others.FunctionalStringConverter;
import io.github.palexdev.materialfx.utils.others.dates.DateStringConverter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.util.StringConverter;
import vn.edu.aptech.hotelmanager.domain.REPO_TYPE;
import vn.edu.aptech.hotelmanager.domain.RepoFactory;
import vn.edu.aptech.hotelmanager.domain.dto.CustomerDTO;
import vn.edu.aptech.hotelmanager.domain.model.*;
import vn.edu.aptech.hotelmanager.domain.repo.ICustomerRepo;
import vn.edu.aptech.hotelmanager.domain.repo.ILocationRepo;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.function.Predicate;

public class CustomerDetailController implements Initializable {
    private static final PseudoClass INVALID_PSEUDO_CLASS = PseudoClass.getPseudoClass("invalid");
    @FXML
    public MFXTextField firstNameTextField;

    @FXML
    public MFXTextField lastNameTextField;

    @FXML
    public MFXTextField emailTextField;

    @FXML
    public MFXTextField phoneTextField;

    @FXML
    public MFXTextField docValueTextField;

    @FXML
    public MFXComboBox<DOCUMENT_TYPE> docTypeComboBox;

    @FXML
    public MFXFilterComboBox<Country> countryComboBox;

    @FXML
    public MFXFilterComboBox<City> cityComboBox;

    @FXML
    public MFXFilterComboBox<District> districtComboBox;

    @FXML
    private MFXComboBox<GENDER_TYPE> genderComboBox;

    @FXML
    public MFXTextField addressTextField;

    @FXML
    public MFXDatePicker birthDayPicker;

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
    private void setupUI() {
        birthDayPicker.setGridAlgorithm(DateTimeUtils::partialIntMonthMatrix);
        birthDayPicker.setConverterSupplier(() -> new DateStringConverter("yyyy-MM-dd", birthDayPicker.getLocale()));

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
        firstNameTextField.setTextLimit(50);
        lastNameTextField.setTextLimit(50);
        emailTextField.setTextLimit(250);
        phoneTextField.setTextLimit(11);
        docValueTextField.setTextLimit(20);
        addressTextField.setTextLimit(250);

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
        emailTextField
                .textProperty()
                .addListener((observable, oldValue, newValue) ->
                        customerDTO.getCustomer().setEmail(newValue)
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
    }
    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }
    private void updateUI() {
        genderComboBox.selectItem(customerDTO.getCustomer().getGender());
        firstNameTextField.setText(customerDTO.getCustomer().getFirstName());
        lastNameTextField.setText(customerDTO.getCustomer().getLastName());
        emailTextField.setText(customerDTO.getCustomer().getEmail());
        phoneTextField.setText(customerDTO.getCustomer().getPhoneNumber());
        if (customerDTO.getCustomer().getDob() != null) {
            birthDayPicker.setValue(LocalDate.);
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
                getCountries();
                countryComboBox.selectItem(address.getCountry());
                getCities();
                cityComboBox.selectItem(address.getCity());
                getDistricts();
                districtComboBox.selectItem(address.getDistrict());
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
