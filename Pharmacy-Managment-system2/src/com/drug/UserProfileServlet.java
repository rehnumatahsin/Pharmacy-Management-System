package com.drug;

import java.io.IOException;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
@WebServlet("/UserProfile")
public class UserProfileServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession httpSession = request.getSession();
        String guid = (String) httpSession.getAttribute("currentuser");
        ResultSet rs = null;
        PreparedStatement ps = null;
        java.sql.Connection conn = null;
        String query = "select fname,uid,address,phno,email from customer where uid=?";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/drug_base", "root", "");
            ps = conn.prepareStatement(query);
            ps.setString(1, guid);
            rs = ps.executeQuery();
            if (rs.next()) {
                request.setAttribute("fname", rs.getString("fname"));
                request.setAttribute("uid", rs.getString("uid"));
                request.setAttribute("address", rs.getString("address"));
                request.setAttribute("phno", rs.getString("phno"));
                request.setAttribute("email", rs.getString("email"));
                RequestDispatcher view = request.getRequestDispatcher("UserProfile.jsp");
                view.forward(request, response);
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
                    conn.close();
            } catch (Exception e) {
            }
            ;
        }
    }
}