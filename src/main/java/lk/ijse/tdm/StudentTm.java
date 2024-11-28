package lk.ijse.tdm;

import lk.ijse.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class StudentTm {
    private String studentId;
    private String studentName;
    private int studentAge;
    private String email;
    private String contact;
    private String address;
    private String regLevel;
    private String courseName;
    private String user;
}
