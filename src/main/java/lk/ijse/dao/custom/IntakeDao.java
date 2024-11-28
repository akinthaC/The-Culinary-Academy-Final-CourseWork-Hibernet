package lk.ijse.dao.custom;

import lk.ijse.Entity.Intake;
import lk.ijse.dao.CrudDao;
import lk.ijse.dto.IntakeDTO;

import java.util.List;

public interface IntakeDao extends CrudDao<Intake> {
    List<Intake> getAllActiveIntakes();

    List<Intake> getIntakeByCourse(String programId);

    Intake getIntakeByName(String intakeName);

    boolean updateCapacity(Intake intake);
}
