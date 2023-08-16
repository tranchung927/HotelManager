package vn.edu.aptech.hotelmanager.controllers;

import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.GridCell;
import org.controlsfx.control.GridView;
import org.controlsfx.control.cell.ColorGridCell;
import vn.edu.aptech.hotelmanager.common.BaseController;
import vn.edu.aptech.hotelmanager.domain.REPO_TYPE;
import vn.edu.aptech.hotelmanager.domain.RepoFactory;
import vn.edu.aptech.hotelmanager.domain.dto.CustomerDTO;
import vn.edu.aptech.hotelmanager.domain.dto.RoomDTO;
import vn.edu.aptech.hotelmanager.domain.model.City;
import vn.edu.aptech.hotelmanager.domain.model.ROOM_STATUS_TYPE;
import vn.edu.aptech.hotelmanager.domain.model.RoomStatusSummary;
import vn.edu.aptech.hotelmanager.domain.repo.IRoomRepo;

import java.net.URL;
import java.util.*;

public class CheckInController extends BaseController implements Initializable {

    @FXML
    private AnchorPane rootAnchorPane;
    @FXML
    private GridView<RoomStatusSummary> statusGridView;
    @FXML
    private GridView<RoomDTO> roomGridView;

    public CheckInController(Stage stage) {
        this.stage = stage;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roomGridView.cellWidthProperty().bind(roomGridView.widthProperty().multiply(0.25));
        roomGridView.cellHeightProperty().set(100);
        roomGridView.horizontalCellSpacingProperty().set(20);
        roomGridView.verticalCellSpacingProperty().set(20);
        roomGridView.setCellFactory(gridView -> new CheckInGridCell(roomDTO -> {
            System.out.println(roomDTO);
        }));

        IRoomRepo repo = RepoFactory.getInstance().getRepo(REPO_TYPE.ROOM);
        List<RoomDTO> roomDTOList = repo.getListRoom(1, 100);
        ObservableList<RoomDTO> roomDTOS = FXCollections.observableArrayList(roomDTOList);
        roomGridView.setItems(roomDTOS);
    }
}
