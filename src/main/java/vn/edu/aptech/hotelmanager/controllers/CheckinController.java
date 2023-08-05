package vn.edu.aptech.hotelmanager.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import vn.edu.aptech.hotelmanager.HMResourcesLoader;

import java.io.IOException;

public class CheckinController {
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
