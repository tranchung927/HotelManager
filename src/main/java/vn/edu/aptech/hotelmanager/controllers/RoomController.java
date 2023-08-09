package vn.edu.aptech.hotelmanager.controllers;

import io.github.palexdev.materialfx.dialogs.MFXGenericDialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import vn.edu.aptech.hotelmanager.domain.REPO_TYPE;
import vn.edu.aptech.hotelmanager.domain.RepoFactory;
import vn.edu.aptech.hotelmanager.domain.model.Room;
import vn.edu.aptech.hotelmanager.domain.repo.IRoomRepo;
import vn.edu.aptech.hotelmanager.repo.dao.impl.RoomDAOImpl;
import vn.edu.aptech.hotelmanager.repo.entity.RoomEntity;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;

public class RoomController implements Initializable {
    private final IRoomRepo roomRepo = RepoFactory.getInstance().getRepo(REPO_TYPE.ROOM);
    private MFXGenericDialog dialogContent;
    private final Stage stage;
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TableView<Room> rooms;

    @FXML
    private TableColumn<Room, Integer> flag;

    @FXML
    private TableColumn<Room, Integer> category;

    @FXML
    private TableColumn<Room, Integer> id;

    @FXML
    private TableColumn<Room, String> name;

    @FXML
    private TableColumn<Room, Integer> numberOfBeds;

    @FXML
    private TableColumn<Room, Double> price;

    @FXML
    private TableColumn<Room, Integer> status;

    private List<Room> roomlist ;
    private List<Room>  setRoomList()  throws Exception {
        RoomDAOImpl roomDAO= new RoomDAOImpl();
         return  roomlist = roomDAO.findAll();
    }


    public RoomController(Stage stage){
        this.stage = stage;
    }
    ObservableList<RoomEntity> roomEntityObservableList;

    {
        try {
            roomEntityObservableList = FXCollections.observableList(
                    setRoomList()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void checkin(ActionEvent event) throws IOException {
        Stage window = new Stage();
        CheckinController checkinController = new CheckinController();
        CheckinController.display(event,window);
    }
    public void occupied(ActionEvent event) throws IOException {
        Stage window = new Stage();
        CheckinController checkinController = new CheckinController();
        CheckinController.display(event,window);
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setCellValueFactory( new PropertyValueFactory<RoomEntity,Integer>("id"));
        name.setCellValueFactory( new PropertyValueFactory<RoomEntity,String>("name"));
        status.setCellValueFactory( new PropertyValueFactory<RoomEntity,Integer>("status"));
        numberOfBeds.setCellValueFactory( new PropertyValueFactory<RoomEntity,Integer>("numberOfBeds"));
        price.setCellValueFactory( new PropertyValueFactory<RoomEntity,Double>("price"));
        category.setCellValueFactory( new PropertyValueFactory<RoomEntity,Integer>("category"));
        flag.setCellValueFactory( new PropertyValueFactory<RoomEntity,Integer>("flag"));
        rooms.setItems(roomEntityObservableList);
    }
}
