package lk.ijse.tdm;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lk.ijse.Entity.Program;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class IntakeTm {
    private String intakeId;
    private String intakeName;
    private Date startDate;
    private Date endDate;
    private int capacity;
    private String status;
    private String course;
}
