package com.drug;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/BuyServlet")
public class BuyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Connect to the database and retrieve all products
        ArrayList<Product> products = new ArrayList<Product>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/drugdatabase", "root", "password");
            PreparedStatement ps = con.prepareStatement("SELECT product.pid, product.pname, product.manufacturer, product.mfg, product.exp, product.price, inventory.quantity FROM product, inventory WHERE product.pid=inventory.pid");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setPid(rs.getInt("pid"));
                product.setPname(rs.getString("pname"));
                product.setManufacturer(rs.getString("manufacturer"));
                product.setMfg(rs.getDate("mfg"));
                product.setExp(rs.getDate("exp"));
                product.setPrice(rs.getDouble("price"));
                product.setQuantity(rs.getInt("quantity"));
                products.add(product);
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Store the products in the request attribute and forward the request to the buy.jsp page
        request.setAttribute("products", products);
        RequestDispatcher rd = request.getRequestDispatcher("buy.jsp");
        rd.forward(request, response);
    }
}
