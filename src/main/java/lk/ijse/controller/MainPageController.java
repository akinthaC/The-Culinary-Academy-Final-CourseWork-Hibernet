package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {

    @FXML
    private JFXButton btnDashBoard;

    @FXML
    private JFXButton btnPayment;

    @FXML
    private JFXButton btnPrograms;

    @FXML
    private JFXButton btnReport;

    @FXML
    private JFXButton btnSetting;

    @FXML
    private JFXButton btnStudent;

    @FXML
    private Label lblUserId;

    @FXML
    private Label lblUserType;

    @FXML
    private AnchorPane mainPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            dashBoard();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        addHoverAnimation(btnDashBoard);
        addHoverAnimation(btnStudent);
        addHoverAnimation(btnPrograms);
        addHoverAnimation(btnPayment);
        addHoverAnimation(btnSetting);
        addHoverAnimation(btnReport);
        lblUserId.setText(LoginPageController.staticUserName);
        lblUserType.setText(LoginPageController.staticJobRole);

    }

    private void dashBoard() throws IOException {
        loadFormWithAtractiveAnimation("/view/dashboard-page.fxml");
    }

    private void addHoverAnimation(JFXButton button) {
        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(100), button);
        scaleIn.setFromX(1.0);
        scaleIn.setFromY(1.0);
        scaleIn.setToX(1.1);
        scaleIn.setToY(1.1);

        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(100), button);
        scaleOut.setFromX(1.1);
        scaleOut.setFromY(1.1);
        scaleOut.setToX(1.0);
        scaleOut.setToY(1.0);

        button.setOnMouseEntered(event -> {
            scaleIn.play();
        });

        button.setOnMouseExited(event -> {
            scaleOut.play();
        });
    }

    private void loadFormWithAtractiveAnimation(String formPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(formPath));
        AnchorPane newPane = loader.load();

        newPane.setOpacity(0);
        mainPane.getChildren().add(newPane);

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), newPane);
        translateTransition.setFromX(newPane.getWidth());
        translateTransition.setToX(0);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), newPane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);


        ScaleTransition zoomIn = new ScaleTransition(Duration.seconds(0.5), newPane);
        zoomIn.setFromX(0.5);
        zoomIn.setFromY(0.5);
        zoomIn.setToX(1.0);
        zoomIn.setToY(1.0);

        // Combine all transitions
        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(translateTransition,fadeTransition,zoomIn);
        parallelTransition.setOnFinished(event -> {
            mainPane.getChildren().clear();
            mainPane.getChildren().add(newPane);
        });
        parallelTransition.play();
    }


    @FXML
    void btnDashBoardOnAction(ActionEvent event) throws IOException {
        loadFormWithAtractiveAnimation("/view/dashboard-page.fxml");
    }

    @FXML
    void btnLogOutOnAction(ActionEvent event) {


    }

    @FXML
    void btnPaymentOnAction(ActionEvent event) {

    }

    @FXML
    void btnProgramsOnAction(ActionEvent event) {

    }

    @FXML
    void btnReportOnAction(ActionEvent event) {

    }

    @FXML
    void btnSettingOnAction(ActionEvent event) {

    }

    @FXML
    void btnStudentOnAction(ActionEvent event) throws IOException {
        loadFormWithAtractiveAnimation("/view/student-page.fxml");

    }

}
