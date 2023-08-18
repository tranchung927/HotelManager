package vn.edu.aptech.hotelmanager.controllers;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import vn.edu.aptech.hotelmanager.HMResourcesLoader;
import vn.edu.aptech.hotelmanager.common.BaseController;
import vn.edu.aptech.hotelmanager.domain.REPO_TYPE;
import vn.edu.aptech.hotelmanager.domain.RepoFactory;
import vn.edu.aptech.hotelmanager.domain.model.Account;
import vn.edu.aptech.hotelmanager.domain.repo.IAccountRepo;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends BaseController implements Initializable {

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
    private MFXPasswordField passwordTextField;
    @FXML
    private MFXTextField usernameTextField;
    @FXML
    private AnchorPane rootPane;

    public LoginController(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.ownerNode = rootPane;
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

    @FXML
    private void login(ActionEvent event) {
        String userName = usernameTextField.getText();
        String password = passwordTextField.getText();

        if (userName == null || userName.isEmpty() || password == null || password.isEmpty()) {
            this.showErrorDialog("Error", "Please fill all blank fields");
            return;
        }
        try {
            IAccountRepo repo = RepoFactory.getInstance().getRepo(REPO_TYPE.ACCOUNT);
            Account account = repo.login(userName, password);
            if (account != null) {
                this.showInfoDialog("Success", "Login Successfully", e -> {
                    hiddenDialog();
                    openMain();
                });
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.showErrorDialog("Error", "Wrong username or password");
    }
    private void openMain() {
        try {
            FXMLLoader loader = new FXMLLoader(HMResourcesLoader.loadURL("fxml/Main.fxml"));
            loader.setControllerFactory(c -> new MainController(stage));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
            stage.setTitle("Hotel FX");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
