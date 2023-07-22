package vn.edu.aptech.hotelmanager.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import javafx.application.Platform;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private final Stage stage;
//    private double x = 0;
//    private double y = 0;

    @FXML
    private MFXFontIcon alwaysOnTopIcon;

    @FXML
    private MFXFontIcon closeIcon;

    @FXML
    private MFXButton custom;

    @FXML
    private MFXFontIcon minimizeIcon;

    @FXML
    private MFXPasswordField password;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private MFXTextField username;
    @FXML
    private MFXButton closeButton;


    @FXML
    private MFXButton maximizeIButton;

    @FXML
    private MFXButton minimizeButton;


    public LoginController(Stage stage) {
        this.stage = stage;
    }

    @FXML
    void exit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void maximize(ActionEvent event) {

    }

    @FXML
    void minimize(ActionEvent event ){
        ((Stage) rootPane.getScene().getWindow()).setIconified(true);
    } ;





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

/*
        rootPane.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });
        rootPane.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX()- x);
            stage.setY(mouseEvent.getScreenY()-y);

            stage.setOpacity(4);
        });

        rootPane.setOnMouseReleased(mouseEvent -> {
            stage.setOpacity(1);
        });

 */

        closeIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> Platform.exit());
        minimizeIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> ((Stage) rootPane.getScene().getWindow()).setIconified(true));
        alwaysOnTopIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            boolean newVal = !stage.isAlwaysOnTop();
            alwaysOnTopIcon.pseudoClassStateChanged(PseudoClass.getPseudoClass("always-on-top"), newVal);
            stage.setAlwaysOnTop(newVal);
        });


    }
}
