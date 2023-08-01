package vn.edu.aptech.hotelmanager.common.dao;

import vn.edu.aptech.hotelmanager.common.entity.IEntity;

import java.io.Serializable;
import java.util.List;

public interface CrudDAO <T extends IEntity> extends DAO {
    List<T> findAll() throws Exception;

    T find(int id) throws Exception;

    boolean save(T entity) throws Exception;

    boolean update(T entity) throws Exception;

    boolean delete(int id) throws Exception;
}
