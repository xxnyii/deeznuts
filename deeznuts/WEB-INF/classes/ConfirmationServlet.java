// To save as "ebookshop\WEB-INF\classes\QueryServlet.java".
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;            // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
//import javax.servlet.*;            // Tomcat 9
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;

@WebServlet("/confirmation")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class ConfirmationServlet extends HttpServlet {

   // The doGet() runs once per HTTP GET request to this servlet.
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
      // Set the MIME type for the response message
      response.setContentType("text/html");
      // Get a output writer to write the response message into the network socket
      PrintWriter out = response.getWriter();
      // Print an HTML page as the output of the query
      out.println("<html><head><meta charset=\"UTF-8\"><title>DEEZNUTS</title><link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\"></head>");
      out.println("<body><header style='background-color: #B5BEA9'><nav class=\"navbar\"><ul class=\"left_nav\"><li><a class=\"navbar_brand\" href=\"/deeznuts/order.html\">DEEZNUTS</a></li><li><a href=\"/deeznuts/querybookmvp.html\">Query</a></li></ul><ul class=\"right_nav\"><li><a href=\"/deeznuts/login.html\">Log In</a></li></ul></nav></header>");

      try (
         // Step 1: Allocate a database 'Connection' object
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/deeznuts?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
               "myuser", "xxxx");   // For MySQL
               // The format is: "jdbc:mysql://hostname:port/databaseName", "username", "password"

         // Step 2: Allocate a 'Statement' object in the Connection
         Statement stmt = conn.createStatement();
      ) {
        out.println("<main><div style='padding: 150px 150px 150px 150px; text-align: center;'>");
        //out.print("<h3>Im here</h3>");
         String[] names = request.getParameterValues("productname");
         String[] qtystring = request.getParameterValues("qtyorder");
         String custname = request.getParameter("cust_name");
         String custemail = request.getParameter("cust_email");
         String custmobile = request.getParameter("cust_phone");
         //out.println("<h3>" + names + "</h3>");
         int[] qtys = new int[qtystring.length];
         for (int j=0; j < qtystring.length; j++){
            //String temp = qtystring[j];
            qtys[j] = Integer.parseInt(qtystring[j]);
         }
        //out.println("<h3>" + qtys + "</h3>");

         if (names != null) {
            String sqlStr;
            int count;
 
            // Process each of the nuts
            for (int i = 0; i < names.length; ++i) {
               // Update the qty of the table nuts
               sqlStr = "UPDATE nuts SET qty = qty -" + qtys[i] + " WHERE name = '" + names[i] + "'";
               //out.println("<p>" + sqlStr + "</p>");  // for debugging
               count = stmt.executeUpdate(sqlStr);
               //out.println("<p>" + count + " record updated.</p>");
 
               // Create a transaction record
               // sqlStr = "INSERT INTO order_records (name, qtyOrdered, custName, custEmail, custPhone) VALUES ('"
               //       + names[i] + "', " + qtys[i] + ", '" + custname + "', '" + custemail +"', '"+ custmobile+"')";
               // //out.println("<p>" + sqlStr + "</p>");  // for debugging
               // count = stmt.executeUpdate(sqlStr);
               if (qtys[i]>0) {
                sqlStr = "INSERT INTO order_records (name, qtyOrdered, custName, custEmail, custPhone) VALUES ('"
                     + names[i] + "', " + qtys[i] + ", '" + custname + "', '" + custemail +"', '"+ custmobile+"')";
               //out.println("<p>" + sqlStr + "</p>");  // for debugging
               count = stmt.executeUpdate(sqlStr);
                out.println("<h3>Your order for " + qtys[i]+ " packets of " + names[i] + " has been confirmed.</h3>");
               }
               //out.println("<p>" + count + " record inserted.</p>");
               //out.println("<h3>Your order for " + qtys[i]+ " of " + names[i] + " has been confirmed.</h3>");
            }
            out.println("<h3>Thank you.<h3>");
         } else { // No book selected
            out.println("<h3>Please go back and select your nuts...</h3>");
         }
      } catch(Exception ex) {
         out.println("<p>Error: " + ex.getMessage() + "</p>");
         out.println("<p>Check Tomcat console for details.</p>");
         ex.printStackTrace();
      }  // Step 5: Close conn and stmt - Done automatically by try-with-resources (JDK 7)
 
      out.println("</main><footer><section class='footer_nav'><ul><li><a class='footer_link' href='#'>GitHub</a></li><li><a class='footer_link' href='#'>Twitter</a></li></ul></section><p>go nuts</p></footer></body></html>");
      out.close();
   }
}