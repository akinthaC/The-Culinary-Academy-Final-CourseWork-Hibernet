package lk.ijse.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
public static boolean isTextFieldValid(TextField textField, String text){
    String filed = "";

    switch (textField){
        case ID:
            filed = "^([A-Z][0-9]{3})$";
            break;
        case NAME:
            filed = "^[A-z|\\s]{3,}$";
            break;
        case ADDRESS:
            filed = "^([A-z0-9]|[-/,.@+]|\\s){4,}$";
            break;
        case CONTACT:
            filed = "\\d{10}";
            break;
        case SALARY:
            filed = "^\\d+(\\.\\d{1,2})?$";
            break;
        case PRICE:
            filed = "^\\d+(\\.\\d{1,2})?$";
            break;
        case QTY:
            filed = "^[1-9]\\d*$";
            break;
        case EMAIL:
            filed = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
            break;
        case PASSWORD:
            filed ="^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{4,}$";
            break;
        case AMOUNT:
            filed ="^\\d+(\\.\\d{1,2}.)?$";
            break;
        case DATE:
            filed ="^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$";
            break;
    }
    Pattern pattern = Pattern.compile(filed);
    if (text != null){
        if (text.trim().isEmpty()){
            return false;
        }
    }else {
        return false;
    }

    Matcher matcher = pattern.matcher(text);

    if (matcher.matches()){
        return true;
    }
    return false;
}

    public static boolean setTextColor(TextField location, javafx.scene.control.TextField textField){
        if (Regex.isTextFieldValid(location, textField.getText())){
            textField.setStyle("-fx-border-color: green;");
            textField.setStyle("-fx-border-color: green;");
            return true;
        } else {
            textField.setStyle("-fx-border-color: red;");
            textField.setStyle("-fx-border-color: red;");
            return false;
        }
    }
}
