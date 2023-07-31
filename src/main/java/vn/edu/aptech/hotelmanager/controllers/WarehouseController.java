package vn.edu.aptech.hotelmanager.controllers;

import fr.brouillard.oss.cssfx.CSSFX;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import vn.edu.aptech.hotelmanager.HMResourcesLoader;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WarehouseController implements Initializable {
    private Stage stage;

    public void goInputProduct(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader(HMResourcesLoader.loadURL("fxml/Product.fxml"));
        loader.setControllerFactory(c -> new ProductController());
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
