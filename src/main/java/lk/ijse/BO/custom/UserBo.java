package lk.ijse.BO.custom;

import lk.ijse.BO.SuperBo;
import lk.ijse.Entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserBo extends SuperBo {
    public User searchByName(String name) throws SQLException, ClassNotFoundException;
    public  boolean update(String Password, String userName) throws SQLException, ClassNotFoundException ;
    List<String> getUserNames();

}
