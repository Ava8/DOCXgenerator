package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import sample.Main;
import sample.Models.DataModel;
import sample.Database.DBWrapper;
import sample.Database.GroupModel;
import sample.Models.Student;
import java.util.*;

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
    private ChoiceBox<Student> getFIO_forTask;

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

        // TODO: set list of students to choice box
//        examples of non working code to set string value in choice box
//        ObservableList<Student> students_list = FXCollections.observableList(model.list);
//        getFIO_forTask.setItems(students_list);



        done1.setOnAction(event -> {
            String fio = this.fio_field.getText();
            String group = this.group_field.getText();

            //TODO: get students count from data base
            if (fio != null & group != null) {
                model.list.add(new Student(fio, group));
                dbWrapper.setField(new GroupModel(fio, group));
                this.student_count.setText(Integer.toString(model.list.size()));
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
            } catch (IOException e) {
            }
        });

        done2.setOnAction(event -> {
            String month = month_field.getText();
            String subject = subject_field.getText();
            String days = subjectDays_field.getText();

            model.visitingRegisterMonth  = month;
            model.visitingRegisterSubject = subject;

            List<String> subjectDays = new ArrayList<>(Arrays.asList(days.split(",")));
            model.visitingRegisterMonthDays = subjectDays;
        });

        done3.setOnAction(event -> {
            // TODO: make it work
//            String task = task_field.getText();
//            // String value = (String) choiceBox.getValue();
//
//            for (Student s: model.list) {
//                if (s.getFio().equals(/*selected choice box item: value*/))
//                    s.setTask(task);
//            }
        });

        // TODO: button "done4" same as "done3", make it work too

        create_docx.setOnAction(event -> {
            //TODO: add opportunity to choose file save directory & file name
            try {
                WordprocessingMLPackage wordMLPackage;
                wordMLPackage = WordprocessingMLPackage.createPackage();
                for (Student s : model.list) {
                    wordMLPackage.getMainDocumentPart().addParagraphOfText("ФИО: " + s.getFio() + " группа: " + s.getGroup() + "\n");
                }
                wordMLPackage.save(new java.io.File(System.getProperty("user.home") + "/test.doc"));
            } catch (Exception e) {

            }
        });

    }

}
