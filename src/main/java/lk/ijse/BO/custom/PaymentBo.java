package lk.ijse.BO.custom;

import lk.ijse.BO.SuperBo;
import lk.ijse.dto.PaymentDTO;

import java.sql.SQLException;
import java.util.List;

public interface PaymentBo extends SuperBo {
    String getCurrentId() throws SQLException, ClassNotFoundException;

    boolean savePayment(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException;

    List<PaymentDTO> getAllPayment() throws SQLException, ClassNotFoundException;
}
