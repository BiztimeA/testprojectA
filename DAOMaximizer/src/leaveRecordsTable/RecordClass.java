package leaveRecordsTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DBConnection.DBConnection;

public class RecordClass implements RecordInterface{

	@Override
	public ArrayList<Record> getQuery() throws SQLException {
		Connection conn = null;
		conn = DBConnection.getConnection() ;
		String sql="SELECT * FROM SHASHANK.LEAVERECORD";	
		System.out.println(sql);
		Statement pst = null;
		ResultSet rs=null;
		ArrayList<Record> a1=new ArrayList<Record>();
		if(conn!=null){
			 pst=conn.createStatement();
			 rs=pst.executeQuery(sql);
			 //EDT row = new EDT();
			 while(rs.next()){
				 Record row = new Record();
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
	public void insertQuery(Record rec) throws SQLException {
		Connection conn = null;
		conn = DBConnection.getConnection();
		String sql= "INSERT INTO SHASHANK.LEAVERECORD(USERNAME,DATE,LEAVETYPE,REMARKS) VALUES(?,?,?,?)";
		System.out.println(sql);
		PreparedStatement pst = null;
		System.out.println("setting the values");
		pst = conn.prepareStatement(sql);
		pst.setString(1, rec.getUsername());
		pst.setString(2, rec.getDate());
		pst.setString(3, rec.getLeaveType());
		pst.setString(4, rec.getRemarks());
		System.out.println("Inserted");
		pst.executeUpdate();
		System.out.println("updated");
		conn.commit();
		System.out.println("committed");
		
	}

	@Override
	public int getQuery(String user, String time) throws SQLException {
		Connection conn = null;
		conn = DBConnection.getConnection() ;
		String sql ="SELECT COUNT(*) FROM SHASHANK.LEAVERECORD WHERE USERNAME='"+user+"' AND DATE LIKE '%"+time+"'";
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
