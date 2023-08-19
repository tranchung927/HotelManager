package vn.edu.aptech.hotelmanager.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import vn.edu.aptech.hotelmanager.domain.dto.RoomDTO;
import vn.edu.aptech.hotelmanager.domain.model.ROOM_STATUS_TYPE;

import java.net.URL;
import java.util.ResourceBundle;

public class CheckInOptionController implements Initializable {
    @FXML
    private MFXButton dirtyButton;
    @FXML
    private MFXButton cleanButton;
    @FXML
    private MFXButton checkInButton;
    @FXML
    private MFXButton repairingButton;
    @FXML
    private MFXButton repairSuccessButton;
    private RoomDTO roomDTO;
    private ICheckInOptionListener listener;
    public CheckInOptionController(RoomDTO roomDTO) {
        this.roomDTO = roomDTO;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        switch (roomDTO.getRoom().getStatus()) {
            case AVAILABLE -> {
                checkInButton.setDisable(false);
                repairingButton.setDisable(false);
                repairSuccessButton.setDisable(true);
                dirtyButton.setDisable(false);
                cleanButton.setDisable(true);
            }
            case REPAIR -> {
                checkInButton.setDisable(true);
                repairingButton.setDisable(true);
                repairSuccessButton.setDisable(false);
                dirtyButton.setDisable(true);
                cleanButton.setDisable(true);
            }
            case DIRTY -> {
                checkInButton.setDisable(true);
                repairingButton.setDisable(true);
                repairSuccessButton.setDisable(true);
                dirtyButton.setDisable(true);
                cleanButton.setDisable(false);
            }
        }
    }

    public void setListener(ICheckInOptionListener listener) {
        this.listener = listener;
    }

    @FXML
    private void onClickedCheckIn() {
        listener.onCheckIn();
    }

    @FXML
    private void onClickedRepairing() {
        listener.onUpdateStatus(ROOM_STATUS_TYPE.REPAIR);
    }

    @FXML
    private void onClickedRepaired() {
        listener.onUpdateStatus(ROOM_STATUS_TYPE.AVAILABLE);
    }

    @FXML
    private void onClickedDirty() {
        listener.onUpdateStatus(ROOM_STATUS_TYPE.DIRTY);
    }

    @FXML
    private void onClickedClean() {
        listener.onUpdateStatus(ROOM_STATUS_TYPE.AVAILABLE);
    }
}
