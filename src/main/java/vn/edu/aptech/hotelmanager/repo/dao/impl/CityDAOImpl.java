package vn.edu.aptech.hotelmanager.repo.dao.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import vn.edu.aptech.hotelmanager.repo.dao.CityDAO;
import vn.edu.aptech.hotelmanager.repo.entity.CityEntity;
import vn.edu.aptech.hotelmanager.utils.HibernateUtils;
import java.util.List;

public class CityDAOImpl implements CityDAO {
    @Override
    public List<CityEntity> findAll() throws Exception {
        Transaction transaction = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            // start a transaction
            transaction = session.beginTransaction();

            // get Student entity using get() method
            List<CityEntity> cityEntities = session.createQuery("FROM CityEntity", CityEntity.class).list();

            // commit transaction
            transaction.commit();
            return cityEntities;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public CityEntity find(int id) throws Exception {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.find(CityEntity.class, id);
        }
    }

    @Override
    public boolean save(CityEntity entity) throws Exception {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
            return true;
        }
    }

    @Override
    public boolean update(CityEntity entity) throws Exception {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            session.persist(entity);
            session.getTransaction().commit();
            return true;
        }
    }

    @Override
    public boolean delete(int id) throws Exception {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            CityEntity cityEntity = new CityEntity();
//            cityEntity.setId(id);
            session.remove(cityEntity);
            session.getTransaction().commit();
            return true;
        }
    }
}
