package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.BO.BOFactory;
import lk.ijse.BO.custom.UserBo;

import java.io.IOException;
import java.sql.SQLException;

public class ForgetPasswordNewPasswordPageController {

    @FXML
    private JFXButton btnSave;

    @FXML
    private Hyperlink hyperBack;

    @FXML
    private Label lblOtp;

    @FXML
    private Label lblText;

    @FXML
    private PasswordField txtConformPassword;

    @FXML
    private TextField txtConformPassword1;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtPassword1;

    UserBo userBo = (UserBo) BOFactory.getBoFactory().GetBo(BOFactory.BOType.USER);

    public void initialize(){
        txtPassword1.setVisible(false);
        txtConformPassword1.setVisible(false);
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String password = txtPassword.getText();
        String reEnterPassword = txtConformPassword.getText();
        String userName = ForgetPasswordEmailPageController.userName;
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
            if (BCriptPassword.equals(reEnterPassword)){
                new Alert(Alert.AlertType.INFORMATION, "Password Matched!").show();
            }
            boolean isUpdated= userBo.update(BCriptPassword,userName);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Password Updated!!").show();
            }
        }

    }

    @FXML
    void hyperBackOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/login-page.fxml"));
        Scene scene = hyperBack.getScene();
        root.translateXProperty().set(scene.getWidth());

        AnchorPane parentContainer = (AnchorPane) scene.getRoot();

        // Remove the existing content
        parentContainer.getChildren().clear();

        // Add the new content
        parentContainer.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_BOTH);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.5), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
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

    }

}
