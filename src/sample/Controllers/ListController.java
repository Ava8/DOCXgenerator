package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ListController {
    @FXML
    private ListView<String> listView;

    public void initialize() {
        ObservableList<String> data = FXCollections.observableArrayList(
                "Student1","Student2","Student3","Student4","Student5"
        );
        listView.setItems(data);
    }
}
