package lk.ijse.dao.custom;

import lk.ijse.Entity.Program;
import lk.ijse.dao.CrudDao;

import java.util.List;

public interface ProgramDao extends CrudDao<Program> {
    List<String> getCoursesNames();

    Program searchByName(String course);
}
