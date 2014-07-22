package registrationServlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import employeeDetailsTable.EDT;
import employeeDetailsTable.EDTClass;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
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
		String fname = request.getParameter("fname").trim();
		String lname = request.getParameter("lname").trim();
		String phone = request.getParameter("phone").trim();
		String dob = request.getParameter("dob").trim();
		String email = request.getParameter("email").trim();
		String role = request.getParameter("role").trim();
		EDTClass table = new EDTClass();
		EDT row = new EDT();
		row.setFname(fname);
		row.setLname(lname);
		row.setPhone(phone);
		row.setDob(dob);
		row.setEmail(email);
		row.setRole(role);
		row.setLogin(email);
		row.setPass(phone);
		try {
			table.insertQuery(row);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("/Register.jsp");
		dispatcher.forward(request, response);
	}

}
