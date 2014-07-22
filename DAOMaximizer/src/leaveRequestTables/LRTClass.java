package leaveRequestTables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DBConnection.DBConnection;

public class LRTClass implements LRTInterface {

	@Override
	public ArrayList<LRT> getQuery() throws SQLException {
		Connection conn = null;
		conn = DBConnection.getConnection() ;
		String sql="SELECT * FROM SHASHANK.LEAVEREQUEST";	
		System.out.println(sql);
		Statement pst = null;
		ResultSet rs=null;
		ArrayList<LRT> a1=new ArrayList<LRT>();
		if(conn!=null){
			 pst=conn.createStatement();
			 rs=pst.executeQuery(sql);
			 //EDT row = new EDT();
			 while(rs.next()){
				 LRT row = new LRT();
				row.setUsername(rs.getString("USERNAME"));
				row.setDate(rs.getString("DATE"));
				row.setLeaveType(rs.getString("LEAVETYPE"));
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
	public void insertQuery(LRT leave) throws SQLException {
		Connection conn = null;
		conn = DBConnection.getConnection();
		String sql= "INSERT INTO SHASHANK.LEAVEREQUEST(USERNAME,DATE,LEAVETYPE,REMARKS) VALUES(?,?,?,?)";
		System.out.println(sql);
		PreparedStatement pst = null;
		System.out.println("setting the values");
		pst = conn.prepareStatement(sql);
		pst.setString(1, leave.getUsername());
		pst.setString(2, leave.getDate());
		pst.setString(3, leave.getLeaveType());
		pst.setString(4, leave.getRemarks());
		System.out.println("Inserted");
		pst.executeUpdate();
		System.out.println("updated");
		conn.commit();
		System.out.println("committed");
		
	}

	@Override
	public void deleteQuery(LRT leave) throws SQLException {
		Connection conn = null;
		conn = DBConnection.getConnection();
		String query = "DELETE FROM SHASHANK.LEAVEREQUEST WHERE USERNAME='"+leave.getUsername()+"' AND DATE='"+leave.getDate()+"'";
		System.out.println(query);
		PreparedStatement pst = conn.prepareStatement(query);
		pst.executeUpdate();
		conn.commit();
		
	}

}
