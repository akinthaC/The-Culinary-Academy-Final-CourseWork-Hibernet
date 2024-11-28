package lk.ijse.dao.custom;

import lk.ijse.Entity.Student;
import lk.ijse.dao.CrudDao;
import lk.ijse.dto.StudentDTO;

import java.util.List;

public interface StudentDao extends CrudDao<Student> {
    Student getStudentDetailByContact(String search);
}
