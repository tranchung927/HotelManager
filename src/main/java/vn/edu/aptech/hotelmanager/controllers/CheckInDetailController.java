package vn.edu.aptech.hotelmanager.controllers;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXDateCell;
import io.github.palexdev.materialfx.utils.StringUtils;
import io.github.palexdev.materialfx.utils.others.FunctionalStringConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.util.StringConverter;
import vn.edu.aptech.hotelmanager.domain.REPO_TYPE;
import vn.edu.aptech.hotelmanager.domain.RepoFactory;
import vn.edu.aptech.hotelmanager.domain.dto.RoomDTO;
import vn.edu.aptech.hotelmanager.domain.model.*;
import vn.edu.aptech.hotelmanager.domain.repo.ILocationRepo;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class CheckInDetailController implements Initializable {
    private static final PseudoClass INVALID_PSEUDO_CLASS = PseudoClass.getPseudoClass("invalid");
    @FXML
    private MFXFilterComboBox<Country> countryComboBox;
    @FXML
    private MFXTextField firstNameTextField;
    @FXML
    private MFXTextField lastNameTextField;
    @FXML
    private MFXTextField phoneTextField;
    @FXML
    private MFXTextField checkInDateTextField;
    @FXML
    private MFXComboBox<GENDER_TYPE> genderComboBox;
    @FXML
    private MFXTextField docValueTextField;
    @FXML
    private MFXComboBox<DOCUMENT_TYPE> docTypeComboBox;
    private RoomDTO roomDTO;
    public CheckInDetailController(RoomDTO roomDTO) {
        this.roomDTO = roomDTO;
        CheckIn checkIn = new CheckIn();
        checkIn.setCheckInAt(new Date());
        this.roomDTO.setCheckIn(checkIn);
        this.roomDTO.setCustomer(new Customer());
        this.roomDTO.setDocument(new Document());
        this.roomDTO.setAddress(new Address());
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupUI();
        getAddressAndUpdateUI();
    }

    public boolean validate() {
        boolean isValidDoc = false;
        boolean isValidPhone = false;
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
        return isValidDoc && isValidFirstName && isValidLastName && isValidPhone;
    }

    private void setupUI() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        checkInDateTextField.setText(dateFormat.format(roomDTO.getCheckIn().getCheckInAt()));
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
                roomDTO.getCustomer().setGender(newValue);
            }
        });
        genderComboBox.selectItem(GENDER_TYPE.MALE);

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
                roomDTO.getDocument().setType(newValue);
            }
        });
        docTypeComboBox.selectItem(DOCUMENT_TYPE.ID_CARD);

        lastNameTextField.setTextLimit(50);
        phoneTextField.setTextLimit(11);
        docValueTextField.setTextLimit(20);
        firstNameTextField.setTextLimit(50);

        firstNameTextField
                .textProperty()
                .addListener((observable, oldValue, newValue) ->
                        roomDTO.getCustomer().setFirstName(newValue)
                );
        lastNameTextField
                .textProperty()
                .addListener((observable, oldValue, newValue) ->
                        roomDTO.getCustomer().setLastName(newValue)
                );
        phoneTextField
                .textProperty()
                .addListener((observable, oldValue, newValue) ->
                        roomDTO.getCustomer().setPhoneNumber(newValue)
                );
        docValueTextField
                .textProperty()
                .addListener((observable, oldValue, newValue) ->
                        roomDTO.getDocument().setValue(newValue)
                );
    }

    private void getAddressAndUpdateUI() {
        StringConverter<Country> converterCountry = FunctionalStringConverter.to(Country::getName);
        countryComboBox.setConverter(converterCountry);
        Function<String, Predicate<Country>> filterCountryFunction = s -> country -> StringUtils.containsIgnoreCase(converterCountry.toString(country), s);
        countryComboBox.setFilterFunction(filterCountryFunction);

        countryComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            roomDTO.getAddress().setCountry(newValue);
        });

        ILocationRepo locationRepo = RepoFactory.getInstance().getRepo(REPO_TYPE.LOCATION);
        List<Country> countryList = locationRepo.getListCountries();
        ObservableList<Country> countries = FXCollections.observableArrayList(countryList);
        countryComboBox.setItems(countries);
        List<Country> filterVN = countryList.stream().filter(c -> c.getId() == 240).toList();
        if (!filterVN.isEmpty()) {
            countryComboBox.selectItem(filterVN.get(0));
        }
    }
}
