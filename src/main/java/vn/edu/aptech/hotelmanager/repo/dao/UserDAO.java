package vn.edu.aptech.hotelmanager.repo.dao;

import vn.edu.aptech.hotelmanager.common.dao.CrudDAO;
import vn.edu.aptech.hotelmanager.repo.entity.UserEntity;

public interface UserDAO extends CrudDAO<UserEntity> {

    String getLastUserId() throws Exception;

    String login(String username,String password,String role) throws Exception;
}