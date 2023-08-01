package vn.edu.aptech.hotelmanager.repo.dao;

import vn.edu.aptech.hotelmanager.common.dao.CrudDAO;
import vn.edu.aptech.hotelmanager.repo.entity.RoomEntity;

public interface RoomDAO extends CrudDAO<RoomEntity> {
    public int getLastRoomId() throws Exception;
}
