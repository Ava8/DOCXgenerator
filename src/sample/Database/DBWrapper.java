package sample.Database;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.util.List;

public class DBWrapper {
    private ConnectionSource connectionSource;
    private GroupDAO groupDAO;

    // default wrapper constructor with initialization of db connection
    public DBWrapper(){
        try{
            String dburl = "jdbc:sqlite:" + System.getProperty("user.dir") + "/student.db";
            connectionSource = new JdbcConnectionSource(dburl);
            groupDAO = new GroupDAO(connectionSource, GroupModel.class);
            TableUtils.createTableIfNotExists(connectionSource, GroupModel.class);
            System.out.println("Connection to DB is successful");
        } catch (Exception r){
            System.out.println("ERROR (db connect) "+ r.toString());
        }
    }

    //void to add new record
    public void setField(GroupModel var){
        try{
            groupDAO.createIfNotExists(var);
        } catch (Exception r){
            System.out.println("ERROR "+ r.toString());
        }
    }

    //void to get model by its ID (if id isn't correct, returns null object)
    public GroupModel getField(int id){
        List<GroupModel> response = null;
        try{
            QueryBuilder<GroupModel, String> builder = groupDAO.queryBuilder();
            builder.where().eq("ID", id);
            response = builder.query();
        } catch (Exception r){
            System.out.println("ERROR "+ r.toString());
        }
        if (response != null) return response.get(0); else return new GroupModel();
    }

    public void deleteField(int id) throws Exception{
        DeleteBuilder<GroupModel, String> deleteBuilder = groupDAO.deleteBuilder();
        deleteBuilder.where().eq("ID", id);
        deleteBuilder.delete();
    }

    public void editField(int id, String FIO, String group) throws  Exception{
        UpdateBuilder<GroupModel, String> updateBuilder = groupDAO.updateBuilder();
        updateBuilder.where().eq("ID", id);
        updateBuilder.updateColumnValue("FIO", FIO);
        updateBuilder.updateColumnValue("GroupName", group);
        updateBuilder.update();
    }

    public List<GroupModel> getAllStudents() throws Exception{
        QueryBuilder<GroupModel, String> builder = groupDAO.queryBuilder();
        builder.selectColumns("ID");
        builder.selectColumns("FIO");
        builder.selectColumns("GroupName");
        return builder.query();
    }
}
