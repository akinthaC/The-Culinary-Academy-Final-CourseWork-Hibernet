package lk.ijse.BO.custom.impl;

import lk.ijse.BO.custom.StudentBo;
import lk.ijse.Entity.Program;
import lk.ijse.Entity.Student;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.StudentDao;
import lk.ijse.dao.custom.UserDao;
import lk.ijse.dto.StudentDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentBoImpl implements StudentBo {
    StudentDao studentDao = (StudentDao) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.STUDENT);;

    @Override
    public String getCurrentId() throws SQLException, ClassNotFoundException {
        return studentDao.getCurrentId();
    }

    @Override
    public boolean saveStudent(StudentDTO studentDTO) throws SQLException, ClassNotFoundException {
        return studentDao.save(new Student(studentDTO.getStudentId(),studentDTO.getStudentName(),studentDTO.getStudentAge(),studentDTO.getEmail(),studentDTO.getContact(),studentDTO.getAddress(),studentDTO.getRegLevel(),studentDTO.getCourseName(),studentDTO.getUser()));
    }

    @Override
    public List<StudentDTO> getAllStudents() throws SQLException, ClassNotFoundException {
        List<Student> studentDTOArrayList= studentDao.getAll();

        List<StudentDTO> studentDTOS=new ArrayList<>();

        for (Student student : studentDTOArrayList) {
            studentDTOS.add(new StudentDTO(
                    student.getStudentId(),
                    student.getStudentName(),
                    student.getStudentAge(),
                    student.getEmail(),
                    student.getContact(),
                    student.getAddress(),
                    student.getRegLevel(),
                    student.getCourseName(),
                    student.getUser()
            ));
        }
        return studentDTOS;
    }

    @Override
    public boolean upadteStudent(StudentDTO studentDTO) throws SQLException, ClassNotFoundException {
        return studentDao.update(new Student(studentDTO.getStudentId(),studentDTO.getStudentName(),studentDTO.getStudentAge(),studentDTO.getEmail(),studentDTO.getContact(),studentDTO.getAddress(),studentDTO.getRegLevel(),studentDTO.getCourseName(),studentDTO.getUser()));

    }

    @Override
    public boolean deleteStudent(StudentDTO studentDTO) throws SQLException, ClassNotFoundException {
        return studentDao.delete(new Student(studentDTO.getStudentId(),studentDTO.getStudentName(),studentDTO.getStudentAge(),studentDTO.getEmail(),studentDTO.getContact(),studentDTO.getAddress(),studentDTO.getRegLevel(),studentDTO.getCourseName(),studentDTO.getUser()));
    }

    @Override
    public Student searchByContact(String search) {
        return studentDao.getStudentDetailByContact(search);


    }
}

