package vn.edu.aptech.hotelmanager.repo;

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

}
