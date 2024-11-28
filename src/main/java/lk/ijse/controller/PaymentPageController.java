package lk.ijse.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import lk.ijse.BO.BOFactory;
import lk.ijse.BO.custom.PaymentBo;
import lk.ijse.dto.PaymentDTO;
import lk.ijse.dto.ProgramDTO;
import lk.ijse.dto.StudentDTO;
import lk.ijse.tdm.PaymentTm;
import lk.ijse.tdm.StudentTm;

import java.sql.SQLException;
import java.util.List;

public class PaymentPageController {

    @FXML
    private Label LblPayPlan;

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

    PaymentBo paymentBo = (PaymentBo) BOFactory.getBoFactory().GetBo(BOFactory.BOType.PAYMENT) ;

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
                        payment.getStudent()

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

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void tblOnMouseClicked(MouseEvent event) {

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
    void txtStuSearchOnAction(ActionEvent event) {

    }

}
