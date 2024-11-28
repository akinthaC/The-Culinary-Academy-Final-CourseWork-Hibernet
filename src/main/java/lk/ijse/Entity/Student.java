package lk.ijse.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Student {
    @Id
    private String studentId;
    private String studentName;
    private int studentAge;
    private String email;
    private String contact;
    private String address;
    private String regLevel;

    private String courseName;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Student_Program> studentPrograms;

    @ManyToOne
    private User user;

    public Student() {}

    public Student(String studentId, String studentName, int studentAge, String email, String contact, String address, String regLevel, String courseName, User user) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentAge = studentAge;
        this.email = email;
        this.contact = contact;
        this.address = address;
        this.regLevel = regLevel;
        this.courseName = courseName;
        this.user = user;
    }

    public Student(String studentId, String studentName, int studentAge, String email, String contact, String address, String regLevel, String courseName, List<Student_Program> studentPrograms, User user) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentAge = studentAge;
        this.email = email;
        this.contact = contact;
        this.address = address;
        this.regLevel = regLevel;
        this.courseName = courseName;
        this.studentPrograms = studentPrograms;
        this.user = user;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getStudentAge() {
        return studentAge;
    }

    public void setStudentAge(int studentAge) {
        this.studentAge = studentAge;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegLevel() {
        return regLevel;
    }

    public void setRegLevel(String regLevel) {
        this.regLevel = regLevel;
    }

    public List<Student_Program> getStudentPrograms() {
        return studentPrograms;
    }

    public void setStudentPrograms(List<Student_Program> studentPrograms) {
        this.studentPrograms = studentPrograms;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
