import com.login.AuthenticationException;
import com.login.NullValueException;
import com.login.SqlException;
import java.sql.*;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {

    Connection con;
    //
    public void init(ServletConfig config) throws ServletException {
        super.init(config); 
        try 
        {	
            Class.forName(config.getInitParameter("jdbcClassName"));
            String username = config.getInitParameter("dbUserName");
            String password = config.getInitParameter("dbPassword");
            StringBuffer url = new StringBuffer(config.getInitParameter("jdbcDriverURL")).append("://")
                .append(config.getInitParameter("dbHostName"))
                .append(":")
                .append(config.getInitParameter("dbPort"))
                .append("/")
                .append(config.getInitParameter("databaseName"));
            con = DriverManager.getConnection(url.toString(),username,password);
        } 
        catch (SQLException sqle)
        {
            System.out.println("SQLException error occured - " + sqle.getMessage());
        } 
        catch (ClassNotFoundException nfe)
        {
           System.out.println("ClassNotFoundException error occured - " + nfe.getMessage());
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
   
        try 
        {	
            ResultSet records;
            PreparedStatement pstmt;

            String username = request.getParameter("un");
            String password = request.getParameter("pw");
            
            if (con != null) 
            {
                pstmt = con.prepareStatement("SELECT*FROM USER_INFO");
                records = pstmt.executeQuery();
                
                String[][] info = TestClass.information(records);
                
                if(username.equals("") || password.equals(""))
                {
                    if(username.equals("") && password.equals(""))
                        request.setAttribute("null", "username and password");
                    else if(username.equals(""))
                        request.setAttribute("null", "username");
                    else
                        request.setAttribute("null", "password");

                    throw new NullValueException();
                }

                else
                {
                    int counter = 0;
                    int lengthOne = info.length;
                    int lengthTwo = info[0].length;
                    boolean checkerUsername = false;
                    boolean checkerPassword = false;

                    for(int i = 0; i < lengthOne; i++)
                    {
                        for(int j = 0; j < lengthTwo; j++)
                        {
                            if(TestClass.isContain(info[i][j], username))
                            {
                                checkerUsername = true;
                                counter = j;
                            }
                            else if(TestClass.isContain(info[i][j], password))
                            {
                                checkerPassword = true;
                            }          
                        }
                    }
                    
                    String role = TestClass.getRole(lengthOne, lengthTwo, counter, info);
                    HttpSession session = request.getSession();
                    
                    if(checkerUsername == true && checkerPassword == true)
                    {
                        session.setAttribute("user", username);
                        session.setAttribute("role", role);
                        
                        response.sendRedirect("ProfileServlet");
                    }
                    
                    else
                    {
                        if(checkerUsername == false && checkerPassword == false)
                            request.setAttribute("authentication", "username and password");
                        else if(checkerUsername == false)
                            request.setAttribute("authentication", "username");
                        else 
                            request.setAttribute("authentication", "password");
                        throw new AuthenticationException();
                    }                   
                }
            } 
        } 
        catch (SQLException sqle)
        {
            throw new SqlException();
        } 
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
