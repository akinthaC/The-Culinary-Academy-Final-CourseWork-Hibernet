package lk.ijse.BO.custom.impl;

import lk.ijse.BO.custom.UserBo;
import lk.ijse.Entity.User;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.UserDao;

import java.sql.SQLException;
import java.util.List;

public class UserBoImpl implements UserBo {
    UserDao userDao = (UserDao) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.USER);;
    @Override
    public User searchByName(String name) throws SQLException, ClassNotFoundException {
        return userDao.searchByName(name);
    }

    @Override
    public boolean update(String Password, String userName) throws SQLException, ClassNotFoundException {
        return userDao.update(Password, userName);

    }

    @Override
    public List<String> getUserNames() {
        return userDao.getUserNames();
    }


}
