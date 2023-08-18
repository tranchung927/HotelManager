package vn.edu.aptech.hotelmanager.controllers;

import org.controlsfx.control.GridCell;
import vn.edu.aptech.hotelmanager.domain.dto.RoomDTO;

public class CheckInGridCell extends GridCell<RoomDTO> {
    private final RoomItemView itemView;
    public CheckInGridCell(CheckInGridCellListener listener) {
        itemView = new RoomItemView();
        itemView.prefWidthProperty().bind(this.widthProperty());
        itemView.prefHeightProperty().bind(this.heightProperty());
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
    protected void updateItem(RoomDTO roomDTO, boolean b) {
        super.updateItem(roomDTO, b);
        if (roomDTO != null) {
            itemView.updateUI(roomDTO, b);
        }

    }
}
