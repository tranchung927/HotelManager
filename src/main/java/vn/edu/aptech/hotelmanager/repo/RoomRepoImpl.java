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

    @Override
    public Room creatOrUpdate(Room room) throws Exception {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM rooms WHERE id = ?",room.getId());
        String sql;
        if (resultSet.next()){
            sql = "UPDATE rooms SET name = ? , status = ? , number_of_beds = ? , price = ? , category_id = ? WHERE is =?";
        }else {
            sql = "INSERT INTO rooms(name,status,number_of_beds,price,category_id) VALUES (?,?,?,?,?)";
            ResultSet set = CrudUtil.execute("SELECT * FROM rooms ORDER BY id DESC LIMIT 1");
            room.setId(set.getLong("id"));

        }
        CrudUtil.execute(sql,room.getName(),room.getStatus(),room.getNumberOfBeds(),room.getPrice(),room.getId());
            return room;
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

    @Override
    public Boolean deleteRoom(Long id) throws Exception {
        String url = "DELETE FROM rooms WHERE id = ?";
        CrudUtil.execute(url, id);
        return true;
    }


}
