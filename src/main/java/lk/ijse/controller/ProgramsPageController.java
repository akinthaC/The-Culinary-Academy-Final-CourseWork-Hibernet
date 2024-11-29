package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
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
import lk.ijse.BO.BOFactory;
import lk.ijse.BO.custom.ProgramBo;
import lk.ijse.BO.custom.StudentBo;
import lk.ijse.dto.ProgramDTO;
import lk.ijse.dto.StudentDTO;
import lk.ijse.tdm.ProgramTm;
import lk.ijse.tdm.StudentTm;
import lk.ijse.util.Regex;

import java.sql.SQLException;
import java.util.List;

public class ProgramsPageController {

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<?, ?> colProgramDuration;

    @FXML
    private TableColumn<?, ?> colProgramFee;

    @FXML
    private TableColumn<?, ?> colProgramId;

    @FXML
    private TableColumn<?, ?> colProgramName;

    @FXML
    private TableView<ProgramTm> tblProgram;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtFee;

    @FXML
    private TextField txtProName;

    @FXML
    private TextField txtProgramId;

    ProgramBo programBo = (ProgramBo) BOFactory.getBoFactory().GetBo(BOFactory.BOType.PROGRAM) ;
    public void initialize() throws SQLException, ClassNotFoundException {
        generateProgramId();
        loadAllPrograms();
    }

    private void setCellValueFactory() {
        colProgramId.setCellValueFactory(new PropertyValueFactory<>("programId"));
        colProgramName.setCellValueFactory(new PropertyValueFactory<>("programName"));
        colProgramDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colProgramFee.setCellValueFactory(new PropertyValueFactory<>("fee"));

    }

    private void loadAllPrograms() throws SQLException, ClassNotFoundException {
        ObservableList<ProgramTm> obList = FXCollections.observableArrayList();

        List<ProgramDTO> programDTOList = programBo.getAllCourses();
        for (ProgramDTO program : programDTOList) {
            ProgramTm tm = new ProgramTm(
                    program.getProgramId(),
                    program.getProgramName(),
                    program.getDuration(),
                    program.getFee()

            );

            obList.add(tm);
        }

        tblProgram.setItems(obList);
        setCellValueFactory();
        generateProgramId();
    }

    private void generateProgramId() {
        try {
            String currentId = programBo.getCurrentId();


            txtProgramId.setText(currentId);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        txtProName.setText(null);
        txtDuration.setText(null);
        txtFee.setText(null);
        generateProgramId();

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (txtProgramId.getText() == null || txtDuration.getText() == null || txtFee.getText() == null || txtProName.getText() == null) {
            new Alert(Alert.AlertType.ERROR, "please select valid row in table !").show();
            return;
        }

        String name = txtProName.getText();
        double fee = Double.parseDouble(txtFee.getText());
        String duration = txtDuration.getText();
        String id = txtProgramId.getText();


        ProgramDTO programDTO = new ProgramDTO(id,name,duration,fee);
        boolean isUpdated = programBo.deleteProgram((programDTO));

        if (isUpdated) {
            new Alert(Alert.AlertType.CONFIRMATION, "Program Deleted!!!.").show();
            setCellValueFactory();
            loadAllPrograms();
            clearFields();
            generateProgramId();
        }else {
            new Alert(Alert.AlertType.ERROR, "Program not Deleted!!!").show();
        }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

        if (!isValied()) {
            new Alert(Alert.AlertType.ERROR, "Please check all fields.").show();
            return;
        }

        if (txtProgramId.getText() == null || txtDuration.getText() == null || txtFee.getText() == null || txtProName.getText() == null) {
            new Alert(Alert.AlertType.ERROR, "please Fill all field !").show();
            return;
        }

        String name = txtProName.getText();
        double fee = Double.parseDouble(txtFee.getText());
        String duration = txtDuration.getText();
        String id = txtProgramId.getText();


        ProgramDTO programDTO = new ProgramDTO(id,name,duration,fee);

        try {
            boolean isSaved = programBo.saveProgram(programDTO);
            System.out.println(isSaved);
            if (isSaved) {
                System.out.println(isSaved);
                new Alert(Alert.AlertType.CONFIRMATION, "Student saved!!!.").show();
                setCellValueFactory();
                loadAllPrograms();
                clearFields();
                generateProgramId();
            }
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    private void clearFields() {
        txtProName.setText(null);
        txtDuration.setText(null);
        txtFee.setText(null);
        generateProgramId();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (txtProgramId.getText() == null || txtDuration.getText() == null || txtFee.getText() == null || txtProName.getText() == null) {
            new Alert(Alert.AlertType.ERROR, "please select valid row in table !").show();
            return;
        }

        String name = txtProName.getText();
        double fee = Double.parseDouble(txtFee.getText());
        String duration = txtDuration.getText();
        String id = txtProgramId.getText();


        ProgramDTO programDTO = new ProgramDTO(id,name,duration,fee);
        boolean isUpdated = programBo.updateProgram((programDTO));

        if (isUpdated) {
            new Alert(Alert.AlertType.CONFIRMATION, "Program Updated!!!.").show();
            setCellValueFactory();
            loadAllPrograms();
            clearFields();
            generateProgramId();
        }else {
            new Alert(Alert.AlertType.ERROR, "Program not updated!!!").show();
        }

    }

    @FXML
    void tblOnMouseClicked(MouseEvent event) {
        int index = tblProgram.getSelectionModel().getSelectedIndex();

        if (index <= -1){
            return;
        }

        String id = (String) colProgramId.getCellData(index);
        String name = (String) colProgramName.getCellData(index);
        String duration = (String) colProgramDuration.getCellData(index);
        double fee = (double) colProgramFee.getCellData(index);


        txtProgramId.setText(id);
        txtProName.setText(name);
        txtDuration.setText(duration);
        txtFee.setText(String.valueOf(fee));


    }
    public boolean isValied(){
        if (!Regex.setTextColor(lk.ijse.util.TextField.PRICE,txtFee)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.NAME,txtProName)) return false;
        return true;
    }

    @FXML
    void txtIAgeOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtIEmailOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.PRICE,txtFee);


    }

    @FXML
    void txtINameOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtProgramIdOnAction(ActionEvent event) {

    }

}
