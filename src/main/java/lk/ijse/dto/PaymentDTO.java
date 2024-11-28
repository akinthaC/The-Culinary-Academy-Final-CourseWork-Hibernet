package lk.ijse.dto;

import lk.ijse.Entity.Student;
import lk.ijse.Entity.Student_Program;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class PaymentDTO {
    private String paymentID;
    private String paymentDate;
    private String paymentType;
    private double payAmount;
    private double AmountToBePay;
    private double TotalAmount;
    private String paymentPlan;
    private String paymentStatus;
    private Student_Program studentProgram;
    private Student student;
}
