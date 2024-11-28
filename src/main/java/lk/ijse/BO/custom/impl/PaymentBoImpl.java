package lk.ijse.BO.custom.impl;

import lk.ijse.BO.custom.PaymentBo;
import lk.ijse.Entity.Payment;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.PaymentDao;
import lk.ijse.dto.PaymentDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentBoImpl implements PaymentBo {
    PaymentDao paymentDao = (PaymentDao) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.PAYMENT );;

    @Override
    public String getCurrentId() throws SQLException, ClassNotFoundException {
        return paymentDao.getCurrentId();
    }

    @Override
    public boolean savePayment(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException {
        return paymentDao.save(new Payment(paymentDTO.getPaymentID(), paymentDTO.getPaymentDate(),paymentDTO.getPaymentType(),paymentDTO.getPayAmount(),paymentDTO.getAmountToBePay(),paymentDTO.getTotalAmount(),paymentDTO.getPaymentPlan(),paymentDTO.getPaymentStatus(),paymentDTO.getStudentProgram(),paymentDTO.getStudent()));
    }

    @Override
    public List<PaymentDTO> getAllPayment() throws SQLException, ClassNotFoundException {
        List<Payment> payments= paymentDao.getAll();

        List<PaymentDTO> paymentDTOS=new ArrayList<>();

        for (Payment payment : payments) {
            paymentDTOS.add(new PaymentDTO(
                    payment.getPaymentID(),
                    payment.getPaymentDate(),
                    payment.getPaymentType(),
                    payment.getPayAmount(),
                    payment.getAmountToBePay(),
                    payment.getTotalAmount(),
                    payment.getPaymentPlan(),
                    payment.getPaymentStatus(),
                    payment.getStudentProgram(),
                    payment.getStudent()
            ));
        }
        return paymentDTOS;
    }

    @Override
    public List<Payment> searchByStuId(String studentId) {
       return paymentDao.searchByStuId(studentId);
    }

    @Override
    public Payment searchById(String payNo) throws SQLException, ClassNotFoundException {
        return  paymentDao.searchById(payNo);
    }

    @Override
    public boolean update(PaymentDTO paymentDTO) throws SQLException, ClassNotFoundException {
        return paymentDao.update(new Payment(paymentDTO.getPaymentID(), paymentDTO.getPaymentDate(),paymentDTO.getPaymentType(),paymentDTO.getPayAmount(),paymentDTO.getAmountToBePay(),paymentDTO.getTotalAmount(),paymentDTO.getPaymentPlan(),paymentDTO.getPaymentStatus(),paymentDTO.getStudentProgram(),paymentDTO.getStudent()));
    }


}
