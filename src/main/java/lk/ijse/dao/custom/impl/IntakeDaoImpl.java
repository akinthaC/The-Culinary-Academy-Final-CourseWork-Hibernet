package lk.ijse.dao.custom.impl;

import lk.ijse.Entity.Intake;
import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.custom.IntakeDao;
import lk.ijse.dto.UserDTO;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;

public class IntakeDaoImpl implements IntakeDao {
    @Override
    public boolean save(Intake DTO) throws SQLException, ClassNotFoundException {
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
    public boolean update(Intake DTO) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(DTO);

        transaction.commit();
        session.close();
        System.out.println(transaction.getStatus());
        if (transaction.getStatus().toString().equals("COMMITTED")){
            System.out.println("return true");
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Intake DTO) throws SQLException, ClassNotFoundException {
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
    public List<Intake> getAll() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        List<Intake> intakes = session.createQuery("from Intake ").list();
        transaction.commit();
        session.close();
        return intakes;
    }

    @Override
    public String getCurrentId() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Object intake = session.createQuery("SELECT intakeId FROM Intake ORDER BY intakeId DESC LIMIT 1").uniqueResult();
        String id ="";
        if (intake != null) {
            String customerId = intake.toString();

            // Split based on "STU20" to extract the numeric part
            String[] split = customerId.split("(?i)INT1");

            // Ensure there's a numeric part available after splitting
            if (split.length > 1) {
                int idNum = Integer.parseInt(split[1]); // Get the numeric part
                id = "INT1" + String.format("%03d", ++idNum); // Increment and format
            } else {
                id = "INT1001"; // Default if splitting failed
            }
        } else {
            id = "INT1001"; // Default if customer is null
        }

        transaction.commit();

        session.close();
        return id;
    }

    @Override
    public Intake searchById(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<String> getIds() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public List<Intake> getAllActiveIntakes() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        List<Intake> intakes =session.createQuery("from Intake where status = :status", Intake.class)
                .setParameter("status", "Active")
                .getResultList();
        transaction.commit();
        session.close();
        return intakes;
    }

    @Override
    public List<Intake> getIntakeByCourse(String programId) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        List<Intake> intakes =session.createQuery("from Intake where course.programId = :id", Intake.class)
                .setParameter("id", programId)
                .getResultList();
        transaction.commit();
        session.close();

        return intakes;
    }

    @Override
    public Intake getIntakeByName(String intakeName) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Intake intake = (Intake) session.createQuery("from Intake where intakeName = :name")
                .setParameter("name", intakeName)
                .uniqueResult();
        transaction.commit();
        session.close();
        return intake;
    }

    @Override
    public boolean updateCapacity(Intake intake) {
        Transaction transaction = null;
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            transaction = session.beginTransaction();

            // HQL update query
            int updated = session.createQuery("UPDATE Intake SET capacity = capacity - 1 WHERE intakeId = :intakeId")
                    .setParameter("intakeId", intake.getIntakeId())
                    .executeUpdate();

            if (updated > 0) {
                transaction.commit();
                System.out.println("true Returneds");// Commit if the update was successful
                return true;

            } else {
                transaction.rollback();
                System.out.println("fales Returneds");// Rollback if no rows were updated
                return false;
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback(); // Rollback in case of an exception
            }
            e.printStackTrace();
            return false; // Return false in case of any exception
        }
    }
}
