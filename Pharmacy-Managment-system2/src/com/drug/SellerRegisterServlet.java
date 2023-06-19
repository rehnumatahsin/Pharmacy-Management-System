package com.drug;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.orm.jpa.vendor.Database;


@WebServlet("/SellerRegisterServlet")
public class SellerRegisterServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name1 = request.getParameter("name");
        String phno1 = request.getParameter("phno");
        long phno2 = Long.parseLong(phno1);
        String uid1 = request.getParameter("uid");
        String address1 = request.getParameter("address");
        String pass1 = request.getParameter("pass1");
        String pass2 = request.getParameter("pass2");

        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        Connection conn = null;
        String query1 = "SELECT sid from seller WHERE sid=?";
        String query2 = "INSERT INTO seller(sid,pass,sname,address,phno) VALUES(?,?,?,?,?)";
        ResultSet rs = null;
        try {
        	Class.forName("com.mysql.jdbc.Driver");
        	conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/drug_base","root","");
        	ps1 = conn.prepareStatement(query1);
        	ps1.setString(1, uid1);
        	rs = ps1.executeQuery();
        	if (rs.next()) {
        	response.sendRedirect("SellerRegisterError1.html");
        	} else {
        	if (pass1.equals(pass2)) {
        	ps2 = conn.prepareStatement(query2);
        	ps2.setString(1, uid1);
        	ps2.setString(2, pass1);
        	ps2.setString(3, name1);
        	ps2.setString(4, address1);
        	ps2.setLong(5, phno2);
        	int i = ps2.executeUpdate();
        	response.sendRedirect("Login.jsp");
        	} else {
        	response.sendRedirect("SellerRegisterError2.html");
        	}
        	}
        	} catch (Exception e) {
        		PrintWriter out = response.getWriter();
        		out.println("error: " + e);
        		} finally {            try {
                    if (rs != null) rs.close();
                } catch (Exception e) {
                }
                ;
                try {
                    if (ps1 != null) ps1.close();
                } catch (Exception e) {
                }
                ;
                try {
                    if (ps2 != null) ps2.close();
                } catch (Exception e) {
                }
                ;
                try {
                    if (conn != null) conn.close();
                } catch (Exception e) {
                }
            }
        }
        }
