package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.BO.BOFactory;
import lk.ijse.BO.custom.ProgramBo;
import lk.ijse.BO.custom.StudentBo;
import lk.ijse.BO.custom.UserBo;
import lk.ijse.Entity.User;
import lk.ijse.dto.StudentDTO;
import lk.ijse.tdm.StudentTm;
import lk.ijse.util.Regex;

import java.sql.SQLException;
import java.util.List;

public class StudentPageController {

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXComboBox<String> cmbLevel;

    @FXML
    private JFXComboBox<String> cmbCoordinator;

    @FXML
    private JFXComboBox<String> cmbCourse;

    @FXML
    private TableColumn<?, ?> colStAddress;

    @FXML
    private TableColumn<?, ?> colStContact;

    @FXML
    private TableColumn<?, ?> colStEmail;

    @FXML
    private TableColumn<?, ?> colStId;

    @FXML
    private TableColumn<?, ?> colStName;

    @FXML
    private TableColumn<?, ?> colStAge;
    @FXML
    private TableColumn<?, ?> colStLevel;

    @FXML
    private TableColumn<?, ?> colStCoordinator;

    @FXML
    private TableColumn<?, ?> colStCourse;

    @FXML
    private TableView<StudentTm> tblStudent;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtAge;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtStuName;

    @FXML
    private TextField txtStId;

    StudentBo studentBo = (StudentBo) BOFactory.getBoFactory().GetBo(BOFactory.BOType.STUDENT);
    ProgramBo programBo = (ProgramBo) BOFactory.getBoFactory().GetBo(BOFactory.BOType.PROGRAM) ;
    UserBo userBo = (UserBo) BOFactory.getBoFactory().GetBo(BOFactory.BOType.USER);

    public String StaticUserName;
    public void initialize() {
        getStudentLevelForCombo();
        getCoursesForCombo();
        getCoordinatorForCombo();
        generateStudentId();
        loadAllStudents();
        StaticUserName=MainPageController.staticUserName;
    }

    private void getCoordinatorForCombo() {
        List<String> userNames=userBo.getUserNames();
        ObservableList<String> users = FXCollections.observableArrayList();
        for (String userName : userNames) {
            users.add(userName);
        }
        cmbCoordinator.setItems(users);

    }

    private void getCoursesForCombo() {
        List<String> courseNames=programBo.getProgramNames();
        ObservableList<String> names = FXCollections.observableArrayList();
        for (String coursesName : courseNames) {
            names.add(coursesName);
        }
        cmbCourse.setItems(names);

    }

    private void setCellValueFactory() {
        colStId.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        colStName.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        colStEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colStAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colStContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colStAge.setCellValueFactory(new PropertyValueFactory<>("studentAge"));
        colStLevel.setCellValueFactory(new PropertyValueFactory<>("regLevel"));
        colStCourse.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        colStCoordinator.setCellValueFactory(new PropertyValueFactory<>("user"));

    }

