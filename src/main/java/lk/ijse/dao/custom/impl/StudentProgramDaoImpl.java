package lk.ijse.dao.custom.impl;

import lk.ijse.Entity.Student_Program;
import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.custom.StudentProgramDao;
import lk.ijse.dto.PaymentDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;

public class StudentProgramDaoImpl implements StudentProgramDao {
    @Override
    public boolean save(Student_Program DTO) throws SQLException, ClassNotFoundException {
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
    public boolean update(Student_Program DTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(Student_Program DTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List<Student_Program> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String getCurrentId() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Object intake = session.createQuery("SELECT student_course_id FROM Student_Program ORDER BY student_course_id DESC LIMIT 1").uniqueResult();
        String id ="";
        if (intake != null) {
            String customerId = intake.toString();

            // Split based on "STU20" to extract the numeric part
            String[] split = customerId.split("(?i)REG1");

            // Ensure there's a numeric part available after splitting
            if (split.length > 1) {
                int idNum = Integer.parseInt(split[1]); // Get the numeric part
                id = "REG1" + String.format("%03d", ++idNum); // Increment and format
            } else {
                id = "INT1001"; // Default if splitting failed
            }
        } else {
            id = "REG1001"; // Default if customer is null
        }

        transaction.commit();

        session.close();
        return id;
    }

    @Override
    public Student_Program searchById(String id) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Student_Program studentProgram = null;
        try {
            studentProgram = (Student_Program) session.createQuery("FROM Student_Program WHERE student_course_id = :id")
                    .setParameter("id", id)
                    .uniqueResult();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }

        return studentProgram;
    }

    @Override
    public List<String> getIds() throws SQLException, ClassNotFoundException {
        return null;
    }
}
