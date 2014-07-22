package leaveServlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import leaveRecordsTable.Record;
import leaveRecordsTable.RecordClass;
import leaveRequestTables.LRT;
import leaveRequestTables.LRTClass;
import employeeDetailsTable.EDT;
import employeeDetailsTable.EDTClass;

/**
 * Servlet implementation class LeaveApproval
 */
@WebServlet("/LeaveApproval")
public class LeaveApproval extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LeaveApproval() {
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
		LRT details = new LRT();
		details.setUsername(request.getParameter("name").trim());
		details.setDate(request.getParameter("date").trim());
		details.setLeaveType(request.getParameter("type").trim());
		details.setRemarks(request.getParameter("remarks").trim());
		String status=null;
		String email=null;
		if (request.getParameter("Approve") != null) {
			//send email;
			//*** update table with details to carry out the report
			status="Approved";
		}
		else if(request.getParameter("Disapprove")!=null){
			status="Disapprove";
		}
		LRTClass requestTable = new LRTClass();
		try {
			requestTable.deleteQuery(details);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		EDTClass detailsTable = new EDTClass();
		ArrayList<EDT> a1 = null;
		try {
			a1=detailsTable.getQuery("");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		EDT row = null;
		Iterator<EDT> iter = a1.iterator();
		while(iter.hasNext()){
			row=iter.next();
			if(row.getLogin().equals(details.getUsername())){
				email= row.getEmail();
				break;
			}
		}
		// here goes code to store leave details if approved
		Record record = new Record();
		RecordClass recordTable = new RecordClass();
		record.setUsername(details.getUsername());
		record.setDate(details.getDate());
		record.setLeaveType(details.getLeaveType());
		record.setRemarks(details.getRemarks());
		try {
			recordTable.insertQuery(record);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String emailMessage = "This is to inform you that your request for a "+details.getLeaveType()+" leave on "+details.getDate()+" has been "+status+".";
		//send email
		try{
			//boolean sessionDebug = false;
			String to = null;
			String d_port = "465";
			// String d_port="587";
			String host = "smtp.gmail.com";
			String from = "crazyaaslbiz@gmail.com";
			String subject = "Leave Request";

			to = email;
			Properties props = System.getProperties();
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.socketFactory.port", d_port);
			props.put("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");
			Session session5 = Session.getInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(
									"crazyaaslbiz@gmail.com", "sick2012");
						}
					});

			System.out.println("going to mail");
			Message msg = new MimeMessage(session5);
			msg.setFrom(new InternetAddress(from));
			InternetAddress[] address1 = { new InternetAddress(to) };
			msg.setRecipients(Message.RecipientType.TO, address1);
			msg.setSubject(subject);
			System.out.println("after subject...before send");
			// msg.setSentDate(new Date());
			msg.setText(emailMessage);
			System.out.println("before transport.send");
			Transport.send(msg);
			System.out.println("mailing completed");
			System.out.print("Mail sent successfully");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try{
				
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/HRLeaveApproval.jsp");
		dispatcher.forward(request, response);
		
	}

}
