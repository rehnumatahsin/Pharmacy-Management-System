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
import javax.servlet.http.HttpSession;

@WebServlet("/SellerHomepage")
public class SellerHomepage extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        String guid = (String) httpSession.getAttribute("currentuser");

        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection conn = null;
        String query = "select sname,sid,address,phno from seller where sid=?";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/drug_base", "root", "");
            ps = conn.prepareStatement(query);
            ps.setString(1, guid);
            rs = ps.executeQuery();
            if (rs.next()) {
                request.setAttribute("sname", rs.getString("sname"));
                request.setAttribute("sid", rs.getString("sid"));
                request.setAttribute("address", rs.getString("address"));
                request.setAttribute("phno", rs.getString("phno"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (Exception e) {
            }
            ;
            try {
                if (ps != null)
                    ps.close();
            } catch (Exception e) {
            }
            ;
            try {
                if (conn != null)
                    conn.close();} catch (Exception e) {
                    }
        }
        request.getRequestDispatcher("SellerHomepage.jsp").forward(request, response);
    }}
