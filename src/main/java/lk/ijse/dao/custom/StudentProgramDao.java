package lk.ijse.dao.custom;

import lk.ijse.Entity.Student_Program;
import lk.ijse.dao.CrudDao;
import lk.ijse.tdm.RegTm;

import java.util.List;

public interface StudentProgramDao extends CrudDao<Student_Program> {
    List<RegTm> getAllRegData(String stuSearch);
}
