package com.idiot.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/editscreen")

public class EditScreenServlet extends HttpServlet {
	private static final String query="SELECT ID,BOOKNAME,BOOKEDTION,BOOKPRICE FROM bookData where ID=?";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//Get Printwriter
		PrintWriter pw=res.getWriter();
		//set content type
		res.setContentType("text/html");
		
		pw.println("<link rel='stylesheet' href='css/bootstrap.css'></link>");
		pw.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script>");
		pw.println("<script> \n"
				+ "    $(function(){\n"
				+ "      $(\"#includedContent\").load(\"header.html\"); \n"
				+ "    });\n"
				+ "    </script> ");
		//get the id of record
		int id=Integer.parseInt(req.getParameter("id"));
		
		//LOAD JDBC DRIVER
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		try (Connection con=DriverManager.getConnection("jdbc:mysql:///book","root","Asr@12345")){
			PreparedStatement ps=con.prepareStatement(query);
			ps.setInt(1,id);
			ResultSet rs=ps.executeQuery();
			rs.next();
			pw.println("<body class=\"container-fluid\" style=\" background-image: url('https://img.freepik.com/free-photo/open-book-wooden-table_1204-363.jpg?w=740&t=st=1692270600~exp=1692271200~hmac=92bbe8215266eb52e834b663bfd4417cfdddb79e1c83b320c5da85bb8dae2e75');\" >");
			pw.println("<div id=\"includedContent\"></div>");
			
			pw.println("<form action='editurl?id="+id+"' method='post' style='margin-top:50px'>");
			pw.println("<table class='table'align='center'>");
			pw.println("<tr>");
			pw.println("<td>Book Name</td>");
			pw.println("<td><input type='text' name='bookName' value='"+rs.getString(2)+"'></td>");
			pw.println("</tr>");
			pw.println("<tr>");
			pw.println("<td>Book Edition</td>");
			pw.println("<td><input type='text' name='bookEdition' value='"+rs.getString(3)+"'></td>");
			pw.println("</tr>");
			pw.println("<tr>");
			pw.println("<td>Book Price</td>");
			pw.println("<td><input type='text' name='bookPrice' value='"+rs.getFloat(4)+"'></td>");
			pw.println("</tr>");
			pw.println("<tr>");
			pw.println("<td><input class='btn btn-success' type='submit' value='Edit'</td>");
			pw.println("<td><input class='btn btn-danger' type='reset' value='cancel'</td>");
			pw.println("</tr>");
			pw.println("</table>");
			pw.println("</form>");	
			
		}catch(SQLException es) {
			es.printStackTrace();
			pw.println("<h1>"+es.getMessage()+"<h2>");
		}catch(Exception e) {
			e.printStackTrace();
		}
		pw.println("</body>");
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req,res);
	}
}
