package lk.ijse.controller;

import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.BO.BOFactory;
import lk.ijse.BO.custom.StudentBo;
import lk.ijse.BO.custom.StudentProgramBo;
import lk.ijse.Entity.Student;
import lk.ijse.dao.custom.DashBoardDao;
import lk.ijse.dao.custom.impl.DashBoardDaoImpl;
import lk.ijse.tdm.RegTm;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class DashboardPageController {

    @FXML
    private TableColumn<?, ?> ColIntakeName;

    @FXML
    private TableColumn<?, ?> ColRegDate;

    @FXML
    private PieChart RegPieChart;

    @FXML
    private TableColumn<?, ?> colProgramName;

    @FXML
    private TableColumn<?, ?> colRegId;

    @FXML
    private Label lblPending;

    @FXML
    private Label lblPrograms;

    @FXML
    private Label lblRegTotal;

    @FXML
    private Label lblStuName;

    @FXML
    private AnchorPane mainPane;

    @FXML
    private LineChart<String, Number> regLineChart;

    @FXML
    private TableView<RegTm> tblReg;

    @FXML
    private TextField txtStuSearch;
    StudentBo studentBo = (StudentBo) BOFactory.getBoFactory().GetBo(BOFactory.BOType.STUDENT);
    StudentProgramBo studentProgramBo = (StudentProgramBo) BOFactory.getBoFactory().GetBo(BOFactory.BOType.STUDENT_PROGRAM) ;
    DashBoardDao dashBoardDAO = new DashBoardDaoImpl();

    public void initialize() throws IOException {
        iniPieChart();
        iniLineChart();
        intPendingStudent();
        intProgramCount();
        intRegisteredStudent();
    }

    private void intRegisteredStudent() {
        int count =  dashBoardDAO.getRegisterStudentCount();
        lblRegTotal.setText(String.valueOf(count));
    }

    private void intProgramCount() {
        int count =  dashBoardDAO.getProgramCount();
        lblPrograms.setText(String.valueOf(count));
    }

    private void intPendingStudent() {
        int count = dashBoardDAO.getPendingStudentCount();
        lblPending.setText(String.valueOf(count));
    }

    private void setCellValueFactory() {
        colRegId.setCellValueFactory(new PropertyValueFactory<>("regNo"));
        ColIntakeName.setCellValueFactory(new PropertyValueFactory<>("intakeName"));
        ColRegDate.setCellValueFactory(new PropertyValueFactory<>("regDate"));
        colProgramName.setCellValueFactory(new PropertyValueFactory<>("ProgramName"));
    }
    @FXML
    void txtINameOnKeyPressed(KeyEvent event) {

    }

    @FXML
    void txtINameOnKeyReleased(KeyEvent event) {

    }

    private void iniPieChart() throws IOException {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        Map<String, Long> studentsByCourse = dashBoardDAO.getStudentCountByCourse();

        for (Map.Entry<String, Long> entry : studentsByCourse.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

       RegPieChart .setData(pieChartData);

        javafx.application.Platform.runLater(() -> {
            for (PieChart.Data data : pieChartData) {
                if (data.getNode() != null) {
                    Tooltip tooltip = new Tooltip(data.getName() + ": " + data.getPieValue());
                    Tooltip.install(data.getNode(), tooltip);

                    data.getNode().setOnMouseEntered(event -> {
                        data.getNode().setStyle("-fx-scale-x: 1.1; -fx-scale-y: 1.1; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0, 0, 0);");
                    });
                    data.getNode().setOnMouseExited(event -> {
                        data.getNode().setStyle("-fx-scale-x: 1; -fx-scale-y: 1; -fx-effect: none;");
                    });
                }
            }
        });
        animatePieChart();
    }

    private void iniLineChart() throws IOException {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        Map<String, Long> paymentsByDay = dashBoardDAO.getRegistersByDay();

        for (Map.Entry<String, Long> entry : paymentsByDay.entrySet()) {
            XYChart.Data<String, Number> dataPoint = new XYChart.Data<>(entry.getKey(), entry.getValue());
            series.getData().add(dataPoint);
        }

        regLineChart.getData().add(series);

        javafx.application.Platform.runLater(() -> {
            for (XYChart.Data<String, Number> dataPoint : series.getData()) {
                if (dataPoint.getNode() != null) {
                    Tooltip tooltip = new Tooltip(dataPoint.getXValue() + ": " + dataPoint.getYValue());
                    Tooltip.install(dataPoint.getNode(), tooltip);

                    dataPoint.getNode().setOnMouseEntered(event -> {
                        dataPoint.getNode().setStyle("-fx-scale-x: 1.5; -fx-scale-y: 1.5;");
                    });
                    dataPoint.getNode().setOnMouseExited(event -> {
                        dataPoint.getNode().setStyle("-fx-scale-x: 1; -fx-scale-y: 1;");
                    });
                }
            }
        });

        animateLineChart();
    }

    private void animateLineChart() {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), regLineChart);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    private void animatePieChart() {
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(5), RegPieChart);
        rotateTransition.setByAngle(2);
        rotateTransition.setCycleCount(RotateTransition.INDEFINITE);
        rotateTransition.setInterpolator(javafx.animation.Interpolator.LINEAR);
        rotateTransition.play();
    }
    @FXML
    void txtStuSearchOnAction(ActionEvent event) {
        String stuSearch = txtStuSearch.getText();
        Student student= studentBo.searchByContact(stuSearch);


        if (student != null) {
            lblStuName.setText(student.getStudentName());
            List<RegTm> regTms = studentProgramBo.getAllRegData(stuSearch);
            loadTableData(regTms);
        }else {
            new Alert(Alert.AlertType.ERROR, "Can't Find Student!").show();

        }


    }

    private void loadTableData(List<RegTm> regTms) {
        ObservableList<RegTm> observableList = FXCollections.observableArrayList(regTms);
        tblReg.setItems(observableList);
        setCellValueFactory();
    }



}
