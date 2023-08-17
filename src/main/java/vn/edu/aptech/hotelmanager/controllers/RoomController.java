package vn.edu.aptech.hotelmanager.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import io.github.palexdev.materialfx.enums.ScrimPriority;
import io.github.palexdev.mfxresources.fonts.MFXFontIcon;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import vn.edu.aptech.hotelmanager.HMResourcesLoader;
import vn.edu.aptech.hotelmanager.common.BaseController;
import vn.edu.aptech.hotelmanager.domain.REPO_TYPE;
import vn.edu.aptech.hotelmanager.domain.RepoFactory;
import vn.edu.aptech.hotelmanager.domain.dto.CustomerDTO;
import vn.edu.aptech.hotelmanager.domain.model.Account;
import vn.edu.aptech.hotelmanager.domain.model.ROOM_STATUS_TYPE;
import vn.edu.aptech.hotelmanager.domain.model.Room;
import vn.edu.aptech.hotelmanager.domain.repo.ILocationRepo;
import vn.edu.aptech.hotelmanager.domain.repo.IRoomRepo;
import vn.edu.aptech.hotelmanager.utils.CrudUtil;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class RoomController extends BaseController implements Initializable {
    private final IRoomRepo roomRepo = RepoFactory.getInstance().getRepo(REPO_TYPE.ROOM);
    private MFXGenericDialog dialogContent;
    private MFXStageDialog dialog;
    private final Stage stage;


    @FXML
    private MFXTextField categoryTextField;

    @FXML
    private MFXButton deleteRoomBtn;

    @FXML
    private MFXTextField nobTextField;

    @FXML
    private MFXTextField priceTextField;

    @FXML
    private MFXTextField roomNameTextField;

    @FXML
    private MFXButton saveRoomBtn;

    @FXML
    private MFXComboBox<ROOM_STATUS_TYPE> statusComboBox;

    @FXML
    private GridPane grid;
    private IRoomController listener;
    private Room room;

    public RoomController(Stage stage, Room room) {
        this.stage = stage;
        this.room = room == null ? new Room() : room;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateUI();

    }

    public void setListener(IRoomController listener) {
        this.listener = listener;
    }

    public void updateUI() {
        roomNameTextField.setText(room.getName());
        priceTextField.setText(String.valueOf(room.getPrice()));
        List<ROOM_STATUS_TYPE> roomStatusTypeList = new ArrayList<>();
        roomStatusTypeList.add(ROOM_STATUS_TYPE.AVAILABLE);
        roomStatusTypeList.add(ROOM_STATUS_TYPE.RESERVE);
        roomStatusTypeList.add(ROOM_STATUS_TYPE.DIRTY);
        roomStatusTypeList.add(ROOM_STATUS_TYPE.OCCUPIED);
        ObservableList<ROOM_STATUS_TYPE> roomStatusTypes = FXCollections.observableList(roomStatusTypeList);
        statusComboBox.setItems(roomStatusTypes);
        statusComboBox.selectItem(room.getStatus());
        statusComboBox.getSelectionModel().selectedItemProperty().addListener((option, oldValue, newValue) -> {
            if (oldValue != newValue) {
                room.setStatus(newValue);
            }
        });
        categoryTextField.setText(String.valueOf(room.getCategoryId()));
        nobTextField.setText(String.valueOf(room.getNumberOfBeds()));
    }
    @FXML
    private void deleteRoom() {
        Boolean isSuccess = false;
        try {
            isSuccess = roomRepo.deleteRoom(room.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (isSuccess) {
            this.listener.deleteRoom(room);
            this.showInfoDialog("Success", "Delete Successfully!", event -> {
                this.hiddenDialog();
                // Đóng màn account
            });
            return;
        }
        this.showErrorDialog("Error", "An error occurred, please try again!");
    }

    @FXML
    private void onClickSaveOrUpdate() {
        room.setName(roomNameTextField.getText());
        room.setStatus(statusComboBox.getSelectionModel().getSelectedItem());
        room.setNumberOfBeds(Integer.parseInt(nobTextField.getText()));
        room.setPrice(Double.parseDouble(priceTextField.getText()));
        room.setCategoryId(Long.parseLong(categoryTextField.getText()));

        if (room.getName() == null
                || room.getStatus() == null
                || room.getNumberOfBeds() == 0
                || room.getPrice() == 0
                || room.getCategoryId() == 0) {
            this.showErrorDialog("Error", "Please fill all blank fields");
        } else {

            Room room1 = null;
            try {
                room1 = roomRepo.creatOrUpdate(room);
                if (room.getId() > 0) {
                    listener.updateRoom(room1);
                } else {
                    listener.addNewRoom(room1);
                }
                this.showInfoDialog("Success", room.getId() > 0 ? "Updated Successfully!" : "Created Successfully!", event -> {
                    this.hiddenDialog();
                });
            } catch (Exception e) {
                e.printStackTrace();
                this.showErrorDialog("Error", "An error occurred, please try again!");
            }
        }
    }

}