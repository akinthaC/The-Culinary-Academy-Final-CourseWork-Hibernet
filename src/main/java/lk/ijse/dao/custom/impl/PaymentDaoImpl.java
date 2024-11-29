package lk.ijse.dao.custom.impl;

import lk.ijse.Entity.Payment;
import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.custom.PaymentDao;
import lk.ijse.dto.UserDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class PaymentDaoImpl implements PaymentDao {
    @Override
    public boolean save(Payment DTO) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(DTO);

        transaction.commit();
        session.close();
        System.out.println(transaction.getStatus());
        if (transaction.getStatus().toString().equals("COMMITTED")){
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Payment DTO) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(DTO);

        transaction.commit();
        session.close();
        System.out.println(transaction.getStatus());
        if (transaction.getStatus().toString().equals("COMMITTED")){
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Payment DTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<Payment> getAll() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        List<Payment> payments = session.createQuery("from Payment ").list();
        transaction.commit();
        session.close();
        return payments;
    }

    @Override
    public String getCurrentId() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Object payment = session.createQuery("SELECT paymentID FROM Payment ORDER BY paymentID DESC LIMIT 1").uniqueResult();
        String id ="";
        if (payment != null) {
            String customerId = payment.toString();

            // Split based on "STU20" to extract the numeric part
            String[] split = customerId.split("(?i)PAY");

            // Ensure there's a numeric part available after splitting
            if (split.length > 1) {
                int idNum = Integer.parseInt(split[1]); // Get the numeric part
                id = "PAY" + String.format("%03d", ++idNum); // Increment and format
            } else {
                id = "PAY001";
            }
        } else {
            id = "PAY001";
        }

        transaction.commit();

        session.close();
        return id;
    }

    @Override
    public Payment searchById(String id) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Payment payment = (Payment) session.createQuery("from Payment where paymentID = :id")
                .setParameter("id", id)
                .uniqueResult();
        transaction.commit();
        session.close();
        return payment;
    }

    @Override
    public List<String> getIds() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<Payment> searchByStuId(String studentId) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();


        // HQL query
        Query<Payment> query = session.createQuery("FROM Payment p WHERE p.student.id = :studentId", Payment.class);
        query.setParameter("studentId", studentId);

        List<Payment> payments = query.getResultList();
        session.getTransaction().commit();
        session.close();

        return payments;

    }
}
