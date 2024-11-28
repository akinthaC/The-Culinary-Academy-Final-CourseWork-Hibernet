package lk.ijse.dao.custom;

import lk.ijse.Entity.User;
import lk.ijse.dao.CrudDao;

import java.sql.SQLException;
import java.util.List;

public interface UserDao extends CrudDao<User> {
    public User searchByName(String name) throws SQLException, ClassNotFoundException;
    public  boolean update(String Password, String userName) throws SQLException, ClassNotFoundException;

    List<String> getUserNames();
}
