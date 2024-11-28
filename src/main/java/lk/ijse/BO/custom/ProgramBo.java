package lk.ijse.BO.custom;

import lk.ijse.BO.SuperBo;
import lk.ijse.Entity.Program;
import lk.ijse.dto.ProgramDTO;

import java.sql.SQLException;
import java.util.List;

public interface ProgramBo extends SuperBo {
   

    String getCurrentId() throws SQLException, ClassNotFoundException;

    boolean saveProgram(ProgramDTO programDTO) throws SQLException, ClassNotFoundException;

    List<ProgramDTO> getAllCourses() throws SQLException, ClassNotFoundException;

    boolean deleteProgram(ProgramDTO programDTO) throws SQLException, ClassNotFoundException;

    boolean updateProgram(ProgramDTO programDTO) throws SQLException, ClassNotFoundException;

    List<String> getProgramNames();

    Program searchByName(String course);
}
