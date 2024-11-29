package lk.ijse.dao.custom.impl;

import lk.ijse.Entity.Student;
import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.custom.StudentDao;
import lk.ijse.dto.UserDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;

public class StudentDaoImpl implements StudentDao {
    @Override
    public boolean save(Student DTO) throws SQLException, ClassNotFoundException {
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
    public boolean update(Student DTO) throws SQLException, ClassNotFoundException {
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
    public boolean delete(Student DTO) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.delete(DTO);

        transaction.commit();
        session.close();
        System.out.println(transaction.getStatus());
        if (transaction.getStatus().toString().equals("COMMITTED")){
            return true;
        }
        return false;
    }


    @Override
    public List<Student> getAll() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        List<Student> student = session.createQuery("from Student ").list();
        transaction.commit();
        session.close();
        return student;
    }

    @Override
    public String getCurrentId() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Object customer = session.createQuery("SELECT studentId FROM Student ORDER BY studentId DESC LIMIT 1").uniqueResult();
        String id ="";
        if (customer != null) {
            String customerId = customer.toString();

            // Split based on "STU20" to extract the numeric part
            String[] split = customerId.split("(?i)STU20");

            // Ensure there's a numeric part available after splitting
            if (split.length > 1) {
                int idNum = Integer.parseInt(split[1]); // Get the numeric part
                id = "STU20" + String.format("%03d", ++idNum); // Increment and format
            } else {
                id = "STU20001"; // Default if splitting failed
            }
        } else {
            id = "STU20001"; // Default if customer is null
        }

        transaction.commit();

        session.close();
        return id;
    }

    @Override
    public Student searchById(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<String> getIds() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public Student getStudentDetailByContact(String search) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Student student = (Student) session.createQuery("from Student where contact = :Contact")
                .setParameter("Contact", search)
                .uniqueResult();
        transaction.commit();
        session.close();
        return student;
    }
}
