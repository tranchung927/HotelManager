package vn.edu.aptech.hotelmanager.domain.repo;

import vn.edu.aptech.hotelmanager.common.repo.IRepo;

public interface IUserRepo extends IRepo {
    String login(String username, String password, String role) throws Exception;
}
