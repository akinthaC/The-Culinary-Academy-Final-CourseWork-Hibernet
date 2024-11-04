package lk.ijse.dao.custom.impl;

import lk.ijse.Entity.User;
import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.custom.UserDao;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public User searchByName(String name) throws SQLException, ClassNotFoundException {

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        User user1 = null;
        try {
            user1 = (User) session.createQuery("FROM User WHERE username = :name")
                    .setParameter("name", name)
                    .uniqueResult();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return user1;
    }

    @Override
    public boolean update(String password, String userName) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            int result = session.createQuery("UPDATE User SET password = :password WHERE username = :name")
                    .setParameter("password", password)
                    .setParameter("name", userName)
                    .executeUpdate();

            transaction.commit();
            return result > 0; // Returns true if at least one record was updated
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }


    @Override
    public boolean save(User DTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(User DTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<User> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String getCurrentId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public User searchById(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<String> getIds() throws SQLException, ClassNotFoundException {
        return null;
    }
}


