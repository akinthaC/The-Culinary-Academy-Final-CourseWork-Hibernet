package lk.ijse.confit.BO;


import lk.ijse.BO.custom.impl.CustomerBoImpl;
import lk.ijse.BO.custom.impl.ItemBoImpl;
import lk.ijse.BO.custom.impl.OrderBoImpl;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {}

    public static BOFactory getBoFactory(){
        return (boFactory == null) ? new BOFactory() : boFactory;
    }

    public enum BOType{
        CUSTOMER,Item,ORDER
    }
    public SuperBo GetBo(BOType boType){
        switch (boType) {
            case CUSTOMER:
                return new CustomerBoImpl();

            case Item:
                return new ItemBoImpl();

            case ORDER:
                return new OrderBoImpl();

            default:
                return null;

        }

    }
}
