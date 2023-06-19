package com.drug;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fname1 = request.getParameter("fname");
        String lname1 = request.getParameter("lname");
        String email1 = request.getParameter("email");
        String phno1 = request.getParameter("phno");
        String uid1 = request.getParameter("uid");
        long phno2 = Long.parseLong(phno1);
        String address1 = request.getParameter("address");
        String pass1 = request.getParameter("pass1");
        String pass2 = request.getParameter("pass2");

        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        Connection conn = null;
        ResultSet rs = null;

        String query1 = "SELECT uid from customer WHERE uid= ?";
        String query2 = "INSERT INTO customer(uid,pass,fname,lname,email,address,phno) VALUES(?,?,?,?,?,?,?)";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/drug_base", "root", "");
            ps1 = conn.prepareStatement(query1);
            ps1.setString(1, uid1);
            rs = ps1.executeQuery();
            if (rs.next()) {
                response.sendRedirect("RegisterError1.html");
            } else {
                if (pass1.equals(pass2)) {
                    ps2 = conn.prepareStatement(query2);
                    ps2.setString(1, uid1);
                    ps2.setString(2, pass1);
                    ps2.setString(3, fname1);
                    ps2.setString(4, lname1);
                    ps2.setString(5, email1);
                    ps2.setString(6, address1);
                    ps2.setLong(7, phno2);
                    int i = ps2.executeUpdate();
                    response.sendRedirect("Login.jsp");
                } else
                    response.sendRedirect("RegisterError2.html");
            }
        } catch (Exception e) {
            e.printStackTrace();
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
