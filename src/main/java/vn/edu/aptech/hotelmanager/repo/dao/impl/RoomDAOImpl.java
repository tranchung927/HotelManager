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
        ResultSet rst = CrudUtil.execute("SELECT * FROM room ORDER BY RoomId DESC LIMIT 1");
        if (!rst.next()) {
            return null;
        } else {
            return rst.getString(1);
        }
    }

    @Override
    public List<RoomEntity> findAll() throws Exception {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Room");
        List<RoomEntity> roomList = new ArrayList<>();
        while (resultSet.next()) {
            roomList.add(new RoomEntity(
                    resultSet.getInt(1)
                    , resultSet.getString(2)
                    , resultSet.getString(3)
                    , resultSet.getString(4)
                    , resultSet.getDouble(5)
                    , resultSet.getString(6)
                    , resultSet.getInt(7)));
        }
        return roomList;
    }

    @Override
    public RoomEntity find(String key) throws Exception {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM room WHERE RoomName =?", key);
        if (resultSet.next()) {
            return new RoomEntity(
                    resultSet.getInt(1)
                    , resultSet.getString(2)
                    , resultSet.getString(3)
                    , resultSet.getString(4)
                    , resultSet.getDouble(5)
                    , resultSet.getString(6)
                    , resultSet.getInt(7));
        }
        return null;
    }

    @Override
    public boolean save(RoomEntity room) throws Exception {
        return CrudUtil.execute("INSERT INTO room VALUES (?,?,?,?,?,?,?)",
                room.getRoomId(), room.getRoomName(), room.getRoomType(), room.getBedType(), room.getPrice(),room.getStatus(), room.getNumberOfBed());
    }

    @Override
    public boolean update(RoomEntity room) throws Exception {
        return CrudUtil.execute("UPDATE room SET RoomName=?,RoomTypeID=?,BedTypeId=?,Price=?,StatusID=?,NumberOfBed=? WHERE RoomId=?",
                room.getRoomName(), room.getRoomType(), room.getBedType(), room.getPrice(),room.getStatus(), room.getNumberOfBed());
    }

    @Override
    public boolean delete(String key) throws Exception {
        return CrudUtil.execute("DELETE FROM Room WHERE roomNumber=?", key);
    }
}
