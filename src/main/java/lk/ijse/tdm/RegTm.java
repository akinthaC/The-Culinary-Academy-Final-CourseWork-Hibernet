package lk.ijse.tdm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class RegTm {
    private String regNo;
    private String regDate;
    private String intakeName;
    private String ProgramName;
}
