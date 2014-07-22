package loginTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import DBConnection.DBConnection;

public class LoginClass implements LoginInterface {

	@Override
	public Login selectQuery(String user, String date) throws SQLException {
		Login row=null;
		Connection conn = null;
		conn = DBConnection.getConnection() ;
		String sql="SELECT * FROM SHASHANK.REGTAB WHERE USERNAME='"+user+"' AND DATE='"+date+"'";
		//System.out.println(sql);
		PreparedStatement pst = null;
		ResultSet rs=null;
		if(conn!=null){
			 pst=conn.prepareStatement(sql);
			 rs=pst.executeQuery();
			 if(rs.next()){
				 row=new Login();
				 row.setUsername(rs.getString("USERNAME"));
				 row.setDate(rs.getString("DATE"));
				 row.setLogin(rs.getString("LOGIN"));
			 }
		}
		return row;
	}

	@Override
	public void insertQuery(Login row) throws SQLException {
		Connection conn = null;
		conn = DBConnection.getConnection();
		String sql= "INSERT INTO SHASHANK.REGTAB (USERNAME,DATE,LOGIN,LOGOUT)VALUES (?,?,?,?)";
		//System.out.println(sql);
		PreparedStatement pst = null;
		System.out.println("setting the values");
		pst = conn.prepareStatement(sql);
		pst.setString(1,row.getUsername());
		pst.setString(2,row.getDate());
		pst.setString(3,row.getLogin());
		pst.setString(4,row.getLogout());
		System.out.println("Inserted");
		pst.executeUpdate();
		System.out.println("updated");
		conn.commit();
		System.out.println("committed");
		
	}

	@Override
	public void updateQuery(Login row) throws SQLException {
		Connection conn = null;
		conn = DBConnection.getConnection();
		String user=row.getUsername();
		String date=row.getDate();
		String logout = row.getLogout();
		String sql = "UPDATE SHASHANK.REGTAB SET LOGOUT='"+logout+"' WHERE USERNAME='"+user+"' AND DATE='"+date+"'";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.executeUpdate();
		conn.commit();
		
	}

	@Override
	public int getQuery(String user,String time) throws SQLException {
		Connection conn = null;
		conn = DBConnection.getConnection() ;
		String sql ="SELECT COUNT(*) FROM SHASHANK.REGTAB WHERE USERNAME='"+user+"' AND DATE LIKE '%"+time+"'";
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
