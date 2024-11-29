package lk.ijse.dao.custom.impl;

import lk.ijse.Entity.Program;
import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.custom.ProgramDao;
import lk.ijse.dto.UserDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;

public class ProgramDaoImpl implements ProgramDao{
    @Override
    public boolean save(Program DTO) throws SQLException, ClassNotFoundException {
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
    public boolean update(Program DTO) throws SQLException, ClassNotFoundException {
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
    public boolean delete(Program DTO) throws SQLException, ClassNotFoundException {
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
    public List<Program> getAll() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        List<Program> programs = session.createQuery("from Program ").list();
        transaction.commit();
        session.close();
        return programs;
    }

    @Override
    public String getCurrentId() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Object program = session.createQuery("SELECT programId FROM Program ORDER BY programId DESC LIMIT 1").uniqueResult();
        String id ="";
        if (program != null) {
            String customerId = program.toString();

            // Split based on "STU20" to extract the numeric part
            String[] split = customerId.split("(?i)CA1");

            // Ensure there's a numeric part available after splitting
            if (split.length > 1) {
                int idNum = Integer.parseInt(split[1]); // Get the numeric part
                id = "CA1" + String.format("%03d", ++idNum); // Increment and format
            } else {
                id = "CA1001"; // Default if splitting failed
            }
        } else {
            id = "CA1001"; // Default if customer is null
        }

        transaction.commit();

        session.close();
        return id;
    }

    @Override
    public Program searchById(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<String> getIds() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<String> getCoursesNames() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        List<String> CourseNames = null;
        try {
            CourseNames = session.createQuery("SELECT p.programName  FROM Program p", String.class).getResultList();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return CourseNames;
    }

    @Override
    public Program searchByName(String course) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Program program = null;
        try {
            program = (Program) session.createQuery("FROM Program WHERE programName = :name")
                    .setParameter("name", course)
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

        return program;
    }
}
