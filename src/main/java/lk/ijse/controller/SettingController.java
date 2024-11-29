package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.BO.BOFactory;
import lk.ijse.BO.custom.UserBo;
import lk.ijse.Entity.User;
import lk.ijse.util.Regex;

import java.sql.SQLException;

public class SettingController {

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnSaveUser;

    @FXML
    private Label lblText;

    @FXML
    private Label lblUserName;

    @FXML
    private Label lblUserName1;

    @FXML
    private PasswordField txtConformPassword;

    @FXML
    private TextField txtConformPassword1;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtPassword1;

    @FXML
    private TextField txtUserName;


    UserBo userBo = (UserBo) BOFactory.getBoFactory().GetBo(BOFactory.BOType.USER);

    public void initialize(){
        txtPassword1.setVisible(false);
        txtConformPassword1.setVisible(false);
        lblUserName.setText(LoginPageController.staticUserName);
        lblUserName1.setText(LoginPageController.staticUserName);
    }
    @FXML
    void btnPasswordSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if (!isValied()) {
            new Alert(Alert.AlertType.ERROR, "Please check all fields.").show();
            return;
        }

        if (txtConformPassword.getText()==null || txtPassword.getText()==null ) {
            new Alert(Alert.AlertType.ERROR, "please Fill all field !").show();
            return;
        }
        String password = txtPassword.getText();
        String reEnterPassword = txtConformPassword.getText();
        String userName = LoginPageController.staticUserName;
        System.out.println(userName);

        try {
            if(password.isEmpty() || reEnterPassword.isEmpty() ) {
                new Alert(Alert.AlertType.INFORMATION, "Please fill all fields!").show();
                return;
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }

        if (password.equalsIgnoreCase(reEnterPassword)){
            String BCriptPassword = PasswordUtil.encryptPassword(password);
            System.out.println(BCriptPassword);

            boolean isUpdated= userBo.update(BCriptPassword,userName);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Password Updated!!").show();
            }
        }

    }


    public boolean isValied(){
        if (!Regex.setTextColor(lk.ijse.util.TextField.PASSWORD,txtConformPassword)) return false;
        if (!Regex.setTextColor(lk.ijse.util.TextField.PASSWORD,txtPassword)) return false;

        return true;
    }

    @FXML
    void btnUserSaveOnAction(ActionEvent event) {

        if (txtUserName.getText()==null ) {
            new Alert(Alert.AlertType.ERROR, "please Fill all field !").show();
            return;
        }

        String OldUserName = LoginPageController.staticUserName;
        String NewUserName = txtUserName.getText();
        try {
            if(NewUserName.isEmpty() ) {
                new Alert(Alert.AlertType.INFORMATION, "Please fill all fields!").show();
                return;
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
        boolean updated =userBo.updateUserName(OldUserName,NewUserName);
        if (updated) {
            new Alert(Alert.AlertType.CONFIRMATION, "UserName Updated!!").show();
        }

    }

    @FXML
    void showPasswordOnMousePresseds(MouseEvent event) {
        txtPassword.setVisible(false);
        txtPassword1.setText(txtPassword.getText());
        txtPassword1.setVisible(true);
    }

    @FXML
    void showPasswordOnMousePresseds1(MouseEvent event) {
        txtConformPassword.setVisible(false);
        txtConformPassword1.setText(txtConformPassword.getText());
        txtConformPassword1.setVisible(true);
    }

    @FXML
    void showPasswordOnMouseReleased(MouseEvent event) {
        txtPassword.setVisible(true);
        txtPassword1.setVisible(false);
    }

    @FXML
    void showPasswordOnMouseReleased1(MouseEvent event) {
        txtConformPassword.setVisible(true);
        txtConformPassword1.setVisible(false);

    }

    @FXML
    void txtConformPasswordOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtConformPasswordOnKeyReleased1(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.PASSWORD,txtConformPassword);

        if (txtPassword.getText().equals(txtConformPassword.getText())) {
            lblText.setStyle("-fx-text-fill: Green");
            lblText.setText("Password is Matched");
        } else {
            lblText.setStyle("-fx-text-fill: Red");
            lblText.setText("Password does not match");
        }
    }

    @FXML
    void txtNewPasswordOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtNewPasswordOnKeyReleased1(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.PASSWORD,txtPassword);

    }

    @FXML
    void txtUserNameOnAction(ActionEvent event) {

    }

}
