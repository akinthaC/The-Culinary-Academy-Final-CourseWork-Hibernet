package lk.ijse.BO;


import lk.ijse.BO.custom.impl.*;
import lk.ijse.dao.custom.impl.ProgramDaoImpl;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {}

    public static BOFactory getBoFactory(){
        return (boFactory == null) ? new BOFactory() : boFactory;
    }

    public enum BOType{
       USER,STUDENT,PROGRAM,INTAKE,STUDENT_PROGRAM,PAYMENT
    }
    public SuperBo GetBo(BOType boType){
        switch (boType) {

            case USER:
                return new UserBoImpl();
            case STUDENT:
                return new StudentBoImpl();
            case PROGRAM:
                return new ProgramBoImpl();
            case INTAKE:
                return new IntakeBoImpl();
            case STUDENT_PROGRAM:
                return new StudentProgramBoImpl();
            case PAYMENT:
                return new PaymentBoImpl();
            default:
                return null;

        }

    }
}
