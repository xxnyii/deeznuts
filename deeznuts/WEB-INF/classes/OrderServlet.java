// To save as "ebookshop\WEB-INF\classes\QueryServlet.java".
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;            // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
//import javax.servlet.*;            // Tomcat 9
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;

@WebServlet("/order")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class OrderServlet extends HttpServlet {

   // The doGet() runs once per HTTP GET request to this servlet.
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
      // Set the MIME type for the response message
      response.setContentType("text/html");
      // Get a output writer to write the response message into the network socket
      PrintWriter out = response.getWriter();
      // Print an HTML page as the output of the query
      //out.println("<html>");
      //out.println("<head><title>Query Response</title><link rel='stylesheet' type='text/css' href='style.css'></link></head>");
      //out.println("<body>");
      out.println("<html><head><meta charset=\"UTF-8\"><title>DEEZNUTS</title><link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\"></head>");
      out.println("<body><header style='background-color: #B5BEA9'><nav class=\"navbar\"><ul class=\"left_nav\"><li><a class=\"navbar_brand\" href=\"/deeznuts/order.html\">DEEZNUTS</a></li><li><a href=\"/deeznuts/querybookmvp.html\">Query</a></li></ul><ul class=\"right_nav\"><li><a href=\"/deeznuts/signup.html\">Sign Up</a></li><li><a href=\"/deeznuts/login.html\">Log In</a></li></ul></nav></header>");

      try (
         // Step 1: Allocate a database 'Connection' object
         Connection conn = DriverManager.getConnection(
               "jdbc:mysql://localhost:3306/deeznuts?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
               "myuser", "xxxx");   // For MySQL
               // The format is: "jdbc:mysql://hostname:port/databaseName", "username", "password"

         // Step 2: Allocate a 'Statement' object in the Connection
         Statement stmt = conn.createStatement();
      ) {
          // Step 3: Execute a SQL SELECT query
        ResultSet rs = stmt.executeQuery("select * from nuts");  
        // out.println("<table border=1 width=90% height=50>");  
        // out.println("<tr><th>Number of packets</th><th>Name</th><th>Country of Origin</th><th>Price per 100g ($)</th><th>Quantity left</th><tr>");
        // out.println("<form method=\"get\" action=\"confirmation\">");  
        // while (rs.next()) 
        // {  
        //     String n = rs.getString("name");  
        //     String c = rs.getString("country");
        //     float p = rs.getFloat("price");
        //     int q = rs.getInt("qty"); 
        //     out.println("<tr><td><input type='hidden' name='productname' value="+n+"><input type='number' name='qtyorder' min='0' max= '500' value = '0' /></td><td>" + n + "</td><td>" + c + "</td><td>" + p + "</td><td>" + q + "</td></tr>" );   
        // }  
        // out.println("</table>");  

        // out.println("<p>Enter your Name: <input type='text' name='cust_name' /></p>"
        //        + "<p>Enter your Email: <input type='text' name='cust_email' /></p>"
        //        + "<p>Enter your Phone Number: <input type='text' name='cust_phone' /></p>");

        // out.println("</body>");
        // //out.print("<br><button type=\"button\">Shop</button>");
        // out.println("<br><input type=\"submit\" value=\"Order\">");
        // out.println("</form>");

        out.println("<main><form method='get' action='confirmation'>");
        out.println("<section id='bootstrap_features'>");
         out.println("<section id='features_articles'>");

         while(rs.next()) {
            out.println("<article><img src='"+ rs.getString("img_src") +"' height='290' alt='"+rs.getString("name")+"'>");
            out.println("<h3>"+rs.getString("name")+"</h3>");
            //out.println("<a class='bs_button' style='color: #59584E;' href='#'>Add to Cart</a>");
            out.println("<input type='hidden' name='productname' value='"+rs.getString("name")+"'/>");
            out.println("<input class='countinput' type='number' name='qtyorder' value='0' min='0' max='99'/>");
            out.println("</article>");
         }

         
         out.println("</section>");
         out.println("<hr>");

         //out.println("<div style='margin: auto;'>");
         out.println("<p class='section_p'>Enter your Name: <input type='text' name='cust_name' /></p>");
         // out.println("<p class='section_p'>Choose username: <input type='text' name='user_name' /></p>");
         // out.println("<p class='section_p'>Choose password: <input type='text' name='user_pw' /></p>");
         out.println("<p class='section_p'>Enter your Email: <input type='text' name='cust_email' /></p>");
         out.println("<p class='section_p'>Enter your Mobile No.: <input type='text' name='cust_phone' /></p>");
         out.println("<p ><input class='bs_button' style='background-color: gray;' type='submit' value='ORDER' /></p>");
         //out.println("</div>");
         out.println("</section>");
         out.println("</form>");



        conn.close(); 

      } catch(Exception ex) {
         out.println("<p>Error: " + ex.getMessage() + "</p>");
         out.println("<p>Check Tomcat console for details.</p>");
         ex.printStackTrace();
      }  // Step 5: Close conn and stmt - Done automatically by try-with-resources (JDK 7)
 
      //out.println("</body></html>");
      out.println("</main><footer><section class='footer_nav'><ul><li><a class='footer_link' href='/deeznuts/contact.html'>Contact Us</a></li></ul></section><p>go nuts</p></footer></body></html>");
      out.close();
   }
}