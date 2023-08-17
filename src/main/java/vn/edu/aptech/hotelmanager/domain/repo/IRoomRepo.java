package vn.edu.aptech.hotelmanager.domain.repo;

import vn.edu.aptech.hotelmanager.common.repo.IRepo;
import vn.edu.aptech.hotelmanager.domain.dto.RoomDTO;
import vn.edu.aptech.hotelmanager.domain.model.Room;

import java.util.List;

public interface IRoomRepo extends IRepo {
    List<RoomDTO> getListRoom(int page, int pageSize);
    RoomDTO createOrUpdate(RoomDTO roomDTO) throws Exception;
    Boolean deleteRoom(Long id) throws Exception;
}
