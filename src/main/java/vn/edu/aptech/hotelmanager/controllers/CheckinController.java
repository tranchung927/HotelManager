package vn.edu.aptech.hotelmanager.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import vn.edu.aptech.hotelmanager.HMResourcesLoader;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class CheckinController {
    @FXML
    private MFXTextField GroupID;

    @FXML
    private MFXComboBox<?> address;

    @FXML
    private MFXFilterComboBox<?> assignee;

    @FXML
    private MFXDatePicker checkin;

    @FXML
    private MFXButton checkinbtn;

    @FXML
    private MFXDatePicker checkout;

    @FXML
    private MFXButton checkoutbtn;

    @FXML
    private TextArea description;

    @FXML
    private MFXDatePicker dob;

    @FXML
    private MFXTextField document_type;

    @FXML
    private MFXFilterComboBox<?> documnet_type;

    @FXML
    private MFXButton edit;

    @FXML
    private MFXTextField email;

    @FXML
    private MFXTextField fname;

    @FXML
    private MFXTextField lname;

    @FXML
    private MFXTextField phone_number;

    @FXML
    private MFXComboBox<?> sex;
    private static Stage window;

    public static void display(ActionEvent event,Stage stage) throws IOException {
        window = stage;
        stage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader(HMResourcesLoader.loadURL("fxml/Checkin.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();

    }
    public void checkout(ActionEvent event) throws IOException {
        CheckoutController checkoutController = new CheckoutController();
        checkoutController.display(event);
        window.close();
    }
}
