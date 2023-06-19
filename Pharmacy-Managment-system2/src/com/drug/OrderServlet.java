package com.drug;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class OrderServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String gid = (String) session.getAttribute("currentuser");
        int flag = 0;
        ResultSet rs = null;
        CallableStatement cs = null;
        java.sql.Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/drugdatabase", "root", "1234");
            cs = conn.prepareCall("call getorders(?)");
            cs.setString(1, gid);
            rs = cs.executeQuery();
            request.setAttribute("rs", rs);
            request.getRequestDispatcher("/Orders.jsp").forward(request, response);
        } catch (Exception e) {
            response.getWriter().println("error: " + e);
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (Exception e) {
            }
            ;
            try {
                if (cs != null)
                    cs.close();
            } catch (Exception e) {
            }
            ;
            try {
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
            }
        }
    }
}
       