package vn.edu.aptech.hotelmanager.repo.dao.impl;

import vn.edu.aptech.hotelmanager.utils.CrudUtil;
import vn.edu.aptech.hotelmanager.repo.dao.RoomDAO;
import vn.edu.aptech.hotelmanager.repo.entity.RoomEntity;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RoomDAOImpl implements RoomDAO {
    @Override
    public int getLastRoomId() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM room ORDER BY id DESC LIMIT 1");
        if (!rst.next()) {
            return -1;
        } else {
            return rst.getInt(1);
        }
    }
    @Override
    public List<RoomEntity> findAll() throws Exception {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM room");
        List<RoomEntity> roomList =  new ArrayList<>();
        while (resultSet.next()) {
            RoomEntity room = resultSet.get//getObject(1, RoomEntity.class);
            roomList.add(room);
        }
        return roomList;
    }

    @Override
    public RoomEntity find(int id) throws Exception {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM room WHERE id=?",id);
        if (resultSet.next()) {
            return resultSet.getObject(1, RoomEntity.class);
        }
        return null;
    }

    @Override
    public boolean save(RoomEntity room) throws Exception {
        return CrudUtil.execute("INSERT INTO room VALUES (?,?,?)",
                room.getRoomNumber(),room.getTypeId(),room.getRoomStatus());
    }

    @Override
    public boolean update(RoomEntity room) throws Exception {
        return CrudUtil.execute("UPDATE Room SET typeId=?,roomStatus=? WHERE roomNumber=?",
                room.getTypeId(),room.getRoomStatus(),room.getRoomNumber());
    }

    @Override
    public boolean delete(int id) throws Exception {
        return CrudUtil.execute("DELETE FROM Room WHERE id=?", id);
    }
}
