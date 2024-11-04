package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.BO.BOFactory;
import lk.ijse.BO.custom.UserBo;
import lk.ijse.Entity.User;

import java.io.IOException;
import java.sql.SQLException;

public class LoginPageController {

    @FXML
    private AnchorPane rootNode;
    @FXML
    private JFXButton btnLogin;

    @FXML
    private Hyperlink hyperPassword;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtPassword1;

    @FXML
    private TextField txtUserName;

    public static String staticUserName;
    public static String staticJobRole;

    UserBo userBo = (UserBo) BOFactory.getBoFactory().GetBo(BOFactory.BOType.USER);

    public void initialize(){
        txtPassword1.setVisible(false);


    }
    @FXML
    void btnLoginOnAction(ActionEvent event) {
        String password = txtPassword.getText();
        String userName = txtUserName.getText();
        staticUserName = userName;

        try {
            if(password.isEmpty() || userName.isEmpty() ) {
                new Alert(Alert.AlertType.INFORMATION, "Please fill all fields!").show();
                return;
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }

        checkCredential(userName,password);



    }

    private void checkCredential(String userName, String password) {


        try {
            User user = userBo.searchByName(userName);


            if (user != null) {
                if (user.getPassword() != null  ) {

                    boolean deCriptPassword = PasswordUtil.matchPassword(password,user.getPassword());
                    staticJobRole = user.getJobRole();

                    if (deCriptPassword) {
                        if (user.getJobRole().equals("admin")) {
                            new Alert(Alert.AlertType.INFORMATION, "Welcome Admin!").show();
                            navigateToTheAdminDashboard();
                        } else {
                            new Alert(Alert.AlertType.INFORMATION, "Welcome!").show();
                        }
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Wrong Password try again!").show();
                        staticUserName = user.getUsername();
                    }
                }else {
                    new Alert(Alert.AlertType.ERROR, "Wrong Password try again!").show();
                    staticUserName = user.getUsername();
                }


            } else {
                Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
                infoAlert.setTitle("Information");
                infoAlert.setHeaderText(null);
                infoAlert.setContentText("Not registered customer .");
                infoAlert.showAndWait();
            }

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);

        }
    }

    private void navigateToTheAdminDashboard() {
        PauseTransition delay = new PauseTransition(Duration.seconds(1)); // 1 second delay
        delay.setOnFinished(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/LoadingPage.fxml"));
                AnchorPane loadingPage = loader.load();

                Scene scene = new Scene(loadingPage);

                Stage stage = (Stage) this.rootNode.getScene().getWindow();
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.setTitle("Loading");

                PauseTransition innerDelay = new PauseTransition(Duration.seconds(2)); // Inner delay
                innerDelay.setOnFinished(innerEvent -> {
                    try {
                        FXMLLoader mainFormLoader = new FXMLLoader(getClass().getResource("/view/main-page.fxml"));
                        AnchorPane mainForm = mainFormLoader.load();

                        Scene mainScene = new Scene(mainForm);

                        stage.setScene(mainScene);
                        stage.centerOnScreen();
                        stage.setTitle("Dashboard Form");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
                innerDelay.play();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        delay.play();
    }


    @FXML

    void hyperlinkForgetPasswordOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/forget-password-email-page.fxml"));
        Scene scene = hyperPassword.getScene();
        root.translateXProperty().set(scene.getWidth());

        AnchorPane parentContainer = (AnchorPane) scene.getRoot();


        parentContainer.getChildren().clear();


        parentContainer.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_BOTH);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.5), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    @FXML
    void showPasswordOnMousePressed(MouseEvent event) {
        txtPassword.setVisible(false);
        txtPassword1.setText(txtPassword.getText());
        txtPassword1.setVisible(true);

    }

    @FXML
    void showPasswordOnMouseReleased(MouseEvent event) {
        txtPassword.setVisible(true);
        txtPassword1.setVisible(false);


    }

    @FXML
    void txtLoginUserNameOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtPasswordOnKeyReleasedPasswordField(KeyEvent event) {

    }

    @FXML
    void txtNewPasswordOnKeyReleasedTxtField(KeyEvent event) {

    }

}
