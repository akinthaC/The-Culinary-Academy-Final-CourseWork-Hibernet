package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ResourceBundle;

public class MainPageCoordinatorController implements Initializable {

    @FXML
    private JFXButton btnDashBoard;

    @FXML
    private JFXButton btnRegister;

    @FXML
    private JFXButton btnReport;

    @FXML
    private JFXButton btnSetting;

    @FXML
    private JFXButton btnStudent;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblGreetings;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblUserId;

    @FXML
    private Label lblUserType;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private ImageView wishImageView;

    public static String staticUserName;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            dashBoard();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        addHoverAnimation(btnDashBoard);
        addHoverAnimation(btnStudent);
        addHoverAnimation(btnSetting);
        addHoverAnimation(btnReport);
        addHoverAnimation(btnRegister);
        lblUserId.setText(LoginPageController.staticUserName);
        lblUserType.setText(LoginPageController.staticJobRole);
        setDate();
        setTime();
        setGreeting();
        staticUserName=lblUserId.getText();

    }

    public void setGreeting() {
        Calendar c = Calendar.getInstance();
        c.setTime(new java.util.Date());
        int hours = c.get(Calendar.HOUR_OF_DAY);

        // Set image and label based on the time of the day
        if (hours >= 0 && hours < 12) {
            lblGreetings.setText("Good Morning !!!");
            wishImageView.setImage(new Image(MainPageController.class.getResourceAsStream("/image/morning.png")));
        } else if (hours >= 12 && hours < 18) {
            lblGreetings.setText("Good Afternoon !!!");
            wishImageView.setImage(new Image(MainPageController.class.getResourceAsStream("/image/afternoon.png")));
        } else if (hours >= 18 && hours < 22) {
            lblGreetings.setText("Good Evening !!!");
            wishImageView.setImage(new Image(MainPageController.class.getResourceAsStream("/image/afternoon.png")));
        } else {
            lblGreetings.setText("Good Night !!!");
            wishImageView.setImage(new Image(MainPageController.class.getResourceAsStream("/image/night.png")));
        }
    }

    private void setTime() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {

            LocalTime currentTime = LocalTime.now();

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            String formattedTime = currentTime.format(timeFormatter);

            lblTime.setText(formattedTime);
        }), new KeyFrame(Duration.seconds(1)));

        clock.setCycleCount(Animation.INDEFINITE);

        clock.play();
    }

    private void setDate() {
        LocalDate now = LocalDate.now();
        lblDate.setText(String.valueOf(now));

    }

    private void dashBoard() throws IOException {

        loadFormWithAtractiveAnimation("/view/dashboard-page.fxml");

        // Show a notification

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
    void btnRegisterOnAction(ActionEvent event) throws IOException {
        loadFormWithAtractiveAnimation("/view/student-program-page.fxml");


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
