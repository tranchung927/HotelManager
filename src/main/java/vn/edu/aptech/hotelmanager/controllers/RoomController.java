package vn.edu.aptech.hotelmanager.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import io.github.palexdev.materialfx.enums.ScrimPriority;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import vn.edu.aptech.hotelmanager.HMResourcesLoader;
import vn.edu.aptech.hotelmanager.domain.REPO_TYPE;
import vn.edu.aptech.hotelmanager.domain.RepoFactory;
import vn.edu.aptech.hotelmanager.domain.dto.CustomerDTO;
import vn.edu.aptech.hotelmanager.domain.repo.ILocationRepo;
import vn.edu.aptech.hotelmanager.domain.repo.IRoomRepo;

import java.util.Map;

public class RoomController {
    private final IRoomRepo roomRepo = RepoFactory.getInstance().getRepo(REPO_TYPE.ROOM);
    private MFXGenericDialog dialogContent;
    private MFXStageDialog dialog;
    private final Stage stage;

    @FXML
    private GridPane grid;
    public RoomController(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void onClickedTapMe(ActionEvent event) {
        showDialog(Map.entry(new MFXButton("Confirm"), e -> {
                    // Handle when tap confirm
                }),
                Map.entry(new MFXButton("Cancel"), e -> {
                    this.hiddenDialog();
                })
        );
    }
    @SafeVarargs
    private void showDialog(Map.Entry<Node, EventHandler<MouseEvent>>... actions) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(HMResourcesLoader.loadURL("fxml/CustomerDetail.fxml"));
            loader.setControllerFactory(c -> new CustomerDetailController(new CustomerDTO()));
            root = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (root == null) {
            return;
        }
        this.dialogContent = MFXGenericDialogBuilder.build()
                .makeScrollable(true)
                .setHeaderIcon(null)
                .setShowAlwaysOnTop(false)
                .setHeaderText("Customer Info")
                .setContent(root)
                .addActions(actions)
                .get();
        this.dialogContent.setMaxSize(800, 800);
        this.dialog = MFXGenericDialogBuilder.build(dialogContent)
                .toStageDialogBuilder()
                .initOwner(stage)
                .initModality(Modality.APPLICATION_MODAL)
                .setDraggable(false)
                .setTitle("Dialogs")
                .setOwnerNode(grid)
                .setScrimPriority(ScrimPriority.WINDOW)
                .setScrimOwner(true)
                .get();
        this.dialog.showDialog();
    }
    private void hiddenDialog() {
        if (this.dialogContent == null || this.dialog == null) {
            return;
        }
        this.dialog.setOnHidden(e -> {
            this.dialog = null;
            this.dialogContent = null;
        });
        this.dialog.close();
    }
    private void convertDialogTo(String styleClass) {
        dialogContent.getStyleClass().removeIf(
                s -> s.equals("mfx-info-dialog") || s.equals("mfx-warn-dialog") || s.equals("mfx-error-dialog")
        );

        if (styleClass != null)
            dialogContent.getStyleClass().add(styleClass);
    }
}
