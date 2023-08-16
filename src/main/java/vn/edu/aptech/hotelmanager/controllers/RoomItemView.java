package vn.edu.aptech.hotelmanager.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import vn.edu.aptech.hotelmanager.HMResourcesLoader;
import vn.edu.aptech.hotelmanager.domain.dto.RoomDTO;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RoomItemView extends GridPane {
    @FXML
    private Pane viewLeft;
    @FXML
    private Pane viewRight;
    @FXML
    private Label categoryLabel;
    @FXML
    private Label roomName;
    @FXML
    private Label checkInTimeLabel;
    @FXML
    private Label customerNameLabel;
    @FXML
    private Label roomStatusLabel;
    public RoomItemView() {
        FXMLLoader fxmlLoader = new FXMLLoader(HMResourcesLoader.loadURL("fxml/RoomItemView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void updateUI(RoomDTO roomDTO, boolean b) {
        String leftColor = roomDTO.getRoom().getStatus().toColor();
        String rightColor = roomDTO.getRoom().getStatus().toSecondColor();
        BackgroundFill backgroundFillLeft = new BackgroundFill(
                Color.valueOf(leftColor),
                new CornerRadii(0),
                new Insets(0));
        Background backgroundLeft = new Background(backgroundFillLeft);
        viewLeft.setBackground(backgroundLeft);
        BackgroundFill backgroundFillRight = new BackgroundFill(
                Color.valueOf(rightColor),
                new CornerRadii(0),
                new Insets(0));
        Background backgroundRight = new Background(backgroundFillRight);
        viewRight.setBackground(backgroundRight);
        checkInTimeLabel.setTextFill(Color.valueOf(leftColor));
        customerNameLabel.setTextFill(Color.valueOf(leftColor));
        roomStatusLabel.setTextFill(Color.valueOf(leftColor));

        if (roomDTO.getRoom() != null) {
            roomName.setText(roomDTO.getRoom().getName());
            categoryLabel.setText(roomDTO.getCategory().getCode());
        } else {
            roomName.setText("");
            categoryLabel.setText("");
        }

        if (roomDTO.getCustomer() != null) {
            customerNameLabel.setText(roomDTO.getCustomer().getFullName());
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd, HH:mm");
            checkInTimeLabel.setText(format.format(roomDTO.getCheckIn().getCheckInAt()));
            roomStatusLabel.setText(roomDTO.getRoom().getStatus().toString());
        } else {
            checkInTimeLabel.setText("");
            customerNameLabel.setText(roomDTO.getRoom().getStatus().toString());
            roomStatusLabel.setText("");
        }
    }
}
