package lk.ijse.BO.custom;

import lk.ijse.BO.SuperBo;
import lk.ijse.Entity.Student_Program;
import lk.ijse.dto.RegisterDTO;

import java.sql.SQLException;

public interface StudentProgramBo extends SuperBo {
    String getCurrentId() throws SQLException, ClassNotFoundException;


    boolean registerStudent(RegisterDTO registerDTO);

    Student_Program SearchById(String staticRegNo) throws SQLException, ClassNotFoundException;
}
