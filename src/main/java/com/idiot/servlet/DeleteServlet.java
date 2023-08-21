package com.idiot.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/deleteurl")
public class DeleteServlet extends HttpServlet {
	private static final String query="DELETE FROM bookData where ID=?";
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//Get Printwriter
		PrintWriter pw=res.getWriter();
		//set content type
		res.setContentType("text/html");
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
			ps.setInt(1, id);
			int count=ps.executeUpdate();
			if(count==1) {
				pw.println("<h2>Record is Deleted successully</h2>");
			}
			else {
				pw.println("<h2>Record is not Deleted</h2>");
			}
		}catch(SQLException es) {
			es.printStackTrace();
			pw.println("<h1>"+es.getMessage()+"<h2>");
		}catch(Exception e) {
			e.printStackTrace();
		}
		pw.println("<a href='home.html'>Home</a>");
		pw.println("<br>");
		pw.println("<a href='bookList'>Book List</a>");
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req,res);
	}
}
