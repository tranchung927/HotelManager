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
    public static void display(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader(HMResourcesLoader.loadURL("fxml/Checkout.fxml"));
        Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
