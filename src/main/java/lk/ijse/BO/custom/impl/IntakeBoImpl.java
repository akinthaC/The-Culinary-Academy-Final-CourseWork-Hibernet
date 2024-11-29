package lk.ijse.BO.custom.impl;

import lk.ijse.BO.custom.IntakeBo;
import lk.ijse.Entity.Intake;
import lk.ijse.Entity.Student;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.IntakeDao;
import lk.ijse.dao.custom.StudentDao;
import lk.ijse.dto.IntakeDTO;
import lk.ijse.dto.StudentDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IntakeBoImpl implements IntakeBo {
    IntakeDao intakeDao = (IntakeDao) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.INTAKE);;

    @Override
    public String getCurrentId() throws SQLException, ClassNotFoundException {
        return intakeDao.getCurrentId();
    }

    @Override
    public List<IntakeDTO> getAllActiveIntakes() throws SQLException, ClassNotFoundException {
        List<Intake> intakeList= intakeDao.getAllActiveIntakes();

        List<IntakeDTO> intakeDTOS=new ArrayList<>();

        for (Intake intake : intakeList) {
            intakeDTOS.add(new IntakeDTO(
                    intake.getIntakeId(),
                    intake.getIntakeName(),
                    intake.getStartDate(),
                    intake.getEndDate(),
                    intake.getCapacity(),
                    intake.getStatus(),
                    intake.getCourse()
            ));
        }
        return intakeDTOS;
    }

    @Override
    public boolean saveIntake(IntakeDTO intakeDTO) throws SQLException, ClassNotFoundException {
        return intakeDao.save(new Intake(intakeDTO.getIntakeId(),intakeDTO.getIntakeName(),intakeDTO.getStartDate(),intakeDTO.getEndDate(),intakeDTO.getCapacity(),intakeDTO.getStatus(),intakeDTO.getCourse()));
    }

    @Override
    public boolean updateIntake(IntakeDTO intakeDTO) throws SQLException, ClassNotFoundException {
        return intakeDao.update(new Intake(intakeDTO.getIntakeId(),intakeDTO.getIntakeName(),intakeDTO.getStartDate(),intakeDTO.getEndDate(),intakeDTO.getCapacity(),intakeDTO.getStatus(),intakeDTO.getCourse()));

    }

    @Override
    public boolean DeleteIntake(IntakeDTO intakeDTO) throws SQLException, ClassNotFoundException {
        return intakeDao.delete(new Intake(intakeDTO.getIntakeId(),intakeDTO.getIntakeName(),intakeDTO.getStartDate(),intakeDTO.getEndDate(),intakeDTO.getCapacity(),intakeDTO.getStatus(),intakeDTO.getCourse()));

    }

    @Override
    public List<IntakeDTO> getIntakeByCourse(String programId) {
        List<Intake> intakeList= intakeDao.getIntakeByCourse(programId);

        List<IntakeDTO> intakeDTOS=new ArrayList<>();

        for (Intake intake : intakeList) {
            intakeDTOS.add(new IntakeDTO(
                    intake.getIntakeId(),
                    intake.getIntakeName(),
                    intake.getStartDate(),
                    intake.getEndDate(),
                    intake.getCapacity(),
                    intake.getStatus(),
                    intake.getCourse()
            ));
        }
        return intakeDTOS;
    }

    @Override
    public Intake getIntakeByName(String intakeName) {
        return intakeDao.getIntakeByName(intakeName);
    }

    @Override
    public List<IntakeDTO> getAll() throws SQLException, ClassNotFoundException {
        List<Intake> intakeList= intakeDao.getAll();

        List<IntakeDTO> intakeDTOS=new ArrayList<>();

        for (Intake intake : intakeList) {
            intakeDTOS.add(new IntakeDTO(
                    intake.getIntakeId(),
                    intake.getIntakeName(),
                    intake.getStartDate(),
                    intake.getEndDate(),
                    intake.getCapacity(),
                    intake.getStatus(),
                    intake.getCourse()
            ));
        }
        return intakeDTOS;
    }
}
