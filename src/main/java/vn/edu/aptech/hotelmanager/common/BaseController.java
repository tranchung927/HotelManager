package vn.edu.aptech.hotelmanager.common;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import io.github.palexdev.materialfx.enums.ScrimPriority;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.Map;

public class BaseController {
    public Stage stage;
    public Pane ownerNode;
    private MFXGenericDialog alertDialogContent;
    private MFXStageDialog alertDialog;

    @SafeVarargs
    public final void showDialog(Node icon, String title, String message, String style, Map.Entry<Node, EventHandler<MouseEvent>>... actions) {
        this.alertDialogContent = MFXGenericDialogBuilder.build()
                .makeScrollable(true)
                .setHeaderIcon(icon)
                .setShowAlwaysOnTop(false)
                .setHeaderText(title)
                .setContentText(message)
                .addActions(actions)
                .get();
        this.alertDialogContent.setMaxSize(400, 200);
        convertDialogTo(style);
        this.alertDialog = MFXGenericDialogBuilder.build(alertDialogContent)
                .toStageDialogBuilder()
                .initOwner(stage)
                .initModality(Modality.APPLICATION_MODAL)
                .setDraggable(false)
                .setTitle("Dialogs")
                .setOwnerNode(ownerNode)
                .setScrimPriority(ScrimPriority.WINDOW)
                .setScrimOwner(true)
                .get();
        this.alertDialog.showDialog();
    }

    public void hiddenDialog() {
        if (this.alertDialogContent == null || this.alertDialog == null) {
            return;
        }
        this.alertDialog.setOnHidden(e -> {
            this.alertDialog = null;
            this.alertDialogContent = null;
        });
        this.alertDialog.close();
    }

    public void showInfoDialog(String title, String message, EventHandler<MouseEvent> handler) {
        MFXFontIcon infoIcon = new MFXFontIcon("fas-circle-info", 18);
        showDialog(infoIcon, title, message,"mfx-info-dialog", Map.entry(new MFXButton("OK"), handler));
    }

    public void showInfoDialog(String title, String message) {
        showInfoDialog(title, message, event -> hiddenDialog());
    }
    public void showWarningDialog(String title, String message, Map.Entry<Node, EventHandler<MouseEvent>>... actions) {
        MFXFontIcon warnIcon = new MFXFontIcon("fas-circle-exclamation", 18);
        showDialog(warnIcon, title, message,"mfx-warn-dialog", actions);
    }
    public void showErrorDialog(String title, String message, EventHandler<MouseEvent> handler) {
        MFXFontIcon errorIcon = new MFXFontIcon("fas-circle-xmark", 18);
        showDialog(errorIcon, title, message,"mfx-error-dialog", Map.entry(new MFXButton("OK"), handler));
    }
    public void showErrorDialog(String title, String message) {
        showErrorDialog(title, message, event -> hiddenDialog());
    }

    private void convertDialogTo(String styleClass) {
        alertDialogContent.getStyleClass().removeIf(
                s -> s.equals("mfx-info-dialog") || s.equals("mfx-warn-dialog") || s.equals("mfx-error-dialog")
        );

        if (styleClass != null)
            alertDialogContent.getStyleClass().add(styleClass);
    }
}
