package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import sample.Main;
import sample.Models.DataModel;
import sample.Database.DBWrapper;
import sample.Database.GroupModel;
import sample.Models.Student;

import java.io.IOException;

public class MainController {

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

    @FXML
    private Button getList;

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
    private ChoiceBox<?> getFIO_forTask;

    @FXML
    private TextArea task_field;

    @FXML
    private Button done3;

    // list 4 elements

    @FXML
    private ChoiceBox<?> getFIO_forComment;

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
            //TODO: get students count from data base
            String fio = this.fio_field.getText();
            String group = this.group_field.getText();

            if (fio != null & group != null){
                model.list.add(new Student(fio,group));
                dbWrapper.setField(new GroupModel(fio, group));
                this.student_count.setText(Integer.toString(model.list.size()));
            }
        });

        create_docx.setOnAction(event -> {
            //TODO: add opportunity to choose file save directory & file name
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

        getList.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(Main.class.getResource("Views/list.fxml"));

                Scene scene = new Scene(fxmlLoader.load(), 600, 650);
                Stage stage = new Stage();
                stage.setTitle("List");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) { }
        });

    }

}
