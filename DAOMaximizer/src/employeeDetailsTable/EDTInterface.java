package employeeDetailsTable;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EDTInterface {
	ArrayList<EDT> getQuery(String sqlcat) throws SQLException;
	void insertQuery(EDT reg) throws SQLException;
}
