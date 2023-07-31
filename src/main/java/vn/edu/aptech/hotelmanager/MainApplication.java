package vn.edu.aptech.hotelmanager;

import fr.brouillard.oss.cssfx.CSSFX;
import io.github.palexdev.materialfx.css.themes.MFXThemeManager;
import io.github.palexdev.materialfx.css.themes.Themes;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import vn.edu.aptech.hotelmanager.controllers.MainController;
import vn.edu.aptech.hotelmanager.controllers.SplashController;
import vn.edu.aptech.hotelmanager.repo.db.DBConnection;

import java.io.IOException;
import java.sql.SQLException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        CSSFX.start();
        FXMLLoader loader = new FXMLLoader(HMResourcesLoader.loadURL("fxml/Main.fxml"));
        loader.setControllerFactory(c -> new MainController(stage));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
        scene.setFill(Color.TRANSPARENT);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.setTitle("Hotel FX");
        stage.show();
 //       openSplash(stage);
    }

    public static void main(String[] args) {
        launch(args);
        try {
            DBConnection.getInstance().getConnection().close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
 /*   private static void openSplash(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(HMResourcesLoader.loadURL("fxml/Splash.fxml"));
        loader.setControllerFactory(c -> new SplashController(stage));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        MFXThemeManager.addOn(scene, Themes.DEFAULT, Themes.LEGACY);
        stage.setScene(scene);
        stage.setTitle("Splash");
        stage.show();
    }*/
}