package lk.ijse.dao.custom.impl;

import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.custom.DashBoardDao;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashBoardDaoImpl implements DashBoardDao {
    @Override
    public Map<String, Long> getStudentCountByCourse() {
        Map<String, Long> paymentsByDay = new HashMap<>();

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            String hql = "SELECT p.programName, COUNT(sp.student_course_id) AS studentCount\n" +
                    "FROM Student_Program sp\n" +
                    "JOIN Program p ON sp.program.id = p.programId\n" +
                    "GROUP BY p.programName";
            List<Object[]> results = session.createQuery(hql).list();

            for (Object[] result : results) {
                String date = (String) result[0];
                Long totalAmount = (Long) result[1];
                paymentsByDay.put(date, totalAmount);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            session.close();
        }

        return paymentsByDay;
    }

    @Override
    public Map<String, Long> getRegistersByDay() {
        Map<String, Long> registersByDay = new HashMap<>();

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            String hql = "SELECT register_date, COUNT(student_course_id) AS studentCount " +
                    "FROM Student_Program " +
                    "GROUP BY register_date " +
                    "ORDER BY register_date";
            List<Object[]> results = session.createQuery(hql).list();

            for (Object[] result : results) {
                java.sql.Date sqlDate = (java.sql.Date) result[0];  // The date is a java.sql.Date
                String date = new SimpleDateFormat("yyyy-MM-dd").format(sqlDate);  // Format the date to a String
                Long studentCount = (Long) result[1];

                registersByDay.put(date, studentCount);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            session.close();
        }

        return registersByDay;
    }

    @Override
    public int getPendingStudentCount() {
        int studentCount = 0;
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // HQL query to count students with regLevel 'Level1' and 'Level2'
            String hql = "SELECT COUNT(studentId) FROM Student WHERE regLevel IN ('Level 1', 'Level 2')";
            Long result = (Long) session.createQuery(hql).uniqueResult();

            // Convert Long result to int
            studentCount = result != null ? result.intValue() : 0;

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            session.close();
        }

        return studentCount;

    }

    @Override
    public int getProgramCount() {
        int programCount = 0;
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // HQL query to count the number of programs
            String hql = "SELECT COUNT(programId) FROM Program";
            Long result = (Long) session.createQuery(hql).uniqueResult();

            // Convert Long result to int
            programCount = result != null ? result.intValue() : 0;

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            session.close();
        }

        return programCount;

    }

    @Override
    public int getRegisterStudentCount() {
        int registeredCount = 0;
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            // HQL query to count the number of registered students
            String hql = "SELECT COUNT(studentId) FROM Student WHERE regLevel = 'Registered'";
            Long result = (Long) session.createQuery(hql).uniqueResult();

            // Convert Long result to int
            registeredCount = result != null ? result.intValue() : 0;

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        } finally {
            session.close();
        }

        return registeredCount;

    }

}
