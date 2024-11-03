package lk.ijse.dao;


import lk.ijse.dao.custom.impl.CustomerDaoImpl;
import lk.ijse.dao.custom.impl.ItemDaoImpl;
import lk.ijse.dao.custom.impl.OrderDaoImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {

    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory==null) ?  new DAOFactory(): daoFactory;
    }

    public enum DAOType{
       CUSTOMER,ITEM,ORDER
    }
    public SuperDao getDAO(DAOType daoType){
        switch (daoType) {
            case CUSTOMER:
                return new CustomerDaoImpl();

            case ITEM:
                return new ItemDaoImpl();

            case ORDER:
                return new OrderDaoImpl();
            default:
                return null;

        }

    }
}
