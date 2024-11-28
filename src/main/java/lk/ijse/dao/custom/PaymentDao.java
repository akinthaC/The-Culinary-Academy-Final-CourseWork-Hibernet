package lk.ijse.dao.custom;

import lk.ijse.Entity.Payment;
import lk.ijse.dao.CrudDao;

import java.util.List;

public interface PaymentDao extends CrudDao<Payment> {
    List<Payment> searchByStuId(String studentId);
}
