package leaveRecordsTable;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RecordInterface {
	ArrayList<Record> getQuery()throws SQLException;
	void insertQuery(Record rec) throws SQLException;
	int getQuery(String user,String time)throws SQLException;
}
