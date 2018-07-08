package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {

    // list 1 elements

    @FXML
    private TextField fio_field;

    @FXML
    private TextField group_field;

    @FXML
    private Button done1;

    // list 2 elements

    @FXML
    private Label student_count;

    @FXML
    private TextField month_field;

    @FXML
    private TextField subject_field;

    @FXML
    private TextField subjectDays_field;

    @FXML
    private Button done2;

    // list 3 elements

    @FXML
    private TextField fio_forWork_field;

    @FXML
    private TextArea task_field;

    @FXML
    private CheckBox check_yes;

    @FXML
    private CheckBox check_no;

    @FXML
    private TextField dateForTask_field;

    @FXML
    private Button done3;

    // list 4 elements

    @FXML
    private TextField fio_ForComment_filed;

    @FXML
    private TextArea comment_field;

    @FXML
    private Button done4;

    // list 5: create docx file button

    @FXML
    private Button create_docx;
}
