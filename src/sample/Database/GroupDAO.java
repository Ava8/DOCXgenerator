package sample.Database;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class GroupDAO extends BaseDaoImpl<GroupModel, String> {
    public GroupDAO(ConnectionSource connectionSource, Class<GroupModel> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }
}
