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

@WebServlet("/bookList")
public class BookListServlet extends HttpServlet {
	private static final String query="SELECT ID,BOOKNAME,BOOKEDTION,BOOKPRICE FROM bookData";
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
		
		//LOAD JDBC DRIVER
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		try (Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/book","root","Asr@12345")){
			PreparedStatement ps=con.prepareStatement(query);
			ResultSet rs=ps.executeQuery();
			pw.println("<body class=\"container-fluid\" style=\" background-image: url('https://previews.123rf.com/images/motizova/motizova1608/motizova160800266/61508802-open-book-hardback-books-on-wooden-table-education-background-back-to-school-copy-space-for-text.jpg');\" >");
			pw.println("<div id=\"includedContent\"></div>");
			pw.println("<h2 class='text-center text-white' style='margin-top:30px;background-color:Black;'>Available books in your Libarary</h2>");
			pw.println("<table class='table' style='margin: 40px;'");
			pw.println("<thead class='thead-dark table-hover'>");
			pw.println("<tr>");
			pw.println("<th>Book Id</th>");
			pw.println("<th>Book Name</th>");
			pw.println("<th>Book Edition</th>");
			pw.println("<th>Book Price</th>");
			pw.println("<th>Edit</th>");
			pw.println("<th>Delete</th>");
			pw.println("</tr>");
			pw.println("</thead>");
			pw.println("<tbody>");
			while(rs.next()) {
				pw.println("<tr>");
				pw.println("<td>"+rs.getInt(1)+"</td>");
				pw.println("<td>"+rs.getString(2)+"</td>");
				pw.println("<td>"+rs.getString(3)+"</td>");
				pw.println("<td>"+rs.getFloat(4)+"</td>");
				pw.println("<td><a href='editscreen?id="+rs.getInt(1)+"'>Edit</a></td>");
				pw.println("<td><a href='deleteurl?id="+rs.getInt(1)+"'>Delete</a></td>");
				pw.println("</tr>");
			}
			pw.println("</tbody>");
			pw.println("</table>");
			pw.println("</body>");
		}catch(SQLException es) {
			es.printStackTrace();
			pw.println("<h1>"+es.getMessage()+"<h2>");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req,res);
	}
}
