package vn.edu.aptech.hotelmanager.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import vn.edu.aptech.hotelmanager.common.BaseController;
import vn.edu.aptech.hotelmanager.domain.REPO_TYPE;
import vn.edu.aptech.hotelmanager.domain.RepoFactory;;
import vn.edu.aptech.hotelmanager.domain.model.ROOM_STATUS_TYPE;
import vn.edu.aptech.hotelmanager.domain.model.Room;
import vn.edu.aptech.hotelmanager.domain.repo.IRoomRepo;
import vn.edu.aptech.hotelmanager.repo.db.DBConnection;
import vn.edu.aptech.hotelmanager.utils.CrudUtil;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class RoomController extends BaseController implements Initializable {
    private final Room room;
    @FXML
    private MFXButton updateBtn;

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
    private MFXComboBox<?> statusComboBox;
    private final IRoomRepo roomRepo = RepoFactory.getInstance().getRepo(REPO_TYPE.ROOM);

    private IRoomController listener;

    private final Stage stage;
    private String[] statusList = {"Available", "Occupied", "Repair", "Dirty", "Reserve"};

    public RoomController(Stage stage, Room room) {
        this.stage = stage;
        this.room = room == null ? new Room() : room;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addValueStatusComboBox();
        updateUI();
    }
    public void setListener(IRoomController iRoomControllerListener) {
        this.listener = iRoomControllerListener;
    }

    public void addValueStatusComboBox() {
        List<String> list = new ArrayList<>();
        Collections.addAll(list, statusList);
        ObservableList observableList = FXCollections.observableList(list);
        statusComboBox.setItems(observableList);

    }
    public void saveRoomBtn() {
        Room room = new Room();
        try {
            room.setName(roomNameTextField.getText());
            room.setStatus(ROOM_STATUS_TYPE.getStatusStr((String) statusComboBox.getSelectionModel().getSelectedItem()));
            room.setNumberOfBeds(Integer.parseInt(nobTextField.getText()));
            room.setPrice(Double.parseDouble(priceTextField.getText()));
            room.setCategoryId(Long.parseLong(categoryTextField.getText()));

            String url = "INSERT INTO rooms (name,`status`,number_of_beds,price,category_id)VALUES" +
                    " ('" + room.getName() + "'" +
                    ",'" + room.getStatus().toStatus() + "'" +
                    ",'" + room.getNumberOfBeds() + "'" +
                    ",'" + room.getPrice() + "'" +
                    ",'" + room.getCategoryId() + "')";
            Alert alert;
            try {
                boolean a = CrudUtil.execute(url);
                if (a) {

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Add Successfully");
                    alert.showAndWait();
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Please fill all blank fields");
                    alert.showAndWait();
                }

                this.listener.addNewRoom(room);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }


        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateRoom() {
        room.setName(roomNameTextField.getText());
        room.setStatus(ROOM_STATUS_TYPE.getStatusStr((String) statusComboBox.getSelectionModel().getSelectedItem()));
        room.setNumberOfBeds(Integer.parseInt(nobTextField.getText()));
        room.setPrice(Double.parseDouble(priceTextField.getText()));
        room.setCategoryId(Long.parseLong(categoryTextField.getText()));
        Room room1 = null;
        try {
            room1 = roomRepo.creatOrUpdate(room);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (room1 == null) {
            this.showErrorDialog("Error", "An error occurred, please try again!");
            return;
        }
        if (room.getId() > 0) {
            listener.updateRoom(room1);
        } else {
            listener.addNewRoom(room1);
        }
        this.showInfoDialog("Success", room.getId() > 0 ? "Updated Successfully!" : "Created Successfully!", event -> {
            this.hiddenDialog();
        });
            this.listener.updateRoom(room);
    }
    public void updateUI() {
        roomNameTextField.setText(room.getName());
        priceTextField.setText(String.valueOf(room.getPrice()));
        categoryTextField.setText(String.valueOf(room.getCategoryId()));
        nobTextField.setText(String.valueOf(room.getNumberOfBeds()));
    }
    private Connection connection;
    private PreparedStatement prepared;
    private ResultSet result;
    public void deleteBtn() {

        if (room.getId() > 0) {
            String url = "DELETE FROM rooms WHERE id = '" + room.getId() + "'";

            connection = DBConnection.getInstance().getConnection();
            Alert alert;
            try {


                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure to delete ?");

                Optional<ButtonType> optional = alert.showAndWait();

                if (optional.get().equals(ButtonType.OK)) {
                    prepared = connection.prepareStatement(url);
                    prepared.executeUpdate();


                    this.listener.deleteRoom(room);
                } else {
                    return;
                }

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Delete Successfully !");
                alert.showAndWait();


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } else {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please select room !");
            alert.showAndWait();
        }

    }

}