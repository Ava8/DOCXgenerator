package sample.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ListController {
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

    public void initialize() {
        ObservableList<String> data = FXCollections.observableArrayList(
                "Student1","Student2","Student3","Student4","Student5"
        );
        listView.setItems(data);

        // TODO: add funcs for edit and delete button
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                fio_field.setText(newValue);
            }
        });
    }
}
