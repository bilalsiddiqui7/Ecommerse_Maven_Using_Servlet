package com.ecommerse.ecommerse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ChangePassword
 */
@WebServlet("/changePassword")
public class ChangePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChangePassword() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String oldPass = request.getParameter("oldPasword");
		String userName = request.getParameter("userName");
		String confirmPass = request.getParameter("confirmPasword");

		PrintWriter out = response.getWriter();

		String username = request.getParameter("name");
		String password = request.getParameter("password");
		boolean isLogin = false;

		String url = "jdbc:mysql://localhost/aliensdata";
		String uname = "root";
		String pass = "root";
		String query = "select * from loginDetails";
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

			Connection con = DriverManager.getConnection(url, uname, pass);
//			if(oldPass.equals(newPass)) {
			PreparedStatement ps1 = con.prepareStatement("update loginDetails set password=? where userName=?");
			ps1.setString(1, confirmPass);
			ps1.setString(2, userName);
			int count = ps1.executeUpdate();
			response.setContentType("text/html");
			out.println("<h2>Password changed successfully</h2><br>");
			out.println("<a href=\"http://localhost:8080/ecommerse/login.html\" >LogIn</a>");
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
