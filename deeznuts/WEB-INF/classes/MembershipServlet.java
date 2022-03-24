// To save as "ebookshop\WEB-INF\classes\QueryServlet.java".
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;            // Tomcat 10
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
//import javax.servlet.*;            // Tomcat 9
//import javax.servlet.http.*;
//import javax.servlet.annotation.*;

@WebServlet("/thankyou")   // Configure the request URL for this servlet (Tomcat 7/Servlet 3.0 upwards)
public class MembershipServlet extends HttpServlet {

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
      ) {
         // check email: if alr have in database then error msg "you alr have an acc. please log in"

         PreparedStatement st = conn.prepareStatement("insert into membershipdb values(?, ?, ?, ?, ?, ?)");

         st.setString(1, request.getParameter("fname"));
         st.setString(2, request.getParameter("lname"));
         st.setString(3, request.getParameter("gender"));
         st.setInt(4, Integer.valueOf(request.getParameter("phone")));
         st.setString(5, request.getParameter("email"));
         st.setString(6, request.getParameter("pwd"));

         st.executeUpdate();
         st.close();
         conn.close();

         out.println("<h3>Thank you for signing up.</h3>");
         out.println("<form method=\"get\" action=\"order\">");
         //out.print("<br><a href=\"#\"><button type=\"button\">Click here to shop</button></a>");
         out.println("<table><tr>");
         out.println("<input type=\"submit\" value=\"See what we have!\">");
         out.println("</tr></table>");

      } catch(Exception ex) {
         out.println("<p>Error: " + ex.getMessage() + "</p>");
         out.println("<p>Check Tomcat console for details.</p>");
         ex.printStackTrace();
      }  // Step 5: Close conn and stmt - Done automatically by try-with-resources (JDK 7)
 
      out.println("</body></html>");
      out.close();
   }
}