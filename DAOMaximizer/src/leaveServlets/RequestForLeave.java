package leaveServlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import leaveRequestTables.LRT;
import leaveRequestTables.LRTClass;

/**
 * Servlet implementation class RequestForLeave
 */
@WebServlet("/RequestForLeave")
public class RequestForLeave extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RequestForLeave() {
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
		response.setContentType("text/html");
		String username=request.getParameter("user").trim();
		String date = request.getParameter("Date").trim();
		String type = request.getParameter("type").trim();
		String remarks = request.getParameter("remarks").trim();
		LRTClass table = new LRTClass();
		LRT leave= new LRT();
		leave.setUsername(username);
		leave.setDate(date);
		leave.setLeaveType(type);
		leave.setRemarks(remarks);
		try {
			table.insertQuery(leave);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/LeaveRequest.jsp");
		dispatcher.forward(request, response);
	}

}
