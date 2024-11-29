package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lk.ijse.BO.BOFactory;
import lk.ijse.BO.custom.PaymentBo;
import lk.ijse.BO.custom.StudentBo;
import lk.ijse.BO.custom.StudentProgramBo;
import lk.ijse.Entity.Payment;
import lk.ijse.Entity.Student;
import lk.ijse.Entity.Student_Program;
import lk.ijse.dto.PaymentDTO;
import lk.ijse.dto.ProgramDTO;
import lk.ijse.dto.StudentDTO;
import lk.ijse.tdm.PaymentTm;
import lk.ijse.tdm.StudentTm;
import lk.ijse.util.Regex;
import lombok.Data;
import org.controlsfx.control.Notifications;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import static lk.ijse.controller.ForgetPasswordEmailPageController.email;

public class PaymentPageController {

    @FXML
    private Label LblPayPlan;

    @FXML
    private Label lblCourseName;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<?, ?> colDiscount;

    @FXML
    private TableColumn<?, ?> colPayAmount;

    @FXML
    private TableColumn<?, ?> colPayId;

    @FXML
    private TableColumn<?, ?> colPaymentPlan;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colStu;

    @FXML
    private TableColumn<?, ?> colToBePayAmount;

    @FXML
    private TableColumn<?, ?> colTotalAmount;

    @FXML
    private TableColumn<?, ?> colType;

    @FXML
    private Label lblPayNo;

    @FXML
    private Label lblStuName;

    @FXML
    private Label lblToBePayAmount;

    @FXML
    private Label lblTotalAmount;

    @FXML
    private TableView<PaymentTm> tblIPayment;

    @FXML
    private TextField txtPayAmount;

    @FXML
    private TextField txtStuSearch;
    private Stage primaryStage;

    PaymentBo paymentBo = (PaymentBo) BOFactory.getBoFactory().GetBo(BOFactory.BOType.PAYMENT) ;
    StudentBo studentBo = (StudentBo) BOFactory.getBoFactory().GetBo(BOFactory.BOType.STUDENT);
    StudentProgramBo studentProgramBo = (StudentProgramBo) BOFactory.getBoFactory().GetBo(BOFactory.BOType.STUDENT_PROGRAM) ;

    public void initialize() {
        loadAllPayments();
    }

    private void setCellValueFactory() {
        colPayId.setCellValueFactory(new PropertyValueFactory<>("paymentID"));
        colPayAmount.setCellValueFactory(new PropertyValueFactory<>("payAmount"));
        colPaymentPlan.setCellValueFactory(new PropertyValueFactory<>("paymentPlan"));
        colTotalAmount.setCellValueFactory(new PropertyValueFactory<>("TotalAmount"));
        colToBePayAmount.setCellValueFactory(new PropertyValueFactory<>("AmountToBePay"));
        colType.setCellValueFactory(new PropertyValueFactory<>("paymentType"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));
        colStu.setCellValueFactory(new PropertyValueFactory<>("student"));

    }

