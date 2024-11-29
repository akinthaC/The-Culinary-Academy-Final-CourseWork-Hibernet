package lk.ijse.dao.custom.impl;

import lk.ijse.Entity.User;
import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.custom.UserDao;
import lk.ijse.dto.UserDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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
    public List<String> getUserNames() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        List<String> usernames = null;
        try {
            usernames = session.createQuery("SELECT u.username FROM User u", String.class).getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return usernames;
    }

    @Override
    public boolean saveUser(UserDTO userDTO) {
        return false;
    }

    @Override
    public boolean updateUser(UserDTO userDTO) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();


            Query query = session.createQuery("FROM User WHERE username = :username");
            query.setParameter("username", userDTO.getUsername());
            User existingUser = (User) query.uniqueResult();

            if (existingUser != null) {
                // Update the properties
                existingUser.setPassword(userDTO.getPassword());
                existingUser.setEmail(userDTO.getEmail());
                existingUser.setJobRole(userDTO.getJobRole());

                // Save the updated entity
                session.update(existingUser);

                transaction.commit();
                return true;
            } else {
                return false; // User not found
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean deleteUser(UserDTO userDTO) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // HQL delete query
            Query query = session.createQuery("DELETE FROM User WHERE username = :username");
            query.setParameter("username", userDTO.getUsername());

            int result = query.executeUpdate(); // Returns the number of rows affected

            transaction.commit();
            return result > 0; // Return true if at least one record was deleted
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean UpdateUserName(String oldUserName, String newUserName) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            int result = session.createQuery("UPDATE User SET username = :newName WHERE username = :name")
                    .setParameter("newName", newUserName)
                    .setParameter("name", oldUserName)
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
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(DTO);
        transaction.commit();
        session.close();
        System.out.println(transaction.getStatus());
        if (transaction.getStatus().toString().equals("COMMITTED")){
            return true;
        }
        return false;

    }

    @Override
    public boolean update(User DTO) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(DTO);
        transaction.commit();
        session.close();
        System.out.println(transaction.getStatus());
        if (transaction.getStatus().toString().equals("COMMITTED")){
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(User DTO) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.delete(DTO);
        transaction.commit();
        session.close();
        System.out.println(transaction.getStatus());
        if (transaction.getStatus().toString().equals("COMMITTED")){
            return true;
        }
        return false;
    }


    @Override
    public List<User> getAll() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        List<User> users = session.createQuery("from User ").list();
        transaction.commit();
        session.close();
        return users;
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


