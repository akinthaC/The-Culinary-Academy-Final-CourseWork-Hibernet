package lk.ijse.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Entity
public class Intake {
    @Id
    private String intakeId;
    private String intakeName;
    private Date startDate;
    private Date endDate;
    private int capacity;
    private String status;

    @ManyToOne
    @JoinColumn(name = "courseId")
    private Program course;



}

