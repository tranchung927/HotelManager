package vn.edu.aptech.hotelmanager.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import io.github.palexdev.materialfx.enums.ScrimPriority;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.GridView;
import vn.edu.aptech.hotelmanager.HMResourcesLoader;
import vn.edu.aptech.hotelmanager.common.BaseController;
import vn.edu.aptech.hotelmanager.domain.REPO_TYPE;
import vn.edu.aptech.hotelmanager.domain.RepoFactory;
import vn.edu.aptech.hotelmanager.domain.dto.RoomDTO;
import vn.edu.aptech.hotelmanager.domain.model.ROOM_STATUS_TYPE;
import vn.edu.aptech.hotelmanager.domain.model.Room;
import vn.edu.aptech.hotelmanager.domain.model.RoomStatusSummary;
import vn.edu.aptech.hotelmanager.domain.repo.IRoomRepo;

import java.net.URL;
import java.util.*;

public class CheckInController extends BaseController implements Initializable, IMainListener {

    @FXML
    private AnchorPane rootAnchorPane;
    @FXML
    private GridView<RoomStatusSummary> statusGridView;
    @FXML
    private GridView<RoomDTO> roomGridView;
    private MFXGenericDialog dialogContent;
    private MFXStageDialog dialog;
    private final IRoomRepo roomRepo = RepoFactory.getInstance().getRepo(REPO_TYPE.ROOM);
    private ROOM_STATUS_TYPE selectedStatus;
    public CheckInController(Stage stage) {
        this.stage = stage;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.ownerNode = rootAnchorPane;
        statusGridView.horizontalCellSpacingProperty().set(10);
        statusGridView.cellWidthProperty().set(150);
        statusGridView.cellHeightProperty().set(50);
        statusGridView.setCellFactory(gridView -> new RoomSummaryGridCell(summary -> {
            selectedStatus = summary.getStatus();
            resetRoomWitStatus(summary.getStatus());
        }));

//        resetRoomSummary();

        roomGridView.cellWidthProperty().bind(roomGridView.widthProperty().multiply(0.25));
        roomGridView.cellHeightProperty().set(100);
        roomGridView.horizontalCellSpacingProperty().set(20);
        roomGridView.verticalCellSpacingProperty().set(20);
        roomGridView.setCellFactory(gridView -> new CheckInGridCell(this::didSelectRoom));
//        resetRoomWitStatus(selectedStatus);
    }

    @Override
    public void needReload() {
        resetRoomSummary();
        resetRoomWitStatus(selectedStatus);
    }

    private void resetRoomWitStatus(ROOM_STATUS_TYPE status) {
        List<RoomDTO> roomDTOList = roomRepo.getListRoom(status);
        ObservableList<RoomDTO> roomDTOS = FXCollections.observableArrayList(roomDTOList);
        roomGridView.setItems(roomDTOS);
    }

    private void resetRoomSummary() {
        List<RoomStatusSummary> summaryList = roomRepo.getSummaryForStatus();
        RoomStatusSummary all = new RoomStatusSummary();
        all.setCount(summaryList.stream().map(RoomStatusSummary::getCount).reduce(0, Integer::sum));
        summaryList.add(0, all);
        ObservableList<RoomStatusSummary> summaries = FXCollections.observableArrayList(summaryList);
        statusGridView.setItems(summaries);
    }

    private void didSelectRoom(RoomDTO roomDTO) {
        if (roomDTO.getRoom().getStatus() == ROOM_STATUS_TYPE.OCCUPIED) {
            Parent root = null;
            CheckOutController checkOutController = new CheckOutController(roomDTO);
            try {
                FXMLLoader loader = new FXMLLoader(HMResourcesLoader.loadURL("fxml/CheckOut.fxml"));
                loader.setControllerFactory(c -> checkOutController);
                root = loader.load();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (root == null) {
                return;
            }
            showContentDialog(root, "Room " + roomDTO.getRoom().getName(),
                    Map.entry(new MFXButton("Check out"), event -> {
                        checkOut(roomDTO);
                    })
            );
        } else {
            roomOption(roomDTO);
        }
    }

    private void checkOut(RoomDTO roomDTO) {
        try {
            roomRepo.checkOut(roomDTO);
            hiddenContentDialog();
            resetRoomSummary();
            resetRoomWitStatus(selectedStatus);
        } catch (Exception e) {
            e.printStackTrace();
            showErrorDialog("Error", "An error occurred, please try again!");
        }
    }

    private void checkInDetail(RoomDTO roomDTO) {
        Parent root = null;
        CheckInDetailController checkInDetailController = new CheckInDetailController(roomDTO);
        try {
            FXMLLoader loader = new FXMLLoader(HMResourcesLoader.loadURL("fxml/CheckInDetail.fxml"));
            loader.setControllerFactory(c -> checkInDetailController);
            root = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (root == null) {
            return;
        }
        showContentDialog(root, "Room " + roomDTO.getRoom().getName(), Map.entry(new MFXButton("Check in"), event -> {
            if (checkInDetailController.validate()) {
                try {
                    roomRepo.checkIn(roomDTO);
                    hiddenContentDialog();
                    resetRoomSummary();
                    resetRoomWitStatus(selectedStatus);
                } catch (Exception e) {
                    e.printStackTrace();
                    showErrorDialog("Error", "An error occurred, please try again!");
                }
            }
        }));
    }

    private void roomOption(RoomDTO roomDTO) {
        Parent root = null;
        CheckInOptionController checkInOptionController = new CheckInOptionController(roomDTO);
        try {
            FXMLLoader loader = new FXMLLoader(HMResourcesLoader.loadURL("fxml/CheckInOption.fxml"));
            loader.setControllerFactory(c -> checkInOptionController);
            root = loader.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (root == null) {
            return;
        }
        checkInOptionController.setListener(new ICheckInOptionListener() {
            @Override
            public void onUpdateStatus(ROOM_STATUS_TYPE status) {
                hiddenContentDialog();
                Boolean isUpdateSuccess = roomRepo.updateStatus(roomDTO.getRoom().getId(), status);
                if (isUpdateSuccess) {
                    resetRoomSummary();
                    resetRoomWitStatus(selectedStatus);
                }
            }

            @Override
            public void onCheckIn() {
                hiddenContentDialog();
                checkInDetail(roomDTO);
            }
        });
        showContentDialog(root, "Room " + roomDTO.getRoom().getName());
    }

    private void showContentDialog(Node root, String title, Map.Entry<Node, EventHandler<MouseEvent>>... actions) {
        this.dialogContent = MFXGenericDialogBuilder.build()
                .makeScrollable(true)
                .setHeaderIcon(null)
                .setShowAlwaysOnTop(false)
                .setHeaderText(title)
                .setContent(root)
                .get();
        dialogContent.addActions(actions);
        this.dialogContent.setMaxSize(800, 800);
        this.dialog = MFXGenericDialogBuilder.build(dialogContent)
                .toStageDialogBuilder()
                .initOwner(stage)
                .initModality(Modality.APPLICATION_MODAL)
                .setDraggable(false)
                .setTitle("Dialogs")
                .setOwnerNode(rootAnchorPane)
                .setScrimPriority(ScrimPriority.WINDOW)
                .setScrimOwner(true)
                .get();
        this.dialog.setOnHidden(e -> {
            this.dialog = null;
            this.dialogContent = null;
        });
        this.dialog.showDialog();
    }

    private void hiddenContentDialog() {
        if (this.dialogContent == null || this.dialog == null) {
            return;
        }
        this.dialog.close();
    }
}
