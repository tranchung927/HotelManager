package vn.edu.aptech.hotelmanager.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import vn.edu.aptech.hotelmanager.HMResourcesLoader;
import vn.edu.aptech.hotelmanager.domain.model.RoomStatusSummary;

import java.io.IOException;

public class RoomSummaryItemView extends GridPane {
    @FXML
    private Label contentLabel;
    public RoomSummaryItemView() {
        FXMLLoader fxmlLoader = new FXMLLoader(HMResourcesLoader.loadURL("fxml/RoomSummaryItemView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public void updateUI(RoomStatusSummary summary, boolean b) {
        if (summary.getStatus() != null) {
            BackgroundFill backgroundFillRight = new BackgroundFill(
                    Color.valueOf(summary.getStatus().toColor()),
                    new CornerRadii(4),
                    new Insets(0));
            Background bg = new Background(backgroundFillRight);
            this.setBackground(bg);
            contentLabel.setTextFill(Color.WHITE);
            contentLabel.setText(summary.getStatus().toString() + " (" + summary.getCount() + ")");
        } else {
            BackgroundFill backgroundFillRight = new BackgroundFill(
                    Color.WHITE,
                    new CornerRadii(0),
                    new Insets(0));
            Background bg = new Background(backgroundFillRight);
            this.setBackground(bg);
            BorderStroke borderStroke = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
                    new CornerRadii(4), new BorderWidths(1));
            this.setBorder(new Border(borderStroke));
            contentLabel.setTextFill(Color.BLACK);
            contentLabel.setText("All" + " (" + summary.getCount() + ")");
        }
    }
}
