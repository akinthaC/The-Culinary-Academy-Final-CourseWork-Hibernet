package lk.ijse.BO.custom.impl;

import lk.ijse.BO.custom.UserBo;
import lk.ijse.Entity.Payment;
import lk.ijse.Entity.User;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.UserDao;
import lk.ijse.dto.PaymentDTO;
import lk.ijse.dto.UserDTO;

import java.sql.SQLException;
import java.util.ArrayList;
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

    @Override
    public List<UserDTO> getAllPayment() throws SQLException, ClassNotFoundException {
        List<User> users= userDao.getAll();

        List<UserDTO> userDTOS=new ArrayList<>();

        for (User user : users) {
            userDTOS.add(new UserDTO(
                    user.getUsername(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getJobRole()
            ));
        }
        return userDTOS;
    }

    @Override
    public boolean save(User user) throws SQLException, ClassNotFoundException {
        return userDao.save(user);
    }

    @Override
    public boolean update(User user) throws SQLException, ClassNotFoundException {
        return userDao.update(user);
    }

    @Override
    public boolean delete(User user) throws SQLException, ClassNotFoundException {
        return userDao.deleteUser(new UserDTO(user.getUsername(),user.getPassword(),user.getEmail(),user.getJobRole()));
    }

    @Override
    public boolean updateUser(User user) {
        return userDao.updateUser(new UserDTO(user.getUsername(),user.getPassword(),user.getEmail(),user.getJobRole()));
    }

    @Override
    public boolean updateUserName(String oldUserName, String newUserName) {
        return userDao.UpdateUserName(oldUserName,newUserName);
    }


}
