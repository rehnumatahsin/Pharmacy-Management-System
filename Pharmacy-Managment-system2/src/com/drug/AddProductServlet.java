package com.drug;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/AddProductServlet")
public class AddProductServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession httpSession = request.getSession();
        String guid=(String)httpSession.getAttribute("currentuser");

        String prname=request.getParameter("prname");
        String prid=request.getParameter("prid");
        String mfname=request.getParameter("mfname");
        String mdate=request.getParameter("mdate");
        String edate=request.getParameter("edate");
        String price1=request.getParameter("price");
        String quantity1=request.getParameter("quantity");
        int price=Integer.parseInt(price1);
        int quantity=Integer.parseInt(quantity1);

        String query1="select pid from product where pid=?";
        String query2="insert into product(pid,pname,manufacturer,mfg,exp,price) values (?,?,?,?,?,?)";
        String query3="insert into inventory(pid,pname,sid,quantity) values (?,?,?,?)";

        ResultSet rs=null;
        Connection conn=null;
        PreparedStatement ps1=null;
        PreparedStatement ps2=null;
        PreparedStatement ps3=null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/drug_base","root","");
            ps1=conn.prepareStatement(query1);
            ps1.setString(1,prid);
            rs=ps1.executeQuery();
            if(!rs.next())
            {
                ps2=conn.prepareStatement(query2);
                ps2.setString(1,prid);
                ps2.setString(2,prname);
                ps2.setString(3,mfname);
                ps2.setString(4,mdate);
                ps2.setString(5,edate);
                ps2.setInt(6,price);
                int i=ps2.executeUpdate();
                ps3=conn.prepareStatement(query3);
                ps3.setString(1,prid);
                ps3.setString(2,prname);
                ps3.setString(3,guid);
                ps3.setInt(4,quantity);
                int j=ps3.executeUpdate();
                response.sendRedirect("AddInventory.jsp");
            }
            else
            {response.sendRedirect("AddProductError.html");
            }
        }
        catch(Exception e){
        e.printStackTrace();
        }
        finally{
        try{
        if(ps1!=null) ps1.close();
        if(ps2!=null) ps2.close();
        if(ps3!=null) ps3.close();
        if(conn!=null) conn.close();
        }
        catch(Exception e){
        e.printStackTrace();
        }
        }
        }
        }
