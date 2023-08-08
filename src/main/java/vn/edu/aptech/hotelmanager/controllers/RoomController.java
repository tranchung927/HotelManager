package vn.edu.aptech.hotelmanager.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import io.github.palexdev.materialfx.dialogs.MFXGenericDialogBuilder;
import io.github.palexdev.materialfx.dialogs.MFXStageDialog;
import io.github.palexdev.materialfx.enums.ScrimPriority;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import vn.edu.aptech.hotelmanager.domain.REPO_TYPE;
import vn.edu.aptech.hotelmanager.domain.RepoFactory;
import vn.edu.aptech.hotelmanager.domain.repo.ILocationRepo;
import vn.edu.aptech.hotelmanager.domain.repo.IRoomRepo;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class RoomController implements Initializable {
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

    private final Stage stage;
    private String[] statusList = {"Available", "Occupied", "Repair", "Dirty", "Reserve"};

    @FXML
    private GridPane grid;

    public RoomController(Stage stage, Object o) {
        this.stage = stage;
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
}
