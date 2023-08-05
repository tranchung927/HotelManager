package vn.edu.aptech.hotelmanager.repo.dao.impl;

import vn.edu.aptech.hotelmanager.utils.CrudUtil;
import vn.edu.aptech.hotelmanager.repo.dao.RoomDAO;
import vn.edu.aptech.hotelmanager.repo.entity.RoomEntity;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RoomDAOImpl implements RoomDAO {
    @Override
    public String getLastRoomId() throws Exception {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Room ORDER BY roomNumber DESC LIMIT 1");
        if (!rst.next()) {
            return null;
        } else {
            return rst.getString(1);
        }
    }
    @Override
    public List<RoomEntity> findAll() throws Exception {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Room");
        List<RoomEntity> roomList =  new ArrayList<>();
        while (resultSet.next()) {
            roomList.add(new RoomEntity(resultSet.getString(1),resultSet.getInt(2),resultSet.getString(3)));
        }
        return roomList;
    }

    @Override
    public RoomEntity find(String key) throws Exception {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Room WHERE roomNumber=?",key);
        if (resultSet.next()) {
            return new RoomEntity(resultSet.getString(1), resultSet.getInt(2), resultSet.getString(3));
        }
        return null;
    }

    @Override
    public boolean save(RoomEntity room) throws Exception {
        return CrudUtil.execute("INSERT INTO Room VALUES (?,?,?)",
                room.getRoomNumber(),room.getTypeId(),room.getRoomStatus());
    }

    @Override
    public boolean update(RoomEntity room) throws Exception {
        return CrudUtil.execute("UPDATE Room SET typeId=?,roomStatus=? WHERE roomNumber=?",
                room.getTypeId(),room.getRoomStatus(),room.getRoomNumber());
    }

    @Override
    public boolean delete(String key) throws Exception {
        return CrudUtil.execute("DELETE FROM Room WHERE roomNumber=?",key);
    }
}