package vn.edu.aptech.hotelmanager.controllers;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.utils.DateTimeUtils;
import io.github.palexdev.materialfx.utils.StringUtils;
import io.github.palexdev.materialfx.utils.others.FunctionalStringConverter;
import io.github.palexdev.materialfx.utils.others.dates.DateStringConverter;
import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.Severity;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;
import vn.edu.aptech.hotelmanager.domain.REPO_TYPE;
import vn.edu.aptech.hotelmanager.domain.RepoFactory;
import vn.edu.aptech.hotelmanager.domain.dto.CustomerDTO;
import vn.edu.aptech.hotelmanager.domain.model.City;
import vn.edu.aptech.hotelmanager.domain.model.Country;
import vn.edu.aptech.hotelmanager.domain.model.DOCUMENT_TYPE;
import vn.edu.aptech.hotelmanager.domain.model.District;
import vn.edu.aptech.hotelmanager.domain.repo.ILocationRepo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.function.Predicate;

public class CustomerDetailController implements Initializable {
    private static final PseudoClass INVALID_PSEUDO_CLASS = PseudoClass.getPseudoClass("invalid");
    public MFXTextField firstNameTextField;
    public MFXTextField lastNameTextField;
    public MFXTextField emailTextField;
    public MFXTextField phoneTextField;
    public MFXTextField docValueTextField;
    public MFXComboBox<DOCUMENT_TYPE> docTypeComboBox;
    public MFXFilterComboBox<Country> countryComboBox;
    public MFXFilterComboBox<City> cityComboBox;
    public MFXFilterComboBox<District> districtComboBox;
    public MFXTextField addressTextField;
    public MFXDatePicker birthDayPicker;

    private final CustomerDTO customerDTO;
    public CustomerDetailController(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupUI();
        firstNameTextField.setText(customerDTO.getCustomer().getFirstName());
        lastNameTextField.setText(customerDTO.getCustomer().getLastName());
        emailTextField.setText(customerDTO.getCustomer().getEmail());
        phoneTextField.setText(customerDTO.getCustomer().getPhoneNumber());
        birthDayPicker.setText(customerDTO.getCustomer().getDob().toString());
        if (customerDTO.getDocument() != null) {
            docValueTextField.setText(customerDTO.getDocument().getValue());
            docTypeComboBox.setText(customerDTO.getDocument().getType().toString());
        }
        if (customerDTO.getAddress() != null) {
            countryComboBox.setText(customerDTO.getAddress().getCountry().getName());
            cityComboBox.setText(customerDTO.getAddress().getCity().getName());
            districtComboBox.setText(customerDTO.getAddress().getDistrict().getName());
            addressTextField.setText(customerDTO.getAddress().getFullAddress());
        }
        getCountries();
    }

    private void setupUI() {
        birthDayPicker.setGridAlgorithm(DateTimeUtils::partialIntMonthMatrix);
        birthDayPicker.setConverterSupplier(() -> new DateStringConverter("yyyy-MM-dd", birthDayPicker.getLocale()));

        firstNameTextField.setTextLimit(50);
        lastNameTextField.setTextLimit(50);
        emailTextField.setTextLimit(250);
        phoneTextField.setTextLimit(11);
        docValueTextField.setTextLimit(20);
        addressTextField.setTextLimit(250);

        setTextFieldValid(addressTextField, 1);
        setTextFieldValid(firstNameTextField, 1);
        setTextFieldValid(lastNameTextField, 1);
        setTextFieldValid(emailTextField, 1);
        setTextFieldValid(phoneTextField, 1);
        setTextFieldValid(docValueTextField, 10);

        setupComboBox(countryComboBox);
    }

    private void setTextFieldValid(MFXTextField fieldValid, int minimumLength) {
        Constraint lengthConstraint = Constraint.Builder.build()
                .setSeverity(Severity.ERROR)
                .setMessage("Must be at least " + minimumLength + " character long")
                .setCondition(fieldValid.textProperty().length().greaterThanOrEqualTo(minimumLength))
                .get();

        fieldValid.getValidator()
                .constraint(lengthConstraint);

        fieldValid.getValidator().validProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                fieldValid.pseudoClassStateChanged(INVALID_PSEUDO_CLASS, false);
            }
        });

        fieldValid.delegateFocusedProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue && !newValue) {
                List<Constraint> constraints = fieldValid.validate();
                if (!constraints.isEmpty()) {
                    fieldValid.pseudoClassStateChanged(INVALID_PSEUDO_CLASS, true);
                }
            }
        });
    }

    private void getCountries() {
        StringConverter<Country> converter = FunctionalStringConverter.to(Country::getName);
        Function<String, Predicate<Country>> filterFunction = s -> country -> StringUtils.containsIgnoreCase(converter.toString(country), s);
        ILocationRepo repo = RepoFactory.getInstance().getRepo(REPO_TYPE.LOCATION);
        List<Country> countryList = repo.getListCountries();
        ObservableList<Country> countries = FXCollections.observableArrayList(countryList);
        countryComboBox.setItems(countries);
        countryComboBox.setConverter(converter);
        countryComboBox.setFilterFunction(filterFunction);

    }

    private void setupComboBox(MFXComboBox comboBox) {
//        Constraint selectedConstraint = Constraint.Builder.build()
//                .setSeverity(Severity.ERROR)
//                .setCondition(Bindings.createBooleanBinding( () -> comboBox.getSelectedItem() != null))
//                .get();
//        comboBox.getValidator().constraint(selectedConstraint);
//        comboBox.getValidator().validProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue) {
//                comboBox.pseudoClassStateChanged(INVALID_PSEUDO_CLASS, false);
//            }
//        });
//
//        comboBox.delegateFocusedProperty().addListener((observable, oldValue, newValue) -> {
//            if (oldValue && !newValue) {
//                List<Constraint> constraints = comboBox.validate();
//                if (!constraints.isEmpty()) {
//                    comboBox.pseudoClassStateChanged(INVALID_PSEUDO_CLASS, true);
//                }
//            }
//        });
    }
}
