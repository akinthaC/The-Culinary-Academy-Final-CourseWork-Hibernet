package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;

public class ForgetPasswordOTPPage {

    public AnchorPane AnchorPaneFogotPassword2;
    @FXML
    private Button btnGoBack;

    @FXML
    private Button btnResend;

    @FXML
    private JFXButton btnVerify;

    @FXML
    private Label lblOtp;

    @FXML
    private Label lblStatus;

    @FXML
    private TextField otp1;

    @FXML
    private TextField otp2;

    @FXML
    private TextField otp3;

    @FXML
    private TextField otp4;

    @FXML
    private TextField otp5;

    @FXML
    private TextField otp6;



    public void initialize(){
        Platform.runLater(() -> otp1.requestFocus());

        otp1.setOnKeyReleased(event -> handleKeyEvent(otp1, otp2, null));
        otp2.setOnKeyReleased(event -> handleKeyEvent(otp2, otp3, otp1));
        otp3.setOnKeyReleased(event -> handleKeyEvent(otp3, otp4, otp1));
        otp4.setOnKeyReleased(event -> handleKeyEvent(otp4, otp5, otp1));
        otp5.setOnKeyReleased(event -> handleKeyEvent(otp5, otp6, otp6));
        otp6.setOnKeyReleased(event -> handleKeyEvent(otp5, null, otp6));
    }

    private void handleKeyEvent(TextField currentTextField, TextField nextTextField, TextField previousTextField) {
        if (currentTextField.getText().length() == 1) {
            if (nextTextField != null) {
                nextTextField.requestFocus();
            }
        } else if (currentTextField.getText().isEmpty()) {
            if (previousTextField != null) {
                previousTextField.requestFocus();
            }
        }
        lblOtp.setText(String.valueOf(ForgetPasswordEmailPageController.OTP));
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
    void btnResendOnAction(ActionEvent event) {

    }

    @FXML
    void btnVerifyOnAction(ActionEvent event) throws IOException {
        String Otp1 = otp1.getText();
        String Otp2 = otp2.getText();
        String Otp3 = otp3.getText();
        String Otp4 = otp4.getText();
        String Otp5 = otp5.getText();
        String Otp6 = otp6.getText();

        String setOtp=Otp1+Otp2+Otp3+Otp4+Otp5+Otp6;



        try {
            if(setOtp.isEmpty()) {
                new Alert(Alert.AlertType.INFORMATION, "Please fill all fields!").show();
                return;
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }

        if (lblOtp.getText().equalsIgnoreCase(setOtp)){
            lblStatus.setStyle("-fx-text-fill: Green");
            lblStatus.setText("Correct OTP");

            Parent root = FXMLLoader.load(getClass().getResource("/view/forget-password-new-password-page.fxml"));
            Scene scene = btnVerify.getScene();
            root.translateXProperty().set(scene.getWidth());

            AnchorPane parentContainer = (AnchorPane) scene.getRoot();
            parentContainer.getChildren().add(root);

            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
            KeyFrame kf = new KeyFrame(Duration.seconds(1),kv);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(event1 -> {
                parentContainer.getChildren().removeAll(AnchorPaneFogotPassword2);
            });
            timeline.play();
        }else {
            lblStatus.setStyle("-fx-text-fill: Red");
            lblStatus.setText("Invalid OTP");
        }

    }



    @FXML
    void txtOTP1OnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtOTP2OnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtOTP3OnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtOTP4OnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtOTP5OnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtOTP6OnKeyReleased(KeyEvent event) {

    }

}
