package vn.edu.aptech.hotelmanager.controllers;

import javafx.geometry.Pos;
import org.controlsfx.control.GridCell;
import vn.edu.aptech.hotelmanager.domain.model.RoomStatusSummary;

public class RoomSummaryGridCell extends GridCell<RoomStatusSummary> {
    private final RoomSummaryItemView itemView;
    public RoomSummaryGridCell(RoomSummaryGridCellListener listener) {
        itemView = new RoomSummaryItemView();
//        itemView.prefWidthProperty().bind(this.widthProperty());
//        itemView.prefHeightProperty().bind(this.heightProperty());
        itemView.setAlignment(Pos.CENTER);
        this.setGraphic(this.itemView);
        this.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() >= 2) {
                if (this.getItem() != null) {
                    listener.onClicked(this.getItem());
                }
            }
        });
    }

    @Override
    protected void updateItem(RoomStatusSummary summary, boolean b) {
        super.updateItem(summary, b);
        if (summary != null) {
            itemView.updateUI(summary, b);
        }
    }
}
