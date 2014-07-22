package AttendanceServlets;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import loginTable.Login;
import loginTable.LoginClass;
import employeeDetailsTable.EDT;
import employeeDetailsTable.EDTClass;

/**
 * Servlet implementation class AttendanceServlet
 */
@WebServlet("/AttendanceServlet")
public class AttendanceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public AttendanceServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user= request.getParameter("user").trim();
		String pass= request.getParameter("pass").trim();
		EDTClass tableClass = new EDTClass();
		ArrayList<EDT> table = null;
		try {
			table = tableClass.getQuery(null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Iterator<EDT> iter = table.iterator();
		EDT row = null;
		int flag=0;
		String status=" ";
		while(iter.hasNext()){
			row=iter.next();
			//System.out.println(row.getLogin()+"  "+row.getPass());
			if(row.getLogin().equals(user)&&row.getPass().equals(pass)){
				System.out.println("Success");
				flag=1;
				if(request.getParameter("login") != null){
					status=login(user);
					break;
				}
		
				else if(request.getParameter("logout") != null){
					status=logout(user);
					break;
				}
			}
		}
		if(flag==0) {System.out.println("You have entered a wrong username or password");
		request.getSession().setAttribute("status", "Bad username or password");
		}
		else{
			request.getSession().setAttribute("status", status);
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/Attendance.jsp");
		dispatcher.forward(request, response);
	}
	
	String login(String user){
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		String today = dateFormat.format(date);
		String intime = timeFormat.format(date);
		LoginClass table= new LoginClass();
		Login row = null;
		try {
			row=table.selectQuery(user, today);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(row==null){
			row=new Login();
			row.setUsername(user);
			row.setDate(today);
			row.setLogin(intime);
			row.setLogout("");
			try {
				table.insertQuery(row);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return (String)"Login Successful";
		}
		else{
			System.out.println("User has already logged in");
			return (String)"Already logged in";
		}
	}
	
	String logout(String user){
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		String today = dateFormat.format(date);
		String outtime = timeFormat.format(date);
		LoginClass table= new LoginClass();
		Login row = null;
		try {
			row=table.selectQuery(user, today);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(row==null){
			System.out.println("Sorry... the user has not logged on today");
			return(String)"No login data found";
		}
		else{
			row.setLogout(outtime);
			try {
				table.updateQuery(row);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return(String)"Logout Successful";
		}
	}
}
