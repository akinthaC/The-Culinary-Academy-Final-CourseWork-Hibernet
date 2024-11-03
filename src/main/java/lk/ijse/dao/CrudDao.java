package lk.ijse.dao;

import java.sql.SQLException;
import java.util.List;

public interface CrudDao<T>  extends SuperDao{
    public  boolean save(T DTO) throws SQLException, ClassNotFoundException;
    public  String getCurrentId() throws SQLException, ClassNotFoundException;
    public  List<T> getAll() throws SQLException, ClassNotFoundException;

    public  boolean update(T DTO) throws SQLException, ClassNotFoundException;

    public  boolean delete(T DTO) throws SQLException, ClassNotFoundException;

    public  T searchById(String id) throws SQLException, ClassNotFoundException;

  /*
    public  T searchById(String id) throws SQLException, ClassNotFoundException;

    public  List<String> getIds() throws SQLException, ClassNotFoundException;*/



}
