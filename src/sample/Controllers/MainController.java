package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import sample.Helpers.DOCXWrapper;
import sample.Main;
import sample.Models.DataModel;
import sample.Database.DBWrapper;
import sample.Database.GroupModel;
import sample.Models.Student;

import java.util.*;

import java.io.IOException;

public class MainController {

    private DataModel model = new DataModel();
    private DBWrapper dbWrapper = new DBWrapper();
    private List<String> s_list = new ArrayList<>();
    private DOCXWrapper docxWrapper;
    private FXMLLoader fxmlLoader;

    private boolean[] documentParts = new boolean[3];

    public interface sendWrapper {
        void getWrapper(DBWrapper wrapper);
    }

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
    private ChoiceBox<String> getFIO_forTask;

    @FXML
    private TextArea task_field;

    @FXML
    private Button done3;

    // list 4 elements

    @FXML
    private ChoiceBox<String> getFIO_forComment;

    @FXML
    private TextArea comment_field;

    @FXML
    private Button done4;

    // list 5: create docx file button

    @FXML
    private Button create_docx;

    @FXML
    private CheckBox list_checkbox;

    @FXML
    private  void initialize() {
        updateWindowInfo();
        done1.setOnAction(event -> {

            String fio = this.fio_field.getText();
            String group = this.group_field.getText();

            //TODO: get students count from data base
            if (!fio.equals("") & !group.equals("")) {
                model.list.add(new Student(fio, group));
                dbWrapper.setField(new GroupModel(fio, group));
                updateWindowInfo();
                //((sendWrapper) fxmlLoader.getController()).getWrapper(dbWrapper);
            }
        });

        getList.setOnAction(event -> {
            try {
                fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(Main.class.getResource("Views/list.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 600, 650);
                scene.getStylesheets().add("sample/style.css");
                Stage stage = new Stage();
                stage.setTitle("List");
                stage.setScene(scene);
                ((sendWrapper) fxmlLoader.getController()).getWrapper(dbWrapper);
                ((Stage)((Node)event.getSource()).getScene().getWindow()).hide();
                stage.show();
                stage.setOnCloseRequest(e -> {
                    ((Stage)((Node)event.getSource()).getScene().getWindow()).show();
                    updateWindowInfo();
                });
            } catch (IOException e) {
            }
        });

        done2.setOnAction(event -> {

            String month = month_field.getText();
            String subject = subject_field.getText();
            String days = subjectDays_field.getText();

            if(!month.equals("") & !subject.equals("") & !days.equals("")){

                model.visitingRegisterMonth  = month;
                model.visitingRegisterSubject = subject;

                List<String> subjectDays = new ArrayList<>(Arrays.asList(days.split(",")));
                model.visitingRegisterMonthDays = subjectDays;
                documentParts[0] = true;
            }
        });

        done3.setOnAction(event -> {
            String task = task_field.getText();
            String fio = getFIO_forTask.getValue();

            if (!task.equals("") & !fio.equals("")){
                for (Student s: model.list) {
                    if (s.getFio().equals(fio))
                        s.setTask(task);
                }
            }

            documentParts[1] = true;
        });

        done4.setOnAction(event -> {
            String comment = comment_field.getText();
            String fio = getFIO_forComment.getValue();

            if (!comment.equals("") & !fio.equals("")){
                for (Student s: model.list) {
                    if (s.getFio().equals(fio))
                        s.setMasterComment(comment);
                }
            }

            documentParts[2] = true;
        });

        create_docx.setOnAction(event -> {
            //TODO: add opportunity to choose file save directory & file name
            try {
                if (!list_checkbox.isSelected() & !documentParts[0] & !documentParts[1] & !documentParts[2] ){
                    //TODO: dialog with error
                } else {
                    docxWrapper = new DOCXWrapper();

                    if (list_checkbox.isSelected()){
                        docxWrapper.addStudentList(model);
                    }

                    if (documentParts[0]){
                        docxWrapper.addAttendList(model);
                    }
                    if (documentParts[1]){
                        docxWrapper.addTaskList(model);
                    }
                    if (documentParts[2]){
                        docxWrapper.addCommentList(model);
                    }
                    docxWrapper.saveDocument(System.getProperty("user.dir") + "/List.docx");

                    for (int i = 0; i<documentParts.length; i++) documentParts[i] = false;
                }
            } catch (Exception e) {
                create_docx.setText(e.toString());
            }
        });
    }

    private void updateWindowInfo(){
        updateStudentsList();
        ObservableList<String> students_list = FXCollections.observableArrayList(s_list);
        getFIO_forTask.setItems(students_list);
        getFIO_forComment.setItems(students_list);
        student_count.setText(String.valueOf(s_list.size()));
    }

    private void updateStudentsList(){
        try{
            s_list.clear();
            model.list.clear();
            List<GroupModel> group;
            group = dbWrapper.getAllStudents();
            for (GroupModel student:group) {
                s_list.add(student.getStudentFIO());
                model.list.add(new Student(student.getStudentFIO(), student.getStudentGroupName()));
            }
        }catch (Exception r){ }
    }
}
