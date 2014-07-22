package holidayTable;

import java.sql.SQLException;
import java.util.ArrayList;

public interface HolidayInterface {
	ArrayList<Holiday> getQuery()throws SQLException;
	void insertQuery(Holiday holiday)throws SQLException;
	int getQuery(String time)throws SQLException;
}
