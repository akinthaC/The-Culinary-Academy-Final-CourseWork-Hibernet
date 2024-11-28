package lk.ijse.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Entity
public class Student_Program {
    @Id
    private String student_course_id;

    private Date register_date;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "programId", nullable = false)
    private Program program;

    @ManyToOne
    @JoinColumn(name = "intakeId", nullable = false)
    private Intake intake;




}
