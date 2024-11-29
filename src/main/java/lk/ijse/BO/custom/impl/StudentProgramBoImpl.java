package lk.ijse.BO.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.BO.custom.StudentProgramBo;
import lk.ijse.Entity.Student_Program;
import lk.ijse.config.FactoryConfiguration;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.IntakeDao;
import lk.ijse.dao.custom.StudentProgramDao;
import lk.ijse.dto.RegisterDTO;
import lk.ijse.tdm.RegTm;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.SQLException;
import java.util.List;

public class StudentProgramBoImpl implements StudentProgramBo {
    StudentProgramDao studentProgramDao = (StudentProgramDao) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.STUDENT_PROGRAM);
    IntakeDao intakeDao = (IntakeDao) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.INTAKE);;


    @Override
    public String getCurrentId() throws SQLException, ClassNotFoundException {
        return studentProgramDao.getCurrentId();
    }

    @Override
    public boolean registerStudent(RegisterDTO registerDTO) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;

        try {
            // Begin transaction (Hibernate internally sets auto-commit to false here)
            transaction = session.beginTransaction();
            if (registerDTO.getIntake().getCapacity() ==0){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Capacity is Zero");
                alert.setContentText("The intake capacity is 0. Please try another batch.");
                alert.showAndWait();
                return false;
            }

            Student_Program studentProgram =registerDTO.getStudentProgram();
            session.save(studentProgram);
            System.out.println(transaction.getStatus());
            if (transaction.getStatus().toString().equals("ACTIVE")){

                boolean isUpdate = intakeDao.updateCapacity(registerDTO.getIntake());
                System.out.println("update: " + isUpdate);
                if (isUpdate) {
                    transaction.commit();
                    session.close();
                    System.out.println("update: " + isUpdate);
                    return true;
                }
                transaction.rollback();
                return false;
            }
            transaction.rollback();
                return false;

        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                // Rollback the transaction (Hibernate resets auto-commit after rollback)
                transaction.rollback();
                return false;
            }
            e.printStackTrace();
        } finally {
            session.close(); // Close the session
            return true;
        }
    }

    @Override
    public Student_Program SearchById(String staticRegNo) throws SQLException, ClassNotFoundException {
        return studentProgramDao.searchById(staticRegNo);
    }

    @Override
    public List<RegTm> getAllRegData(String stuSearch) {
        return studentProgramDao.getAllRegData(stuSearch);
    }
}


