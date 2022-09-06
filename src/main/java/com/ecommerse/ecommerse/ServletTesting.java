package com.ecommerse.ecommerse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Testing
 */
@WebServlet("/login")
public class ServletTesting extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletTesting() {
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
		Date d1 = new Date();

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

			Statement st = con.createStatement();

			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				String u = rs.getString("userName");
				String p = rs.getString("password");
				if (u.equals(username) && p.equals(password)) {
					isLogin = true;
				}
			}
			if (isLogin == true) {
				response.setContentType("text/html");
				out.println("<h2>Welcome to our Portal </h2>" + username);
				out.print("<h2>You logged in at </h2>" + d1);
				out.println(
						"<br><a href=\"http://localhost:8080/ecommerse/changePassword.html\" >Change your Password</a>");
			} else {
				response.setContentType("text/html");
				out.print("<h2>ERROR (Please enter valid credentials)</h2><br>");
				out.println("<a href=\"http://localhost:8080/ecommerse/login.html\" >Back</a><br>");
				out.println("<a href=\"http://localhost:8080/ecommerse/registration.html\" >New User</a>");
			}
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
