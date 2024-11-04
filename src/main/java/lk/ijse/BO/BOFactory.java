package lk.ijse.BO;


import lk.ijse.BO.custom.impl.UserBoImpl;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {}

    public static BOFactory getBoFactory(){
        return (boFactory == null) ? new BOFactory() : boFactory;
    }

    public enum BOType{
       USER
    }
    public SuperBo GetBo(BOType boType){
        switch (boType) {

            case USER:
                return new UserBoImpl();
            default:
                return null;

        }

    }
}
