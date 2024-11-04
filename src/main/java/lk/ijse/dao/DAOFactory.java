package lk.ijse.dao;


import lk.ijse.BO.custom.impl.UserBoImpl;
import lk.ijse.dao.custom.impl.UserDaoImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {

    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory==null) ?  new DAOFactory(): daoFactory;
    }

    public enum DAOType{
       USER
    }
    public SuperDao getDAO(DAOType daoType){
        switch (daoType) {
            case USER:
                return new UserDaoImpl();
            default:
                return null;

        }

    }
}
