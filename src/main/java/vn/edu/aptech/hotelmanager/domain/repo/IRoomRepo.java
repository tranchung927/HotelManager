package vn.edu.aptech.hotelmanager.domain.repo;

import vn.edu.aptech.hotelmanager.common.repo.IRepo;
import vn.edu.aptech.hotelmanager.domain.model.Account;
import vn.edu.aptech.hotelmanager.domain.model.Room;

import java.util.List;

public interface IRoomRepo extends IRepo {
    List<Room> getListRoom(int page, int pageSize);


}
