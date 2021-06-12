import com.login.AuthenticationException;
import com.login.NullLoginValueException;
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
            throw new SqlException();
        } 
        catch (ClassNotFoundException nfe)
        {
            
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
            PreparedStatement pstmt = con.prepareStatement("SELECT*FROM USER_INFO");
            ResultSet records = pstmt.executeQuery();
                
            String[][] info = TestClass.information(records);
            
            String username = request.getParameter("usernameLogin");
            String password = request.getParameter("passwordLogin");
            
            int counter = 0;
            int lengthOne = info.length;
            int lengthTwo = info[0].length;
            boolean checkerUsername = false;
            boolean checkerPassword = false;

            if (con != null) 
            { 
                if(username.equals("") || password.equals(""))
                {
                    String errorMessage = "";
                    if(username.equals("") && password.equals(""))
                        errorMessage = "username and password were";
                    else if(username.equals(""))
                        errorMessage = "username was";
                    else
                        errorMessage = "password was";
                    
                    request.setAttribute("nullLogin", errorMessage);
                    throw new NullLoginValueException();
                }

                else
                {  
                    password = Security.encrypt(password);
                    
                    for(int i = 0; i < lengthOne; i++)
                    {
                        for(int j = 0; j < lengthTwo; j++)
                        {
                            if(info[i][j].equals(username))
                            {
                                checkerUsername = true;
                                counter = j;
                            }
                            else if(info[i][j].equals(password) && counter == j)
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
                            request.setAttribute("authentication", "username and password are");
                        else if(checkerUsername == false)
                            request.setAttribute("authentication", "username is");
                        else 
                            request.setAttribute("authentication", "password is");
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
