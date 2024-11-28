package lk.ijse.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Entity
public class Payment {
    @Id
    private String paymentID;
    private String paymentDate;
    private String paymentType;
    private double payAmount;
    private double AmountToBePay;
    private double TotalAmount;
    private String paymentPlan;
    private String paymentStatus;
    @ManyToOne
    @JoinColumn(name = "ResgisterId")
    private Student_Program studentProgram;

    @ManyToOne
    @JoinColumn(name = "StdentId")
    private Student student;

}
