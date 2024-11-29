
package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.BO.BOFactory;
import lk.ijse.BO.custom.*;
import lk.ijse.Entity.*;
import lk.ijse.dto.IntakeDTO;
import lk.ijse.dto.RegisterDTO;
import lk.ijse.dto.StudentDTO;
import lk.ijse.util.Regex;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class StudentProgramPageController {

    public AnchorPane mainPane;
    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnRegister;

    @FXML
    private JFXComboBox<String> cmbCourse;

    @FXML
    private JFXComboBox<String> cmbIntake;

    @FXML
    private Label lblCourseEndDate;

    @FXML
    private Label lblCourseEndDate1;

    @FXML
    private Label lblCourseFee;

    @FXML
    private Label lblCourseStartDate;

    @FXML
    private Label lblMessage;

    @FXML
    private TextField txtAge;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtRegId;

    @FXML
    private TextField txtStuId;

    @FXML
    private TextField txtStuSearch;

    @FXML
    private TextField txtStudentAddress;

    @FXML
    private TextField txtStudentEmail;

    @FXML
    private TextField txtStudentName;

    static String staticRegNo;
    static String staticFee;
    static Student staticStudent;

    private Stage primaryStage;
    UserBo userBo = (UserBo) BOFactory.getBoFactory().GetBo(BOFactory.BOType.USER);
    ProgramBo programBo = (ProgramBo) BOFactory.getBoFactory().GetBo(BOFactory.BOType.PROGRAM) ;
    StudentBo studentBo = (StudentBo) BOFactory.getBoFactory().GetBo(BOFactory.BOType.STUDENT);
    IntakeBo intakeBo = (IntakeBo) BOFactory.getBoFactory().GetBo(BOFactory.BOType.INTAKE) ;
    StudentProgramBo studentProgramBo = (StudentProgramBo) BOFactory.getBoFactory().GetBo(BOFactory.BOType.STUDENT_PROGRAM) ;
    public void initialize() {
        generateRegisterId();
        getCoursesForCombo();



    }

    private void getIntakesForCombo(String course ) {

        Program program = programBo.searchByName(course);
        List<IntakeDTO> dtos = intakeBo.getIntakeByCourse(program.getProgramId());
        ObservableList<String> list = FXCollections.observableArrayList();

        for (IntakeDTO dto : dtos) {
            list.add(dto.getIntakeName());
        }
        cmbIntake.setItems(list);
    }

    private void generateRegisterId() {
        try {
            String currentId = studentProgramBo.getCurrentId();


            txtRegId.setText(currentId);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void getCoursesForCombo() {
        List<String> courseNames=programBo.getProgramNames();
        ObservableList<String> names = FXCollections.observableArrayList();
        for (String coursesName : courseNames) {
            names.add(coursesName);
        }
        cmbCourse.setItems(names);

    }
    private void generateStudentId() {
        try {
            String currentId = studentBo.getCurrentId();


            txtStuId.setText(currentId);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnRegisterOnAction(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
        if (!isValied()) {
            new Alert(Alert.AlertType.ERROR, "Please check all fields.").show();
            return;
        }

        if (txtStuId.getText()==null || txtContact.getText()==null || txtAge.getText() == null || txtRegId.getText() ==null || txtStudentAddress.getText()==null ) {
            new Alert(Alert.AlertType.ERROR, "please Fill all field !").show();
            return;
        }
        String userName = LoginPageController.staticUserName;
        System.out.println(userName);
        User user = userBo.searchByName(userName);
        System.out.println(userName);
        String course = cmbCourse.getValue();
        String regId = txtRegId.getText();
        staticRegNo = regId;
        Date date = java.sql.Date.valueOf(LocalDate.now());
        Intake intake = intakeBo.getIntakeByName(cmbIntake.getValue());
        Program program = programBo.searchByName(course);
        Student studentId = studentBo.searchByContact(txtContact.getText());
        staticStudent=studentId;
        System.out.println(studentId);

        ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

        Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to Registered?", yes, no).showAndWait();

        if(type.orElse(no) == yes) {

            if (studentId == null) {
                generateStudentId();
                String stuId = txtStuId.getText();
                String studentName = txtStudentName.getText();
                String studentEmail = txtStudentEmail.getText();
                String studentAddress = txtStudentAddress.getText();
                String contact = txtContact.getText();
                String courseName = cmbCourse.getSelectionModel().getSelectedItem();
                int age = Integer.parseInt(txtAge.getText());
                String level = "Registered";

                StudentDTO student = new StudentDTO(stuId, studentName, age, studentEmail, contact, studentAddress, level, courseName, user);
                studentBo.saveStudent(student);

            } else {
                Student student = studentBo.searchByContact(txtContact.getText());
                studentBo.upadteStudent(new StudentDTO(student.getStudentId(), student.getStudentName(), student.getStudentAge(), student.getEmail(), student.getContact(), student.getAddress(), "Registered", student.getCourseName(), student.getUser()));
            }
            System.out.println("hiiii"+txtContact.getText());
            Student student = studentBo.searchByContact(txtContact.getText());
            System.out.println(student);
            Student_Program studentRegister = new Student_Program(regId, date, student, program, intake);
            RegisterDTO registerDTO = new RegisterDTO(studentRegister, intake);
            System.out.println("stttt" + studentRegister.getStudent());
            boolean isRegistered = studentProgramBo.registerStudent(registerDTO);
            System.out.println(isRegistered);
            if (isRegistered) {
                addpayment();
                System.out.println("All are registered");

            }
            System.out.println(intake.getCapacity());


        }
    }
    public boolean isValied(){
        if (!Regex.setTextColor(lk.ijse.util.TextField.ADDRESS,txtStudentAddress)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.AMOUNT,txtAge)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.CONTACT,txtContact)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.EMAIL,txtStudentEmail)) return false;
        return true;
    }

    public void addpayment() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PaymentInfoForm.fxml"));
        Parent rootNode = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(rootNode));
        stage.centerOnScreen();
        stage.setTitle("AddPayment Form");

        stage.show();
        close();

    }

    private void close() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/student-program-page.fxml"));
        AnchorPane contentPane = loader.load();

        // Add the loaded content to the main pane
        mainPane.getChildren().clear();
        mainPane.getChildren().add(contentPane);
    }

    @FXML
    void cmbCourseOnAction(ActionEvent event) {
        String course = cmbCourse.getValue();
        if (course != null && !course.isEmpty()) {
            System.out.println(cmbCourse.getValue());
            getIntakesForCombo(course);
        }


    }

    @FXML
    void cmbIntakeOnAction(ActionEvent event) {
        String course = cmbCourse.getValue();
        Program program = programBo.searchByName(course);
        String intakeName = cmbIntake.getValue();
        Intake intake=intakeBo.getIntakeByName(intakeName);
        if (intake != null) {
            lblCourseEndDate.setText(String.valueOf(intake.getEndDate()));
            lblCourseStartDate.setText(String.valueOf(intake.getStartDate()));
            lblCourseFee.setText(String.valueOf(program.getFee()));
            staticFee = String.valueOf(program.getFee());
        }


    }

    @FXML
    void txtAgeOnAction(ActionEvent event) {

    }

    @FXML
    void txtContactOnAction(ActionEvent event) {

    }

    @FXML
    void txtIAddressOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.ADDRESS,txtStudentAddress);


    }

    @FXML
    void txtIAgeOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.AMOUNT,txtAge);


    }

    @FXML
    void txtIContactOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.CONTACT,txtContact);


    }

    @FXML
    void txtIEmailOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.EMAIL,txtStudentEmail);


    }

    @FXML
    void txtINameOnKeyPressed(KeyEvent event) {

    }

    @FXML
    void txtINameOnKeyReleased(KeyEvent event) {

    }
    @FXML
    void txtRegIdOnAction(ActionEvent event) {

    }

    @FXML
    void txtStuIdOnAction(ActionEvent event) {

    }

    @FXML
    void txtStuSearchOnAction(ActionEvent event) {
        String search = txtStuSearch.getText();
        Student studentDTOS=studentBo.searchByContact(search);
        if (studentDTOS!=null) {
            lblMessage.setText("find Successfully");
            lblMessage.setStyle("-fx-text-fill: blue;");
            Notifications notification = Notifications.create()
                    .title("Information")
                    .text("find Successfully")
                    .position(Pos.TOP_LEFT) // Position at the top left of the screen
                    .owner(primaryStage);
            notification.showInformation();

            txtStuId.setText(studentDTOS.getStudentId());
            txtStuSearch.setText("");
            txtStudentAddress.setText(studentDTOS.getAddress());
            txtStudentEmail.setText(studentDTOS.getEmail());
            txtStudentName.setText(studentDTOS.getStudentName());
            txtContact.setText(studentDTOS.getContact());
            txtAge.setText(String.valueOf(studentDTOS.getStudentAge()));
            cmbCourse.setValue(studentDTOS.getCourseName());

        }else {
            Notifications notification = Notifications.create()
                    .title("Error")
                    .text("Can't find student Please enter Student information")
                    .position(Pos.TOP_LEFT) // Position at the top left of the screen
                    .owner(primaryStage);
            notification.showInformation();
            lblMessage.setText("Can't find student Please enter Student information");
            lblMessage.setStyle("-fx-text-fill: red;");
            generateStudentId();
            clearFields();
        }


    }

    private void clearFields() {
        txtAge.setText("");
        txtContact.setText("");
        txtStuSearch.setText("");
        txtStudentAddress.setText("");
        txtStudentEmail.setText("");
        txtStudentName.setText("");
        txtContact.setText("");
        cmbCourse.setValue(null);
        cmbIntake.setValue(null);
        lblMessage.setText("");
        lblCourseEndDate.setText("");
        if (lblCourseEndDate1.getText()==null){
            lblCourseEndDate1.setText("");
            lblCourseFee.setText("");
        }


    }

    @FXML
    void txtStudentAddressOnAction(ActionEvent event) {

    }

    @FXML
    void txtStudentEmailOnAction(ActionEvent event) {

    }

    @FXML
    void txtStudentNameOnAction(ActionEvent event) {

    }

}
