package sample.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.Database.DBWrapper;
import sample.Database.GroupModel;
import sample.Models.Student;

import java.util.List;

public class ListController implements MainController.sendWrapper {

    private DBWrapper wrapper;

    private List<GroupModel> group;

    @FXML
    private ListView<String> listView;

    @FXML
    private TextField fio_field;

    @FXML
    private TextField group_field;

    @FXML
    private Button change_button;

    @FXML
    private Button delete_button;

    @Override
    public void getWrapper(DBWrapper wrapper) {
        this.wrapper = wrapper;
        System.out.println("not null wrapper");
        configureListView();
    }

    public void initialize() {
        // TODO: add funcs for edit and delete button
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                fio_field.setText(newValue);
            }
        });
        delete_button.setOnAction(event -> {
            try{
                //System.out.println(test);
                wrapper.deleteField(group.get(listView.getSelectionModel().getSelectedIndex()).getId());
                configureListView();
            } catch (Exception e){
                System.out.println("ERROR "+ e.toString());
            }
        });
    }

    private void configureListView(){
        ObservableList<String> data = FXCollections.observableArrayList();
        data.clear();
        try {
            group = wrapper.getAllStudents();
            for (GroupModel student:group) {
                data.add(student.getStudentFIO());
            }
        } catch (Exception e){
            System.out.println("ERROR "+ e.toString());
        }
        listView.setItems(data);
    }
}
