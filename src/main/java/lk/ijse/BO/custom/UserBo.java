package lk.ijse.BO.custom;

import lk.ijse.BO.SuperBo;
import lk.ijse.Entity.User;
import lk.ijse.dto.UserDTO;

import java.sql.SQLException;
import java.util.List;

public interface UserBo extends SuperBo {
    public User searchByName(String name) throws SQLException, ClassNotFoundException;
    public  boolean update(String Password, String userName) throws SQLException, ClassNotFoundException ;
    List<String> getUserNames();

    List<UserDTO> getAllPayment() throws SQLException, ClassNotFoundException;

    boolean save(User user) throws SQLException, ClassNotFoundException;

    boolean update(User user) throws SQLException, ClassNotFoundException;

    boolean delete(User user) throws SQLException, ClassNotFoundException;

    boolean updateUser(User user);

    boolean updateUserName(String oldUserName, String newUserName);
}
