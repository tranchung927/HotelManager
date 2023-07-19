package vn.edu.aptech.hotelmanager.repo;

import vn.edu.aptech.hotelmanager.domain.repo.IUserRepo;
import vn.edu.aptech.hotelmanager.repo.dao.DAOFactory;
import vn.edu.aptech.hotelmanager.repo.dao.DAO_TYPE;
import vn.edu.aptech.hotelmanager.repo.dao.UserDAO;

public class UserRepoImpl implements IUserRepo {
    private final UserDAO userDAO = DAOFactory.getInstance().getDAO(DAO_TYPE.USER);
    @Override
    public String login(String username, String password, String role) throws Exception {
        return userDAO.login(username, password, role);
    }
}
