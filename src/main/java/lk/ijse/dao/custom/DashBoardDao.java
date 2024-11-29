package lk.ijse.dao.custom;

import java.util.Map;

public interface DashBoardDao {

    Map<String, Long> getStudentCountByCourse();

    Map<String, Long> getRegistersByDay();

    int getPendingStudentCount();

    int getProgramCount();

    int getRegisterStudentCount();
}
