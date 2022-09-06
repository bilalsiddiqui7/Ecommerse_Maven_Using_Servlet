package com.ecommerse.ecommerse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Registration
 */
@WebServlet("/registration")
public class Registration extends HttpServlet {
	public static String vowelsChecker(String str) {
		String r = "";
		for (int i = 0; i < str.length(); i++) {
			if ((str.charAt(i) == 'A') || (str.charAt(i) == 'E') || (str.charAt(i) == 'I') || (str.charAt(i) == 'O')
					|| (str.charAt(i) == 'U')) {
				r = r + str.charAt(i);
			}
		}
		return r;
	}

	public static int sumOfIntegers(String str) {
		int sum = 0;
		int len = str.length();
		for (int i = 0; i < str.length(); i++) {
			if (Character.isDigit(str.charAt(i))) {
				sum = sum + Character.getNumericValue(str.charAt(i));
			}
		}
		return sum;
	}

	public static String userNameMaker(String input) {
		String res = input.substring(input.length() - 4);
		return res;
	}

	public static String passwordMaker(String firstName, String lastName, String mobileNum, String aadharNum,
			String dob) {
		String res = "";
		char firstChar = lastName.charAt(0);
		String s1 = "" + firstChar;
		char lastChar = firstName.charAt(firstName.length() - 1);
		String s2 = "" + lastChar;
		char a = mobileNum.charAt(0);
		char b = mobileNum.charAt(2);
		char c = mobileNum.charAt(4);
		char d = mobileNum.charAt(6);
		char e = mobileNum.charAt(8);
		String mobileOdd = new StringBuilder().append(a).append(b).append(c).append(d).append(e).toString();
		String vowels = vowelsChecker(aadharNum);
		int sumOfaadhar = sumOfIntegers(aadharNum) % 9;
		int sumOfDob = sumOfIntegers(dob) % 9;
		res = s1.toUpperCase() + s2.toUpperCase() + mobileOdd + vowels + Integer.toString(sumOfaadhar)
				+ Integer.toString(sumOfDob);

		return res;
	}

	public static String removeSpecialCharacterFromDate(String input) {
		String str;
		str = input.replaceAll("[^a-zA-Z0-9]", "");
		return str;
	}

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Registration() {
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
		PrintWriter out = response.getWriter();

		String username = request.getParameter("name");
		String password = request.getParameter("password");
		boolean isLogin = false;

		String url = "jdbc:mysql://localhost/aliensdata";
		String uname = "root";
		String pass = "root";
		Connection con;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(url, uname, pass);

			String firstName = request.getParameter("fname");
			String lastName = request.getParameter("lname");
			String mobileNum = request.getParameter("mobileNo");
			String aadharNum = request.getParameter("aadharNo");
			String dateOfBirth = removeSpecialCharacterFromDate(request.getParameter("dateOfBirth"));
//			System.out.println("DOB-> " + dateOfBirth);

			String lastFourDigitsOfAadhar = userNameMaker(aadharNum);
			String newuName = firstName + lastFourDigitsOfAadhar;
			String newPass = passwordMaker(firstName, lastName, mobileNum, aadharNum, dateOfBirth);

			PreparedStatement ps = con.prepareStatement("insert into loginDetails values(?,?,?,?,?,?,?)");
			ps.setString(1, firstName);
			ps.setString(2, lastName);
			ps.setString(3, mobileNum);
			ps.setString(4, aadharNum);
			ps.setString(5, dateOfBirth);
			ps.setString(6, newuName);
			ps.setString(7, newPass);
			int c = ps.executeUpdate();
//			HttpSession session=request.getSession();
//			session.setAttribute("newUname", newuName);
//			session.setAttribute("newPass", newPass);
//			response.sendRedirect("NewUserCredentials.html");
			response.setContentType("text/html");
			out.println("<h2>Congratulations, your registration is successful</h2>");
			out.println("<h2>Your credentials are</h2>");
			out.println("<h2>Uername  </h2>" + newuName);
			out.println("<h2>Password  </h2>" + newPass);
			out.println("<a href=\"http://localhost:8080/ecommerse/login.html\" >You can LogIn now</a>");
			

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
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
