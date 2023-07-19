package vn.edu.aptech.hotelmanager.repo.dao;

import vn.edu.aptech.hotelmanager.common.dao.CrudDAO;
import vn.edu.aptech.hotelmanager.repo.entity.RoomEntity;

public interface RoomDAO extends CrudDAO<RoomEntity,String> {
    public String getLastRoomId() throws Exception;
}
