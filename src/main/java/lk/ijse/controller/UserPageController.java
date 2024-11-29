package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import lk.ijse.BO.custom.UserBo;
import lk.ijse.Entity.Payment;
import lk.ijse.Entity.Student;
import lk.ijse.Entity.Student_Program;
import lk.ijse.Entity.User;
import lk.ijse.dto.PaymentDTO;
import lk.ijse.dto.UserDTO;
import lk.ijse.tdm.PaymentTm;
import lk.ijse.tdm.UserTm;
import lk.ijse.util.Regex;

import java.sql.SQLException;
import java.util.List;

public class UserPageController {

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXComboBox<String> cmbJobRole;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colJobRole;

    @FXML
    private TableColumn<?, ?> colUserId;

    @FXML
    private TableView<UserTm> tblProgram;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserId;
    UserBo userBo = (UserBo) BOFactory.getBoFactory().GetBo(BOFactory.BOType.USER);

    public void initialize() {

        loadAllUsers();
        loadCmbSetJobRoles();
    }

    private void loadCmbSetJobRoles() {
        ObservableList<String> obList = FXCollections.observableArrayList(
                "admin",
                "coordinator"

        );

        cmbJobRole.setItems(obList);

    }

    private void setCellValueFactory() {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("username"));
        colJobRole.setCellValueFactory(new PropertyValueFactory<>("JobRole"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

    }

    private void loadAllUsers() {
        ObservableList<UserTm> obList = FXCollections.observableArrayList();


        try {
            List<UserDTO> userDTOS = userBo.getAllPayment();
            for (UserDTO user : userDTOS) {
                UserTm tm = new UserTm(
                        user.getUsername(),
                        user.getEmail(),
                        user.getJobRole()
                );

                obList.add(tm);
            }

            tblProgram.setItems(obList);
            setCellValueFactory();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String jobRole = (String) cmbJobRole.getValue();
        String userId = txtUserId.getText();

        String BCriptPassword = PasswordUtil.encryptPassword(password);

        User user = new User();
        user.setEmail(email);
        user.setPassword(BCriptPassword);
        user.setJobRole(jobRole);
        user.setUsername(userId);

        boolean deleted = userBo.delete(user);
        if (deleted){
            new Alert(Alert.AlertType.CONFIRMATION, "User deleted!!!.").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "User not deleted!!!").show();

        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (!isValied()) {
            new Alert(Alert.AlertType.ERROR, "Please check all fields.").show();
            return;
        }

        if (txtUserId.getText()==null || txtEmail.getText()==null   ) {
            new Alert(Alert.AlertType.ERROR, "please Fill all field !").show();
            return;
        }
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        String jobRole = (String) cmbJobRole.getValue();
        String userId = txtUserId.getText();

        String BCriptPassword = PasswordUtil.encryptPassword(password);

        User user = new User();
        user.setEmail(email);
        user.setPassword(BCriptPassword);
        user.setJobRole(jobRole);
        user.setUsername(userId);

        boolean saved = userBo.save(user);

        if (saved){
            new Alert(Alert.AlertType.CONFIRMATION, "User saved!!!.").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "User not Saved!!!").show();

        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String email = txtEmail.getText();

        String jobRole = (String) cmbJobRole.getValue();
        String userId = txtUserId.getText();

        User user1 =userBo.searchByName(userId);
        String password = user1.getPassword();

        String BCriptPassword = PasswordUtil.encryptPassword(password);

        User user = new User();
        user.setEmail(email);
        user.setPassword(BCriptPassword);
        user.setJobRole(jobRole);
        user.setUsername(userId);

        boolean saved = userBo.updateUser(user);

        if (saved){
            new Alert(Alert.AlertType.CONFIRMATION, "User  updated!!!.").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "User not updated!!!").show();

        }
    }

    @FXML
    void tblOnMouseClicked(MouseEvent event) {
        int index = tblProgram.getSelectionModel().getSelectedIndex();

        if (index <= -1){
            return;
        }

        String id = (String) colUserId.getCellData(index);
        String email = (String) colEmail.getCellData(index);
        String role = (String) colJobRole.getCellData(index);

        txtEmail.setText(email);
        txtUserId.setText(id);
        cmbJobRole.setValue(role);

    }

    @FXML
    void txtEmailOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.EMAIL,txtEmail);

    }

    @FXML
    void txtPasswordeOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.PASSWORD,txtPassword);

    }

    @FXML
    void txtUserOnAction(ActionEvent event) {

    }

    public boolean isValied(){
        if (!Regex.setTextColor(lk.ijse.util.TextField.PASSWORD,txtPassword)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.EMAIL,txtEmail)) return false;
        return true;
    }

}
