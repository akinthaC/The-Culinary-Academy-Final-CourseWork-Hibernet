package lk.ijse.dto;

import lk.ijse.Entity.Intake;
import lk.ijse.Entity.Student_Program;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class RegisterDTO {
    private Student_Program studentProgram;
    private Intake intake;
}
