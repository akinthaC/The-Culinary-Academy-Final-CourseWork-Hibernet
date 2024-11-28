package lk.ijse.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Program {
    @Id
    private String programId;
    private String programName;
    private String duration;
    private double fee;

    @OneToMany(mappedBy = "program", cascade = CascadeType.ALL)
    private List<Student_Program> studentCourses;

    public Program() {}

    public Program(String programId, String programName, String duration, double fee) {
        this.programId = programId;
        this.programName = programName;
        this.duration = duration;
        this.fee = fee;
    }

    public Program(String programId, String programName, String duration, double fee, List<Student_Program> studentCourses) {
        this.programId = programId;
        this.programName = programName;
        this.duration = duration;
        this.fee = fee;
        this.studentCourses = studentCourses;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public List<Student_Program> getStudentCourses() {
        return studentCourses;
    }

    public void setStudentCourses(List<Student_Program> studentCourses) {
        this.studentCourses = studentCourses;
    }
}