    private void generateStudentId() {
        try {
            String currentId = studentBo.getCurrentId();


            txtStId.setText(currentId);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void getStudentLevelForCombo() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        obList.add("Level 1");
        obList.add("Level 2");
        obList.add("Registered");

        cmbLevel.setItems(obList);
    }

    private void loadAllStudents() {
        ObservableList<StudentTm> obList = FXCollections.observableArrayList();


        try {
            List<StudentDTO> studentList = studentBo.getAllStudents();
            for (StudentDTO student : studentList) {
                StudentTm tm = new StudentTm(
                        student.getStudentId(),
                        student.getStudentName(),
                        student.getStudentAge(),
                        student.getEmail(),
                        student.getContact(),
                        student.getAddress(),
                        student.getRegLevel(),
                        student.getCourseName(),
                        student.getUser().getUsername()
                );

                obList.add(tm);
            }

            tblStudent.setItems(obList);
            setCellValueFactory();
            generateStudentId();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        cmbLevel.setValue(null);
        txtAddress.setText(null);
        txtAge.setText(null);
        txtContact.setText(null);
        txtEmail.setText(null);
        txtStuName.setText(null);
        cmbCourse.setValue(null);
        cmbCoordinator.setValue(null);
        generateStudentId();

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (txtStId.getText() == null) {
            new Alert(Alert.AlertType.ERROR, "Id column is empty Please enter a valid ID!").show();
            return;
        }

        System.out.println("okkkkkkkkk");

        String name = txtStuName.getText();
        int age = Integer.parseInt(txtAge.getText());
        String contact = txtContact.getText();
        String email = txtEmail.getText();
        String address = txtAddress.getText();
        String id = txtStId.getText();
        String level = cmbLevel.getValue();
        String courseName = cmbCourse.getValue();
        String userName = cmbCoordinator.getValue();
        User user = userBo.searchByName(userName);


        StudentDTO studentDTO = new StudentDTO(id,name,age,email,contact,address,level,courseName,user);

        boolean isDelete = studentBo.deleteStudent((studentDTO));

        if (isDelete) {
            new Alert(Alert.AlertType.CONFIRMATION, "Customer deleted!!!.").show();
            setCellValueFactory();
            loadAllStudents();
            clearFields();
            generateStudentId();
        }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (!isValied()) {
            new Alert(Alert.AlertType.ERROR, "Please check all fields.").show();
            return;
        }

        if (txtStId.getText()==null || txtAddress.getText()==null || txtEmail.getText() == null || txtAge.getText() ==null || txtStuName.getText()==null) {
            new Alert(Alert.AlertType.ERROR, "please Fill all field !").show();
            return;
        }
        String name = txtStuName.getText();
        int age = Integer.parseInt(txtAge.getText());
        String contact = txtContact.getText();
        String email = txtEmail.getText();
        String address = txtAddress.getText();
        String id = txtStId.getText();
        String level = cmbLevel.getValue();
        String courseName = cmbCourse.getValue();
        String userName = cmbCoordinator.getValue();
        User user = userBo.searchByName(userName);


        StudentDTO studentDTO = new StudentDTO(id,name,age,email,contact,address,level,courseName,user);

        try {
            boolean isSaved = studentBo.saveStudent(studentDTO);
            System.out.println(isSaved);
            if (isSaved) {
                System.out.println(isSaved);
                new Alert(Alert.AlertType.CONFIRMATION, "Student saved!!!.").show();
                setCellValueFactory();
                loadAllStudents();
                clearFields();
                generateStudentId();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    public boolean isValied(){
        if (!Regex.setTextColor(lk.ijse.util.TextField.ADDRESS,txtAddress)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.AMOUNT,txtAge)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.CONTACT,txtContact)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.NAME,txtStuName)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.EMAIL,txtEmail)) return false;
        return true;
    }

    private void clearFields() {
        cmbLevel.setValue(null);
        txtAddress.setText(null);
        txtAge.setText(null);
        txtContact.setText(null);
        txtEmail.setText(null);
        txtStuName.setText(null);
        cmbCourse.setValue(null);
        cmbCoordinator.setValue(null);
        generateStudentId();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (txtAddress.getText() == null || txtStuName.getText() ==null) {
            new Alert(Alert.AlertType.ERROR, "please select valid row in table !").show();
            return;
        }
        String name = txtStuName.getText();
        int age = Integer.parseInt(txtAge.getText());
        String contact = txtContact.getText();
        String email = txtEmail.getText();
        String address = txtAddress.getText();
        String id = txtStId.getText();
        String level = cmbLevel.getValue();
        String courseName = cmbCourse.getValue();
        String userName = cmbCoordinator.getValue();
        User user = userBo.searchByName(userName);


        StudentDTO studentDTO = new StudentDTO(id,name,age,email,contact,address,level,courseName,user);


        boolean isUpdated = studentBo.upadteStudent((studentDTO));

        if (isUpdated) {
            new Alert(Alert.AlertType.CONFIRMATION, "Student Updated!!!.").show();
            setCellValueFactory();
            loadAllStudents();
            clearFields();
            generateStudentId();
        }else {
            new Alert(Alert.AlertType.ERROR, "Student not updated!!!").show();
        }


    }
    @FXML
    void tblOnMouseClicked(MouseEvent event) {
        int index = tblStudent.getSelectionModel().getSelectedIndex();

        if (index <= -1){
            return;
        }

        String id = (String) colStId.getCellData(index);
        String name = (String) colStName.getCellData(index);
        int age = (int) colStAge.getCellData(index);
        String email = (String) colStEmail.getCellData(index);
        String contact = (String) colStContact.getCellData(index);
        String address = (String) colStAddress.getCellData(index);
        String level = (String) colStLevel.getCellData(index);
        String coordinator = (String) colStCoordinator.getCellData(index);
        String course = (String) colStCourse.getCellData(index);

        txtStId.setText(id);
        txtStuName.setText(name);
        txtAddress.setText(address);
        txtAge.setText(String.valueOf(age));
        txtContact.setText(contact);
        txtEmail.setText(email);
        cmbLevel.setValue(level);
        cmbCourse.setValue(course);
        cmbCoordinator.setValue(coordinator);

    }

    @FXML
    void cmbCoordinatorOnAction(ActionEvent event) {

    }

    @FXML
    void cmbCourseOnAction(ActionEvent event) {

    }

    @FXML
    void cmbLevelOnAction(ActionEvent event) {

    }

    @FXML
    void txtIAddressOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.ADDRESS,txtAddress);

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
        Regex.setTextColor(lk.ijse.util.TextField.EMAIL,txtEmail);

    }

    @FXML
    void txtINameOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.NAME,txtStuName);

    }

}
