package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

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
        done1.setOnAction(event -> {
            String fio = this.fio_field.getText();
            String group = this.group_field.getText();
            model.list.add(new Student(fio,group));

            int count = model.list.size();
            
            this.student_count.setText(Integer.toString(count));
        });

        create_docx.setOnAction(event -> {
            //TODO: put code in try to another thread
            try{
                WordprocessingMLPackage wordMLPackage;
                wordMLPackage = WordprocessingMLPackage.createPackage();
                for (int i = 0; i < model.list.size(); i ++){
                    
                }
                for (Student s:model.list) {
                    wordMLPackage.getMainDocumentPart().addParagraphOfText("Фамилия: " + s.getFio() + " Группа: " + s.getGroup() + "\n");
                }
                wordMLPackage.save(new java.io.File(System.getProperty("user.home") + "/test.docx"));
            } catch (Exception e){

            }
        });

//        done3.setOnAction(event -> {
//            String fio = this.fio_forWork_field.getText();
//            String task = this.task_field.getText();
//
//            for (int i = 0; i < DataModel.list.size(); ++i) {
//                if (DataModel.list.get(i).getFio() == fio )
//                    DataModel.list.get(i).setTask(task);
//                else
//                    System.out.println("error here");
//            }
//        });
    }
}
