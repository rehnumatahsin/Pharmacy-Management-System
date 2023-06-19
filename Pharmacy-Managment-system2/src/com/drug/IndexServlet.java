package com.drug;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action != null) {
            switch (action) {
                case "login":
                    response.sendRedirect("login.jsp");
                    break;
                case "register":
                    response.sendRedirect("register.jsp");
                    break;
                case "sellerregister":
                    response.sendRedirect("sellerregister.jsp");
                    break;
                default:
                    response.sendRedirect("index.jsp");
                    break;
            }
        } else {
            response.sendRedirect("index.jsp");
        }
    }
}