    private void loadAllPayments() {
        ObservableList<PaymentTm> obList = FXCollections.observableArrayList();


        try {
            List<PaymentDTO> paymentDTOS = paymentBo.getAllPayment();
            for (PaymentDTO payment : paymentDTOS) {
                PaymentTm tm = new PaymentTm(
                        payment.getPaymentID(),
                        payment.getPaymentType(),
                        payment.getPayAmount(),
                        payment.getAmountToBePay(),
                        payment.getTotalAmount(),
                        payment.getPaymentPlan(),
                        payment.getPaymentStatus(),
                        payment.getStudent().getContact()

                );

                obList.add(tm);
            }

            tblIPayment.setItems(obList);
            setCellValueFactory();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        lblPayNo.setText(null);
        lblToBePayAmount.setText(null);
        lblTotalAmount.setText(null);
        LblPayPlan.setText(null);
        txtStuSearch.setText(null);
        lblStuName.setText(null);
        lblCourseName.setText(null);

    }

    void clear(){
        lblPayNo.setText(null);
        lblToBePayAmount.setText(null);
        lblTotalAmount.setText(null);
        LblPayPlan.setText(null);
        txtStuSearch.setText(null);
        lblStuName.setText(null);
        lblCourseName.setText(null);
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String payNo= lblPayNo.getText();
        Payment payment=paymentBo.searchById(payNo);
        if (!isValied()) {
            new Alert(Alert.AlertType.ERROR, "Please check all fields.").show();
            return;
        }
        if (txtPayAmount.getText()==null) {
            new Alert(Alert.AlertType.ERROR, "please select valid row in table !").show();
            return;
        }

        if(payment!=null){
            Student student = payment.getStudent();
            String paymentType= payment.getPaymentType();
            double total = Double.parseDouble(lblTotalAmount.getText());
            double payAmount = Double.parseDouble(txtPayAmount.getText());
            double payAmount1 = payment.getPayAmount();
            double netPayAmount = payAmount+payAmount1;
            double netAmountToBePay =total-netPayAmount;
            String paymentPlan= payment.getPaymentPlan();
            Student_Program regId = payment.getStudentProgram();
            String date = String.valueOf(Date.valueOf(payment.getPaymentDate()));
            String paymentStatus;
            if (netAmountToBePay == 0){
                paymentStatus="Complete";
            }else {
                paymentStatus="pending";
            }

            double amountToBePay = Double.parseDouble(lblToBePayAmount.getText());

            if (amountToBePay<payAmount){
                new Alert(Alert.AlertType.ERROR, "invalid payment amount.").show();
                return;
            }

            PaymentDTO paymentDTO = new PaymentDTO(payNo,date,paymentType,netPayAmount,netAmountToBePay,total,paymentPlan,paymentStatus,regId,student);

            boolean update = paymentBo.update(paymentDTO);

            if(update){
                new Alert(Alert.AlertType.CONFIRMATION, "Payment Updated!!!.").show();
                loadAllPayments();
                setCellValueFactory();
                clear();

            }

        }
    }

    @FXML
    void tblOnMouseClicked(MouseEvent event) throws SQLException, ClassNotFoundException {
        int index = tblIPayment.getSelectionModel().getSelectedIndex();

        if (index <= -1){
            return;
        }

        String id = (String) colPayId.getCellData(index);
        double total = (double) colTotalAmount.getCellData(index);
        double toBePayAmount = (double) colToBePayAmount.getCellData(index);
        String paymentPlan = (String) colPaymentPlan.getCellData(index);
        String stuContact = (String) colStu.getCellData(index);

        Student student=studentBo.searchByContact(stuContact);

        String StuName=student.getStudentName();
        String courseName = student.getCourseName();

        Payment payment = paymentBo.searchById(id);
        Student_Program studentProgram = studentProgramBo.SearchById(payment.getStudentProgram().getStudent_course_id());



        lblPayNo.setText(id);
        lblToBePayAmount.setText(String.valueOf(toBePayAmount));
        lblTotalAmount.setText(String.valueOf(total));
        LblPayPlan.setText(String.valueOf(paymentPlan));
        txtStuSearch.setText(stuContact);
        lblStuName.setText(StuName);
        lblCourseName.setText(studentProgram.getProgram().getProgramName());

    }

    public boolean isValied(){
        if (!Regex.setTextColor(lk.ijse.util.TextField.AMOUNT,txtPayAmount)) return false;
        return true;
    }


    @FXML
    void txtIEmailOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtINameOnKeyPressed(KeyEvent event) {

    }

    @FXML
    void txtINameOnKeyReleased(KeyEvent event) {

    }

    @FXML
    void txtStuSearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String search = txtStuSearch.getText();
        Student studentDTOS=studentBo.searchByContact(search);

        if (studentDTOS!=null) {
            if (studentDTOS.getRegLevel().equals("Registered")) {
                Notifications notification = Notifications.create()
                        .title("Information")
                        .text("find Successfully")
                        .position(Pos.TOP_LEFT) // Position at the top left of the screen
                        .owner(primaryStage);
                notification.showInformation();

                lblStuName.setText(studentDTOS.getStudentName());
                List<Payment> payment =paymentBo.searchByStuId(studentDTOS.getStudentId());

                System.out.println(payment.size());
                if (payment.size()>1) {
                    laodPayments(payment);
                }else {
                    for (Payment p : payment) {
                        lblPayNo.setText(p.getPaymentID());
                        lblToBePayAmount.setText(String.valueOf(p.getAmountToBePay()));
                        lblTotalAmount.setText(String.valueOf(p.getTotalAmount()));
                        LblPayPlan.setText(String.valueOf(p.getPaymentPlan()));
                        Payment payment1 = paymentBo.searchById(p.getPaymentID());
                        Student_Program studentProgram = studentProgramBo.SearchById(payment1.getStudentProgram().getStudent_course_id());
                        lblCourseName.setText(studentProgram.getProgram().getProgramName());



                    }

                }




            }else{
                Notifications notification = Notifications.create()
                        .title("Error") // Title for the error notification
                        .text(studentDTOS.getStudentName()+ " " +"This Customer is not Registered.") // Descriptive error message
                        .position(Pos.TOP_LEFT) // Position at the top left of the screen
                        .owner(primaryStage);
                notification.showError(); // Displays the notification as an error

            }

        }else {
            Notifications notification = Notifications.create()
                    .title("Error")
                    .text("Can't find student Please enter Student information")
                    .position(Pos.TOP_LEFT) // Position at the top left of the screen
                    .owner(primaryStage);
            notification.showInformation();
        }
    }

    private void laodPayments(List<Payment> payment) {
        ObservableList<PaymentTm> obList = FXCollections.observableArrayList();

        for (Payment p : payment) {
            PaymentTm tm = new PaymentTm(
                    p.getPaymentID(),
                    p.getPaymentType(),
                    p.getPayAmount(),
                    p.getAmountToBePay(),
                    p.getTotalAmount(),
                    p.getPaymentPlan(),
                    p.getPaymentStatus(),
                    p.getStudent().getContact()

            );
            obList.add(tm);
        }
        tblIPayment.setItems(obList);
        setCellValueFactory();
    }



    @FXML
    void btnShowAllOnAction(ActionEvent event) {
        loadAllPayments();

    }

}
