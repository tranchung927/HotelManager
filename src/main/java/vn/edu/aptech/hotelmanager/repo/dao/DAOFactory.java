package vn.edu.aptech.hotelmanager.repo.dao;

import vn.edu.aptech.hotelmanager.common.dao.DAO;
import vn.edu.aptech.hotelmanager.repo.dao.impl.CityDAOImpl;
import vn.edu.aptech.hotelmanager.repo.dao.impl.RoomDAOImpl;
import vn.edu.aptech.hotelmanager.repo.dao.impl.UserDAOImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }
    public static DAOFactory getInstance() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public <T extends DAO> T getDAO(DAO_TYPE type) {
        return switch (type) {
            case ROOM -> (T) new RoomDAOImpl();
            case USER -> (T) new UserDAOImpl();
            case CITY -> (T) new CityDAOImpl();
        };
    }
}
