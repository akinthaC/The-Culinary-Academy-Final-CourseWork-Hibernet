package lk.ijse.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lk.ijse.Entity.Program;
import lk.ijse.Entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Student_programDTO {
    private Long student_course_id;
    private Date register_date;
    private Student student;
    private Program program;
}
