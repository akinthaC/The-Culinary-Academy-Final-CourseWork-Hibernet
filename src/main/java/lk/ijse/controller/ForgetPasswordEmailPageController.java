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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.BO.BOFactory;
import lk.ijse.BO.custom.UserBo;
import lk.ijse.Entity.User;
import lk.ijse.util.Regex;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

public class ForgetPasswordEmailPageController {

    @FXML
    private Button btnGoBack;

    @FXML
    private JFXButton btnSendOtp;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtUserName;

    @FXML
    private Label userLbl;
    public static int OTP;
    public static String email;
    String userName1;
    public static String userName;

    UserBo userBo = (UserBo) BOFactory.getBoFactory().GetBo(BOFactory.BOType.USER);
    public void initialize(){
        userName1=LoginPageController.staticUserName;
        System.out.println(userName1);
        txtUserName.setText(userName1);

    }

    @FXML
    void btnGoBackOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/login-page.fxml"));
        Scene scene = btnGoBack.getScene();
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
    void btnSendOtpOnAction(ActionEvent event) throws IOException {
        if (!isValied()) {
            new Alert(Alert.AlertType.ERROR, "Please check all fields.").show();
            return;
        }


        if (userName1!=null){
            Random random = new Random();
            int otp = 100000 + random.nextInt(900000);
            userName=txtUserName.getText();


            boolean ok =javaMailUtil.sendMail(txtEmail.getText(),otp);
            if (ok) {

                OTP = otp;
                System.out.println(">>>" + otp);

                Parent root = FXMLLoader.load(getClass().getResource("/view/forget-password-OTP-page.fxml"));

                Scene scene = btnSendOtp.getScene();
                root.setScaleX(0.5);
                root.setScaleY(0.5);
                root.setOpacity(0.0);

                AnchorPane parentContainer = (AnchorPane) scene.getRoot();

                // Remove the existing content
                parentContainer.getChildren().clear();

                // Add the new content
                parentContainer.getChildren().add(root);

                Timeline timeline = new Timeline();
                KeyValue scaleXValue = new KeyValue(root.scaleXProperty(), 1.0, Interpolator.EASE_BOTH);
                KeyValue scaleYValue = new KeyValue(root.scaleYProperty(), 1.0, Interpolator.EASE_BOTH);
                KeyValue opacityValue = new KeyValue(root.opacityProperty(), 1.0, Interpolator.EASE_BOTH);
                KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.5), scaleXValue, scaleYValue, opacityValue);
                timeline.getKeyFrames().add(keyFrame);
                timeline.play();
                Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                infoAlert.setTitle("Information");
                infoAlert.setHeaderText(null);
                infoAlert.setContentText("Email has been sent successfully .");
                infoAlert.showAndWait();
            }else {
                return;
            }
        }else {
            txtEmail.setStyle("-fx-background-color: Red");
        }
    }


    public boolean isValied(){
        if (!Regex.setTextColor(lk.ijse.util.TextField.EMAIL,txtEmail)) return false;
        return true;
    }

    @FXML
    void txtEmailOnKeyReleased(KeyEvent event) {
        Regex.setTextColor(lk.ijse.util.TextField.EMAIL,txtEmail);

    }

    @FXML
    void txtUserNameOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String userName = txtUserName.getText();
        User user = userBo.searchByName(userName);


        if (user != null) {
            if (user.getEmail() != null) {
                txtEmail.setText(user.getEmail());
                 userName1=user.getUsername();

            }else{
                Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                infoAlert.setTitle("Information");
                infoAlert.setHeaderText(null);
                infoAlert.setContentText("Please Enter Email Address .");
                infoAlert.showAndWait();
            }


        } else {
            Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
            infoAlert.setTitle("Information");
            infoAlert.setHeaderText(null);
            infoAlert.setContentText("User not found .");
            txtEmail.setText(null);
            infoAlert.showAndWait();
        }
    }

    @FXML
    void txtUserNameOnKeyReleased(KeyEvent event) {
        userLbl.setText("Click the Enter Button to see email");

    }

}
