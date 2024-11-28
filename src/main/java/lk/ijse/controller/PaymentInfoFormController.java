package lk.ijse.controller;

import com.jfoenix.controls.JFXComboBox;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import lk.ijse.BO.BOFactory;
import lk.ijse.BO.custom.PaymentBo;
import javafx.scene.input.KeyEvent;
import lk.ijse.BO.custom.StudentProgramBo;
import lk.ijse.Entity.Student;
import lk.ijse.Entity.Student_Program;
import lk.ijse.dto.PaymentDTO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class PaymentInfoFormController {

    @FXML
    private JFXComboBox<String> cmbPayPlan;

    @FXML
    private JFXComboBox<String> comBoxType;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblOrderNo;

    @FXML
    private Label lblPaymentNo;

    @FXML
    private Label lblTotalAmount;

    @FXML
    private TextField txtDiscount;

    @FXML
    private TextField txtPayAmount;

    PaymentBo paymentBo = (PaymentBo) BOFactory.getBoFactory().GetBo(BOFactory.BOType.PAYMENT) ;
    StudentProgramBo studentProgramBo = (StudentProgramBo) BOFactory.getBoFactory().GetBo(BOFactory.BOType.STUDENT_PROGRAM) ;

    public void initialize() {
        lblOrderNo.setText(StudentProgramPageController.staticRegNo);
        lblTotalAmount.setText(StudentProgramPageController.staticFee);
        generatePaymentNo();
        LocalDate now = LocalDate.now();
        lblDate.setText(String.valueOf(now));
        setDataToCmbPaymentPlan();
        setDataToCmbPaymentType();

    }

    private void setDataToCmbPaymentType() {
        ObservableList<String> obList = FXCollections.observableArrayList(
                "Cash",
                "Credit Card"

        );

        comBoxType.setItems(obList);
    }

    private void setDataToCmbPaymentPlan() {
        ObservableList<String> obList = FXCollections.observableArrayList(
                "1 Installment",
                "2 Installment",
                "3 Installment"

        );

        cmbPayPlan.setItems(obList);

    }

    private void generatePaymentNo() {
        try {
            String currentId = paymentBo.getCurrentId();


            lblPaymentNo.setText(currentId);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    @FXML
    void btnOnActionPayNow(ActionEvent event) throws SQLException, ClassNotFoundException {
        String PaymentId = lblPaymentNo.getText();
        String PaymentDate = lblDate.getText();
        String payType = comBoxType.getValue();
        double payAmount = Double.parseDouble(txtPayAmount.getText());
        double totalAmount = Double.parseDouble(lblTotalAmount.getText());
        double amountToBePay = totalAmount - payAmount;
        String payPlan = cmbPayPlan.getValue();
        String status;
        if (totalAmount== payAmount){
            status="Complete";
        }else {
            status="pending";
        }

        Student_Program studentProgram = studentProgramBo.SearchById(StudentProgramPageController.staticRegNo);
        Student student = StudentProgramPageController.staticStudent;

        PaymentDTO paymentDTO = new PaymentDTO(PaymentId,PaymentDate,payType,payAmount,amountToBePay,totalAmount,payPlan,status,studentProgram,student);

        boolean saved = paymentBo.savePayment(paymentDTO);
        if (saved){
            new Alert(Alert.AlertType.CONFIRMATION, "Student Registered!!!.").show();
        }



    }

    @FXML
    void DiscountOnKeyPressed(KeyEvent event) {
        try {

            double discount = txtDiscount.getText().isEmpty() ? 0 : Double.parseDouble(txtDiscount.getText());
            double totalAmount = Double.parseDouble(lblTotalAmount.getText());


            double netTotal = (totalAmount * discount)/100;


            lblNetTotal.setText(String.format("%.2f", netTotal));
        } catch (NumberFormatException e) {
            // Handle invalid input gracefully
            lblNetTotal.setText("Invalid input");
        }
    }

}
