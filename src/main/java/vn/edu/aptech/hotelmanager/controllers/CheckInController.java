package vn.edu.aptech.hotelmanager.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import vn.edu.aptech.hotelmanager.common.BaseController;

import java.net.URL;
import java.util.ResourceBundle;

public class CheckInController extends BaseController implements Initializable {

    @FXML
    private AnchorPane rootAnchorPane;

    public CheckInController(Stage stage) {
        this.stage = stage;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
