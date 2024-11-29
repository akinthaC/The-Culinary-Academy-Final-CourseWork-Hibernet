package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import lk.ijse.BO.BOFactory;
import lk.ijse.BO.custom.IntakeBo;
import lk.ijse.BO.custom.ProgramBo;
import lk.ijse.Entity.Program;
import lk.ijse.dto.IntakeDTO;
import lk.ijse.dto.StudentDTO;
import lk.ijse.tdm.IntakeTm;
import lk.ijse.tdm.StudentTm;
import lk.ijse.util.Regex;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class IntakePageController {

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXComboBox<String> cmbCourse;

    @FXML
    private JFXComboBox<String> cmbStatus;

    @FXML
    private TableColumn<?, ?> colCourse;

    @FXML
    private TableColumn<?, ?> colEndDate;

    @FXML
    private TableColumn<?, ?> colIntakeId;

    @FXML
    private TableColumn<?, ?> colIntakeName;

    @FXML
    private TableColumn<?, ?> colStartDate;

    @FXML
    private TableColumn<?, ?> colStCapacity;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableView<IntakeTm> tblIntakeData;

    @FXML
    private TextField txtCapacity;

    @FXML
    private TextField txtEndDate;

    @FXML
    private TextField txtIntakeId;

    @FXML
    private TextField txtIntakeName;

    @FXML
    private TextField txtStartDate;
    ProgramBo programBo = (ProgramBo) BOFactory.getBoFactory().GetBo(BOFactory.BOType.PROGRAM) ;
    IntakeBo intakeBo = (IntakeBo) BOFactory.getBoFactory().GetBo(BOFactory.BOType.INTAKE) ;

    public void initialize() {
        getCoursesForCombo();
        generateIntakeId();
        loadAllIntakes();
        setDataForStatus();
        setDate();
    }


    private void setCellValueFactory() {
        colIntakeId.setCellValueFactory(new PropertyValueFactory<>("intakeId"));
        colIntakeName.setCellValueFactory(new PropertyValueFactory<>("intakeName"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        colStCapacity.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        colCourse.setCellValueFactory(new PropertyValueFactory<>("course"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));


    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        txtStartDate.setText(String.valueOf(now));
        txtEndDate.setText(String.valueOf(now));

    }
    private void loadAllIntakes() {
        ObservableList<IntakeTm> obList = FXCollections.observableArrayList();
        try {
            List<IntakeDTO> intakeDTOList = intakeBo.getAll();
            for (IntakeDTO intake : intakeDTOList) {
                IntakeTm tm = new IntakeTm(
                       intake.getIntakeId(),
                        intake.getIntakeName(),
                        intake.getStartDate(),
                        intake.getEndDate(),
                        intake.getCapacity(),
                        intake.getStatus(),
                        intake.getCourse().getProgramName()
                );

                obList.add(tm);
            }

            tblIntakeData.setItems(obList);
            setCellValueFactory();
            generateIntakeId();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


    }
    private void setDataForStatus() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        obList.add("Active");
        obList.add("Complete");
        obList.add("Close");

        cmbStatus.setItems(obList);
    }


    private void generateIntakeId() {
        try {
            String currentId = intakeBo.getCurrentId();


            txtIntakeId.setText(currentId);

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

    @FXML
    void btnClearOnAction(ActionEvent event) {
        cmbStatus.setValue(null);
        txtIntakeId.setText(null);
        txtCapacity.setText(null);
        txtIntakeName.setText(null);
        txtCapacity.setText(null);
        txtStartDate.setText(null);
        txtEndDate.setText(null);
        cmbCourse.setValue(null);
        cmbCourse.setValue(null);
        generateIntakeId();

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (txtStartDate.getText() == null || txtStartDate.getText() ==null) {
            new Alert(Alert.AlertType.ERROR, "please select valid row in table !").show();
            return;
        }
        String id = txtIntakeId.getText();
        String name = txtIntakeName.getText();
        Date startDate = Date.valueOf(txtStartDate.getText());
        Date endDate = Date.valueOf(txtEndDate.getText());
        int capacity = Integer.parseInt(txtCapacity.getText());
        String course = cmbCourse.getValue();
        String status =cmbStatus.getValue();
        Program program = programBo.searchByName(course);

        IntakeDTO intakeDTO = new IntakeDTO(id,name,startDate,endDate,capacity,status,program);

        boolean isDelete = intakeBo.DeleteIntake(intakeDTO);

        if (isDelete) {
            new Alert(Alert.AlertType.CONFIRMATION, "Intake deleted!!!.").show();
            setCellValueFactory();
            loadAllIntakes();
            clearFields();
            generateIntakeId();
        }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (!isValied()) {
            new Alert(Alert.AlertType.ERROR, "Please check all fields.").show();
            return;
        }

        if (txtCapacity.getText()==null || txtIntakeName.getText()==null || txtStartDate.getText() == null || txtStartDate.getText() ==null) {
            new Alert(Alert.AlertType.ERROR, "please Fill all field !").show();
            return;
        }

        String id = txtIntakeId.getText();
        String name = txtIntakeName.getText();
        Date startDate = Date.valueOf(txtStartDate.getText());
        Date endDate = Date.valueOf(txtEndDate.getText());
        int capacity = Integer.parseInt(txtCapacity.getText());
        String course = cmbCourse.getValue();
        String status =cmbStatus.getValue();
        Program program = programBo.searchByName(course);



        IntakeDTO intakeDTO = new IntakeDTO(id,name,startDate,endDate,capacity,status,program);

        boolean isSaved = intakeBo.saveIntake(intakeDTO);
        if (isSaved) {
            System.out.println(isSaved);
            new Alert(Alert.AlertType.CONFIRMATION, "Intake saved!!!.").show();
            setCellValueFactory();
            loadAllIntakes();
            clearFields();
            generateIntakeId();
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (!isValied()) {
            new Alert(Alert.AlertType.ERROR, "Please check all fields.").show();
            return;
        }
        if (txtCapacity.getText()==null || txtIntakeName.getText()==null || txtStartDate.getText() == null || txtStartDate.getText() ==null) {
            new Alert(Alert.AlertType.ERROR, "please select valid row in table !").show();
            return;
        }
        String id = txtIntakeId.getText();
        String name = txtIntakeName.getText();
        Date startDate = Date.valueOf(txtStartDate.getText());
        Date endDate = Date.valueOf(txtEndDate.getText());
        int capacity = Integer.parseInt(txtCapacity.getText());
        String course = cmbCourse.getValue();
        String status =cmbStatus.getValue();
        Program program = programBo.searchByName(course);

        IntakeDTO intakeDTO = new IntakeDTO(id,name,startDate,endDate,capacity,status,program);

        boolean isUpdate = intakeBo.updateIntake(intakeDTO);
        if (isUpdate) {
            new Alert(Alert.AlertType.CONFIRMATION, "Intake Updated!!!.").show();
            setCellValueFactory();
            loadAllIntakes();
            clearFields();
            generateIntakeId();
        }else {
            new Alert(Alert.AlertType.ERROR, "Intake not updated!!!").show();
        }

    }

    @FXML
    void cmbCourseOnAction(ActionEvent event) {

    }
    @FXML
    void cmbStatusOnAction(ActionEvent event) {

    }

    @FXML
    void tblOnMouseClicked(MouseEvent event) {
        int index = tblIntakeData.getSelectionModel().getSelectedIndex();

        if (index <= -1){
            return;
        }

        String id = (String) colIntakeId.getCellData(index);
        String name = (String) colIntakeName.getCellData(index);
        Date startDate = (Date) colStartDate.getCellData(index);
        Date endDate = (Date) colEndDate.getCellData(index);
        int capacity = (int) colStCapacity.getCellData(index);
        String course = (String) colCourse.getCellData(index);
        String status =(String) colStatus.getCellData(index);


        txtIntakeId.setText(id);
        txtIntakeName.setText(name);
        txtStartDate.setText(String.valueOf(startDate));
        txtEndDate.setText(String.valueOf(endDate));
        txtCapacity.setText(String.valueOf(capacity));
        cmbCourse.setValue(course);
        cmbStatus.setValue(status);


    }

    public boolean isValied(){
        if (!Regex.setTextColor(lk.ijse.util.TextField.AMOUNT,txtCapacity)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.DATE,txtEndDate)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.DATE,txtStartDate)) return false;
        return true;
    }

    @FXML
    void txtIAgeOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.DATE,txtStartDate);


    }

    @FXML
    void txtIContactOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.AMOUNT,txtCapacity);

    }

    @FXML
    void txtIEmailOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.DATE,txtEndDate);

    }

    @FXML
    void txtINameOnKeyReleased(KeyEvent event) {


    }
    private void clearFields() {
        cmbStatus.setValue(null);
        txtIntakeId.setText(null);
        txtCapacity.setText(null);
        txtIntakeName.setText(null);
        txtCapacity.setText(null);
        txtStartDate.setText(null);
        txtEndDate.setText(null);
        cmbCourse.setValue(null);
        cmbCourse.setValue(null);
        generateIntakeId();
    }


}
