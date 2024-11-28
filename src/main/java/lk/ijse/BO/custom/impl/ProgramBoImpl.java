package lk.ijse.BO.custom.impl;

import lk.ijse.BO.custom.ProgramBo;
import lk.ijse.Entity.Program;
import lk.ijse.Entity.Student;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.ProgramDao;
import lk.ijse.dao.custom.StudentDao;
import lk.ijse.dto.ProgramDTO;
import lk.ijse.dto.StudentDTO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProgramBoImpl implements ProgramBo {

    ProgramDao programDao = (ProgramDao) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.PROGRAM);;

    @Override
    public String getCurrentId() throws SQLException, ClassNotFoundException {
        return programDao.getCurrentId();

    }

    @Override
    public boolean saveProgram(ProgramDTO programDTO) throws SQLException, ClassNotFoundException {
        return programDao.save(new Program(programDTO.getProgramId(),programDTO.getProgramName(), programDTO.getDuration(), programDTO.getFee()));
    }

    @Override
    public List<ProgramDTO> getAllCourses() throws SQLException, ClassNotFoundException {
        List<Program> programList= programDao.getAll();

        List<ProgramDTO> programDTOList=new ArrayList<>();

        for (Program program : programList) {
            programDTOList.add(new ProgramDTO(
                    program.getProgramId(),
                    program.getProgramName(),
                    program.getDuration(),
                    program.getFee()
            ));
        }
        return programDTOList;
    }

    @Override
    public boolean deleteProgram(ProgramDTO programDTO) throws SQLException, ClassNotFoundException {
        return programDao.delete(new Program(programDTO.getProgramId(),programDTO.getProgramName(), programDTO.getDuration(), programDTO.getFee()));
    }

    @Override
    public boolean updateProgram(ProgramDTO programDTO) throws SQLException, ClassNotFoundException {
        return programDao.update(new Program(programDTO.getProgramId(),programDTO.getProgramName(), programDTO.getDuration(), programDTO.getFee()));
    }

    @Override
    public List<String> getProgramNames() {
        return programDao.getCoursesNames();
    }

    @Override
    public Program searchByName(String course) {
        return programDao.searchByName(course);
    }
}
