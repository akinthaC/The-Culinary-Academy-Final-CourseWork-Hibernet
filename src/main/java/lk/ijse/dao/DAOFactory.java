package lk.ijse.dao;


import lk.ijse.BO.custom.impl.ProgramBoImpl;
import lk.ijse.BO.custom.impl.StudentBoImpl;
import lk.ijse.BO.custom.impl.UserBoImpl;
import lk.ijse.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {

    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory==null) ?  new DAOFactory(): daoFactory;
    }

    public enum DAOType{
       USER,STUDENT,PROGRAM,INTAKE,STUDENT_PROGRAM,PAYMENT
    }
    public SuperDao getDAO(DAOType daoType){
        switch (daoType) {
            case USER:
                return new UserDaoImpl();
            case STUDENT:
                return new StudentDaoImpl();
            case PROGRAM:
                return new ProgramDaoImpl();
            case INTAKE:
                return new IntakeDaoImpl();
            case STUDENT_PROGRAM:
                return new StudentProgramDaoImpl();
            case PAYMENT:
                return new PaymentDaoImpl();
            default:
                return null;

        }

    }
}
