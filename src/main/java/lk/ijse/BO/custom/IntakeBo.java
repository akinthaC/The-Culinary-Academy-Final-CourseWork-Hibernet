package lk.ijse.BO.custom;

import lk.ijse.BO.SuperBo;
import lk.ijse.Entity.Intake;
import lk.ijse.dto.IntakeDTO;

import java.sql.SQLException;
import java.util.List;

public interface IntakeBo extends SuperBo {
    String getCurrentId() throws SQLException, ClassNotFoundException;

    List<IntakeDTO> getAllActiveIntakes() throws SQLException, ClassNotFoundException;

    boolean saveIntake(IntakeDTO intakeDTO) throws SQLException, ClassNotFoundException;

    boolean updateIntake(IntakeDTO intakeDTO) throws SQLException, ClassNotFoundException;

    boolean DeleteIntake(IntakeDTO intakeDTO) throws SQLException, ClassNotFoundException;

    List<IntakeDTO> getIntakeByCourse(String programId);

    Intake getIntakeByName(String intakeName);

    List<IntakeDTO> getAll() throws SQLException, ClassNotFoundException;
}
