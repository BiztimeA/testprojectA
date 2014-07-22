package reportServlets;

import holidayTable.HolidayClass;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import leaveRecordsTable.RecordClass;
import loginTable.LoginClass;

import employeeDetailsTable.EDT;
import employeeDetailsTable.EDTClass;

/**
 * Servlet implementation class MonthlyReport
 */
@WebServlet("/MonthlyReport")
public class MonthlyReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MonthlyReport() {
        super();
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
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession session=request.getSession(true);
		String time ="/"+request.getParameter("month").trim()+"/"+request.getParameter("year").trim();
		int month=Integer.parseInt(request.getParameter("month").trim());
		int year=Integer.parseInt(request.getParameter("year").trim());
		EDTClass employeeTable = new EDTClass();
		EDT employeeRecord = null;
		ArrayList<EDT> a1 = null;
		try {
			a1=employeeTable.getQuery(" ");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Iterator<EDT> iter = a1.iterator();
		HolidayClass holidayTable = new HolidayClass();
		int holiday=0;
		try {
			holiday=holidayTable.getQuery(time);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Calendar cal1 = new GregorianCalendar();
		cal1.set(year, month, 01);
		int total = cal1.getActualMaximum(Calendar.DAY_OF_MONTH)-holiday;
		String table = " ";
		out.println("Total Working days in the year = "+total+" <br/>");
		out.println("<table><tr><th>Name</th><th>Present</th><th>Leaves</th></tr>");
		while(iter.hasNext()){
			employeeRecord = iter.next();
			String name = employeeRecord.getFname()+" "+employeeRecord.getLname();
			String ID = employeeRecord.getLogin();
			LoginClass attendanceTable = new LoginClass();
			int present=0;
			try {
				present = attendanceTable.getQuery(ID, time);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			RecordClass leaveTable= new RecordClass();
			int leaves = 0;
			try {
				leaves=leaveTable.getQuery(ID, time);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			table+= "<tr><td>"+name+"</td><td>"+present+"</td><td>"+leaves+"</td></tr>";
		}
		out.println(table);
		session.setAttribute("total",Integer.toString(total));
		session.setAttribute("table",table);
		out.println("</table>");
		RequestDispatcher dispatcher= request.getRequestDispatcher("/MonthlyReport.jsp");
		dispatcher.forward(request, response);
		
	}

}
