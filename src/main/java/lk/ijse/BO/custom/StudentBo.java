package lk.ijse.BO.custom;

import lk.ijse.BO.SuperBo;
import lk.ijse.Entity.Program;
import lk.ijse.Entity.Student;
import lk.ijse.dto.StudentDTO;

import java.sql.SQLException;
import java.util.List;

public interface StudentBo extends SuperBo {
    public String getCurrentId() throws SQLException, ClassNotFoundException;

    boolean saveStudent(StudentDTO studentDTO) throws SQLException, ClassNotFoundException;

    List<StudentDTO> getAllStudents() throws SQLException, ClassNotFoundException;

    boolean upadteStudent(StudentDTO studentDTO) throws SQLException, ClassNotFoundException;

    boolean deleteStudent(StudentDTO studentDTO) throws SQLException, ClassNotFoundException;

    Student searchByContact(String search);
}
