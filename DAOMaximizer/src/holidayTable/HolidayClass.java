package holidayTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DBConnection.DBConnection;

public class HolidayClass implements HolidayInterface {

	@Override
	public ArrayList<Holiday> getQuery() throws SQLException {
		Connection conn = null;
		conn = DBConnection.getConnection() ;
		String sql="SELECT * FROM SHASHANK.HOLIDAYS";	
		System.out.println(sql);
		Statement pst = null;
		ResultSet rs=null;
		ArrayList<Holiday> a1=new ArrayList<Holiday>();
		if(conn!=null){
			 pst=conn.createStatement();
			 rs=pst.executeQuery(sql);
			 //EDT row = new EDT();
			 while(rs.next()){
				 Holiday row = new Holiday();
				row.setDate(rs.getString("DATE"));
				row.setRemarks(rs.getString("REMARKS"));
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
	public void insertQuery(Holiday holiday) throws SQLException {
		Connection conn = null;
		conn = DBConnection.getConnection();
		String sql= "INSERT INTO SHASHANK.HOLIDAYS(DATE,REMARKS) VALUES(?,?)";
		System.out.println(sql);
		PreparedStatement pst = null;
		System.out.println("setting the values");
		pst = conn.prepareStatement(sql);
		pst.setString(1, holiday.getDate());
		pst.setString(2, holiday.getRemarks());
		System.out.println("Inserted");
		pst.executeUpdate();
		System.out.println("updated");
		conn.commit();
		System.out.println("committed");
		
		
	}

	@Override
	public int getQuery(String time) throws SQLException {
		Connection conn = null;
		conn = DBConnection.getConnection() ;
		String sql ="SELECT COUNT(*) FROM SHASHANK.HOLIDAYS WHERE DATE LIKE '%"+time+"'";
		PreparedStatement pst = null;
		ResultSet rs=null;
		int count=0;
		if(conn!=null){
			 pst=conn.prepareStatement(sql);
			 rs=pst.executeQuery();
			 if(rs.next()){
				 count=Integer.parseInt(rs.getString(1));
			 }
		}
		return count;
	}

}
