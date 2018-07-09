package sample.Database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "Group")
public class GroupModel {
    @DatabaseField(generatedId = true, columnName = "ID")
    private int id;

    @DatabaseField(columnName = "FIO")
    private String studentFIO;

    public int getId() {
        return id;
    }

    public String getStudentFIO() {
        return studentFIO;
    }

    public GroupModel(){

    }

    public GroupModel(String FIO){
        this.studentFIO = FIO;
    }
}
