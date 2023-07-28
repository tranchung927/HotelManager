package vn.edu.aptech.hotelmanager.controllers;

import fr.brouillard.oss.cssfx.CSSFX;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import javafx.application.Platform;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import vn.edu.aptech.hotelmanager.HMResourcesLoader;
import vn.edu.aptech.hotelmanager.repo.db.DBConnection;
import vn.edu.aptech.hotelmanager.utils.CrudUtil;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    private final Stage stage;
    private double x = 0;
    private double y = 0;

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


    public LoginController(Stage stage) {
        this.stage = stage;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        closeIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> Platform.exit());
        minimizeIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> ((Stage) rootPane.getScene().getWindow()).setIconified(true));
        alwaysOnTopIcon.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            boolean newVal = !stage.isAlwaysOnTop();
            alwaysOnTopIcon.pseudoClassStateChanged(PseudoClass.getPseudoClass("always-on-top"), newVal);
            stage.setAlwaysOnTop(newVal);
        });

        rootPane.setOnMousePressed(event -> {
            x = stage.getX() - event.getScreenX();
            y = stage.getY() - event.getScreenY();
        });
        rootPane.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() + x);
            stage.setY(event.getScreenY() + y);
        });


    }


    public void login(ActionEvent event) {

        String user = username.getText();
        String pass = password.getText();
        String sql = "SELECT username,password FROM employee WHERE username = ? and password =?";

        try {
            ResultSet result = CrudUtil.execute(sql, user, pass);

            Alert alert;
            if (user.isEmpty() || pass.isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();

            } else {
                if (result.next()) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Login Successfully");
                    alert.showAndWait();

                    CSSFX.start();
                    FXMLLoader loader = new FXMLLoader(HMResourcesLoader.loadURL("fxml/Main.fxml"));
                    loader.setControllerFactory(c -> new MainController(stage));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
//                scene.setFill(Color.TRANSPARENT);
//                stage.initStyle(StageStyle.TRANSPARENT);
                    stage.setScene(scene);
                    stage.setTitle("Hotel FX");
                    stage.show();
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong username or password");
                    alert.showAndWait();
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
