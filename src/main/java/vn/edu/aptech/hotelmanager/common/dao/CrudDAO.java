package vn.edu.aptech.hotelmanager.common.dao;

import vn.edu.aptech.hotelmanager.common.entity.IEntity;

import java.io.Serializable;
import java.util.List;

public interface CrudDAO <T extends IEntity, ID extends Serializable> extends DAO {
    List<T> findAll() throws Exception;

    T find(ID key) throws Exception;

    boolean save(T entity) throws Exception;

    boolean update(T entity) throws Exception;

    boolean delete(ID key) throws Exception;
}
