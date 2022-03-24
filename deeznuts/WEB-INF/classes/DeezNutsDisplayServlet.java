import java.io.*;
import java.sql.*;
import jakarta.servlet.*;            // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/shop")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class DeezNutsDisplayServlet extends HttpServlet {

   // The doGet() runs once per HTTP GET request to this servlet.
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {
      // Set the MIME type for the response message
      response.setContentType("text/html");
      // Get a output writer to write the response message into the network socket
      PrintWriter out = response.getWriter();
      // Print an HTML page as the output of the query
      out.println("<html>");
      out.println("<head><title>Query Response</title></head>");
      out.println("<body>");

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
        out.println("<table border=1 width=90% height=50%>");  
        out.println("<tr><th>ID</th><th>Name</th><th>Description</th><th>Country of Origin</th><th>Price per 100g ($)</th><tr>");  
        while (rs.next()) 
        {  
            int id = rs.getInt("id"); 
            String n = rs.getString("name");  
            String d = rs.getString("description");  
            String c = rs.getString("country");
            float p = rs.getFloat("price");
            out.println("<tr><td>" + id + "</td><td>" + n + "</td><td>" + d + "</td><td>" + c + "</td><td>" + p + "</td></tr>" );   
        }  
        out.println("</table>");  
        out.println("</html></body>"); 
        out.println("<form method=\"get\" action=\"order\">");
        //out.print("<br><button type=\"button\">Shop</button>");
        out.println("<br><input type=\"submit\" value=\"Shop\">");
        out.println("</form>");
        conn.close();  
      } catch(Exception ex) {
         out.println("<p>Error: " + ex.getMessage() + "</p>");
         out.println("<p>Check Tomcat console for details.</p>");
         ex.printStackTrace();
      }  // Step 5: Close conn and stmt - Done automatically by try-with-resources (JDK 7)
 
      out.println("</body></html>");
      out.close();
   }
}