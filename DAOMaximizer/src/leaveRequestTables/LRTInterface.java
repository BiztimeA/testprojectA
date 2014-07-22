package leaveRequestTables;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LRTInterface {
	ArrayList<LRT> getQuery() throws SQLException;
	void insertQuery(LRT leave) throws SQLException;
	void deleteQuery(LRT leave) throws SQLException;
}
