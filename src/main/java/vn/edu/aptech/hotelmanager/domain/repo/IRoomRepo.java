package vn.edu.aptech.hotelmanager.domain.repo;

import vn.edu.aptech.hotelmanager.common.repo.IRepo;
import vn.edu.aptech.hotelmanager.domain.dto.RoomDTO;
import vn.edu.aptech.hotelmanager.domain.model.ROOM_STATUS_TYPE;
import vn.edu.aptech.hotelmanager.domain.model.Room;
import vn.edu.aptech.hotelmanager.domain.model.RoomStatusSummary;

import java.util.List;

public interface IRoomRepo extends IRepo {

    List<RoomStatusSummary> getSummaryForStatus();
    List<RoomDTO> getListRoom(ROOM_STATUS_TYPE statusType);
    void checkIn(RoomDTO roomDTO) throws Exception;
    void checkOut(RoomDTO roomDTO) throws Exception;
    Room creatOrUpdate(Room room) throws Exception;

    Boolean deleteRoom(Long id) throws Exception;
    Boolean updateStatus(Long id, ROOM_STATUS_TYPE status);

    RoomDTO getById(long id);
}
