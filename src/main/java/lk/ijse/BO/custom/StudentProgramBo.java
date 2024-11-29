package lk.ijse.BO.custom;

import lk.ijse.BO.SuperBo;
import lk.ijse.Entity.Student_Program;
import lk.ijse.dto.RegisterDTO;
import lk.ijse.tdm.RegTm;

import java.sql.SQLException;
import java.util.List;

public interface StudentProgramBo extends SuperBo {
    String getCurrentId() throws SQLException, ClassNotFoundException;


    boolean registerStudent(RegisterDTO registerDTO);

    Student_Program SearchById(String staticRegNo) throws SQLException, ClassNotFoundException;

    List<RegTm> getAllRegData(String stuSearch);
}
