package vn.edu.aptech.hotelmanager.controllers;

import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class SplashController {

    @FXML
    private MFXProgressSpinner prgsSplash;

    private final Stage stage;
    public SplashController(Stage stage) {
        this.stage = stage;
    }
}
