package lk.ijse.tdm;

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
public class PaymentTm {
    private String paymentID;
    private String paymentType;
    private double payAmount;
    private double AmountToBePay;
    private double TotalAmount;
    private String paymentPlan;
    private String paymentStatus;
    private String student;
}
