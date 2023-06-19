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

@WebServlet("/RestockServlet")
public class RestockServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        String guid = (String) httpSession.getAttribute("currentuser");

        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection conn = null;
        String query = "select p.pid,i.quantity,p.pname,p.manufacturer,p.mfg,p.exp,p.price from product p,inventory i where p.pid=i.pid and i.sid=?";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/drugdatabase", "root", "");
            ps = conn.prepareStatement(query);
            ps.setString(1, guid);
            rs = ps.executeQuery();

            request.setAttribute("resultSet", rs);
            request.getRequestDispatcher("/restock.jsp").forward(request, response);
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
                    conn.close();
            } catch (Exception e) {
            }
            ;
        }
    }
}
