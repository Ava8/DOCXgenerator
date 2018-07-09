package sample;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.* ;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import sample.Database.DBWrapper;
import sample.Database.GroupDAO;
import sample.Database.GroupModel;

public class Controller {

    private DataModel model = new DataModel();

    // list 1 elements

    @FXML
    private TextField fio_field;

    @FXML
    private TextField group_field;

    @FXML
    private Button done1;

    @FXML
    private Label student_count;

    // list 2 elements

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


    @FXML
    private  void initialize() {
        DBWrapper dbWrapper = new DBWrapper();

        done1.setOnAction(event -> {
            String fio = this.fio_field.getText();
            String group = this.group_field.getText();
            if (fio != null & group != null){
                model.list.add(new Student(fio,group));
                dbWrapper.setField(new GroupModel(this.fio_field.getText()));
                this.student_count.setText(Integer.toString(model.list.size()));
            }
        });

        create_docx.setOnAction(event -> {
            try {
                WordprocessingMLPackage wordMLPackage;
                wordMLPackage = WordprocessingMLPackage.createPackage();
                for (Student s:model.list) {
                    wordMLPackage.getMainDocumentPart().addParagraphOfText("ФИО: " + s.getFio() + " группа: " + s.getGroup() + "\n");
                }
                wordMLPackage.save(new java.io.File(System.getProperty("user.home") + "/test.doc"));
            } catch (Exception e){

            }
        });
    }
}
