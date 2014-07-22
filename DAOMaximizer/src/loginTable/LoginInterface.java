package loginTable;

import java.sql.SQLException;

public interface LoginInterface {
	Login selectQuery(String user,String date)throws SQLException;
	void insertQuery(Login row)throws SQLException;
	void updateQuery(Login row)throws SQLException;
	int getQuery(String user,String time)throws SQLException;
}
