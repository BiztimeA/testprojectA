package employeeDetailsTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DBConnection.DBConnection;

public class EDTClass implements EDTInterface {

	@Override
	public ArrayList<EDT> getQuery(String sqlcat) throws SQLException {
		Connection conn = null;
		conn = DBConnection.getConnection() ;
		String sql="SELECT * FROM SHASHANK.PROJECT1 ";	
		System.out.println(sql);
		 Statement pst = null;
		 ResultSet rs=null;
		 ArrayList<EDT> a1=new ArrayList<EDT>();
		 if(conn!=null){
			 pst=conn.createStatement();
			 rs=pst.executeQuery(sql);
			 //EDT row = new EDT();
			 while(rs.next()){
				 EDT row = new EDT();
				 row.setFname(rs.getString("FNAME"));
				 row.setLname(rs.getString("LNAME"));
				 row.setDob(rs.getString("DOB"));
				 row.setEmail(rs.getString("EMAIL"));
				 row.setPhone(rs.getString("PHONE"));
				 row.setRole(rs.getString("ROLE"));
				 row.setLogin(rs.getString("LOGIN"));
				 row.setPass(rs.getString("PASS"));
				 a1.add(row);
				 //System.out.println(row.getLogin());
			 }
			 conn.commit();
			 System.out.println("Database Connected");
		 }
		 else{
			 System.out.println("Database Connection Failed");
		 }
		 return a1;
		
	}

	@Override
	public void insertQuery(EDT row) throws SQLException {
		Connection conn = null;
		conn = DBConnection.getConnection();
		String sql= "INSERT INTO SHASHANK.PROJECT1(FNAME, LNAME,DOB,PHONE,EMAIL,ROLE,LOGIN,PASS) VALUES(?,?,?,?,?,?,?,?)";
		System.out.println(sql);
		PreparedStatement pst = null;
		System.out.println("setting the values");
		pst = conn.prepareStatement(sql);
		pst.setString(1, row.getFname());
		pst.setString(2,row.getLname());
		pst.setString(3,row.getDob());
		pst.setString(4,row.getPhone());
		pst.setString(5, row.getEmail());
		pst.setString(6, row.getRole());
		pst.setString(7, row.getLogin());
		pst.setString(8, row.getPass());
		System.out.println("Inserted");
		pst.executeUpdate();
		System.out.println("updated");
		conn.commit();
		System.out.println("committed");
		
	}

}
