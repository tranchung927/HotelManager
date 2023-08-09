package vn.edu.aptech.hotelmanager.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import vn.edu.aptech.hotelmanager.domain.REPO_TYPE;
import vn.edu.aptech.hotelmanager.domain.RepoFactory;
import vn.edu.aptech.hotelmanager.domain.model.ROOM_STATUS_TYPE;
import vn.edu.aptech.hotelmanager.domain.model.Room;
import vn.edu.aptech.hotelmanager.domain.repo.IRoomRepo;
import vn.edu.aptech.hotelmanager.repo.RoomRepoImpl;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class RoomController implements Initializable {
    private final Room room;
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
    };

    public RoomController(Stage stage, Object o, Room room) {
        this.stage = stage;
        this.room = room;
    }


    public void setListener(IRoomController iRoomControllerListener) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addValueStatusComboBox();
    }

    public void addValueStatusComboBox() {
        List<String> list = new ArrayList<>();
        for (String status : statusList){
            list.add(status);
        }
        ObservableList observableList = FXCollections.observableList(list);
        statusComboBox.setItems(observableList);

    }

    public void saveRoomBtn(){
        try {
            room.setName(roomNameTextField.getText());
            room.setStatus(ROOM_STATUS_TYPE.getStatusStr((String) statusComboBox.getSelectionModel().getSelectedItem()));
            room.setNumberOfBeds(Integer.parseInt(nobTextField.getText()));
            room.setPrice(Double.parseDouble(priceTextField.getText()));
            room.setCategoryId(Long.parseLong(categoryTextField.getText()));

            RoomRepoImpl repo = RepoFactory.getInstance().getRepo(REPO_TYPE.ROOM);
            repo.insertRoom(room);

            this.listener.addNewRoom(room);

        }catch (Exception e){
            throw new RuntimeException(e);
        }





    }
}
