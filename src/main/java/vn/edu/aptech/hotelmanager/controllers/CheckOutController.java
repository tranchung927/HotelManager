package vn.edu.aptech.hotelmanager.controllers;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import vn.edu.aptech.hotelmanager.domain.REPO_TYPE;
import vn.edu.aptech.hotelmanager.domain.RepoFactory;
import vn.edu.aptech.hotelmanager.domain.dto.RoomDTO;
import vn.edu.aptech.hotelmanager.domain.repo.IRoomRepo;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.ResourceBundle;

public class CheckOutController implements Initializable {
    @FXML
    private MFXTextField countryTextField;
    @FXML
    private MFXTextField firstNameTextField;
    @FXML
    private MFXTextField lastNameTextField;
    @FXML
    private MFXTextField phoneTextField;
    @FXML
    private MFXTextField checkInDateTextField;
    @FXML
    private MFXTextField checkOutDateTextField;
    @FXML
    private MFXTextField genderTextField;
    @FXML
    private MFXTextField docValueTextField;
    @FXML
    private MFXTextField roomPriceTextField;
    @FXML
    private MFXTextField totalPriceTextField;
    private RoomDTO roomDTO;
    public CheckOutController(RoomDTO roomDTO) {
        this.roomDTO = roomDTO;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getData();
        updateUI();
    }

    private void getData() {
        IRoomRepo roomRepo = RepoFactory.getInstance().getRepo(REPO_TYPE.ROOM);
        RoomDTO res = roomRepo.getById(roomDTO.getRoom().getId());
        if (res != null) {
            roomDTO = res;
        }
    }

    private void updateUI() {
        firstNameTextField.setText(roomDTO.getCustomer().getFirstName());
        lastNameTextField.setText(roomDTO.getCustomer().getLastName());
        phoneTextField.setText(roomDTO.getCustomer().getPhoneNumber());
        countryTextField.setText(roomDTO.getAddress().getCountry().getName());

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd, HH:mm");
        checkInDateTextField.setText(format.format(roomDTO.getCheckIn().getCheckInAt()));
        checkOutDateTextField.setText(format.format(new Date()));

        genderTextField.setText(roomDTO.getCustomer().getGender().toString());

        docValueTextField.setFloatingText(roomDTO.getDocument().getType().toString());
        docValueTextField.setText(roomDTO.getDocument().getValue());

        roomPriceTextField.setText(String.valueOf(roomDTO.getRoom().getPrice()));
        double totalPrice = roomDTO.getRoom().getPrice() * (double) roomDTO.getCheckIn().totalHours();
        totalPriceTextField.setText(String.valueOf(totalPrice));
    }
}
