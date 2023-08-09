package vn.edu.aptech.hotelmanager.repo;

import javafx.scene.control.Alert;
import vn.edu.aptech.hotelmanager.domain.model.ROOM_STATUS_TYPE;
import vn.edu.aptech.hotelmanager.domain.model.Room;
import vn.edu.aptech.hotelmanager.domain.repo.IRoomRepo;
import vn.edu.aptech.hotelmanager.repo.converter.RoomEntityToRoom;
import vn.edu.aptech.hotelmanager.utils.CrudUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RoomRepoImpl implements IRoomRepo {
    @Override
    public List<Room> getListRoom(int page, int pageSize) {
        List<Room> roomList = new ArrayList<>();
        String url = "SELECT * FROM rooms";
        try {
            ResultSet resultSet  = CrudUtil.execute(url);

            while (resultSet.next()){
                roomList.add(new RoomEntityToRoom().convert(resultSet));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return roomList;
    }


    @Override
    public void insertRoom(Room room) {
        String url = "INSERT INTO rooms (name,`status`,number_of_beds,price,category_id)VALUES" +
                " ('"+room.getName()+"'" +
                ",'"+room.getStatus()+"'" +
                ",'"+room.getNumberOfBeds()+"'" +
                ",'"+room.getPrice()+"'" +
                ",'"+room.getCategoryId()+"')";
        Alert alert;
        try {
            int a = CrudUtil.execute(url);
            if(a>0){

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Add Successfully");
                alert.showAndWait();
            }else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String getLastRoomId(){
        ResultSet rst = null;
        try {
            rst = CrudUtil.execute("SELECT * FROM rooms ORDER BY id DESC LIMIT 1");
            if (!rst.next()) {
                return null;
            } else {
                return rst.getString(1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

}
