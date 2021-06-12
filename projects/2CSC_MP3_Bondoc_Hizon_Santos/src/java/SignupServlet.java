import com.login.NullSignupValueException;
import com.login.SignupException;
import com.login.SqlException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import nl.captcha.Captcha;


public class SignupServlet extends HttpServlet {
    
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
            if(con != null)
            {           
                HttpSession session = request.getSession(false);
                
                PreparedStatement pstmtRecord = con.prepareStatement("SELECT*FROM USER_INFO");
                ResultSet records = pstmtRecord.executeQuery();
                String[][] info = TestClass.information(records);
                int lengthOne = info.length;
                int lengthTwo = info[0].length;
                
                PreparedStatement pstmtSignup = con.prepareStatement("INSERT INTO USER_INFO (USERNAME, PASSWORD, ROLE) VALUES(?, ?, 'GUEST')");
                String username = request.getParameter("usernameSignup");
                String password = request.getParameter("passwordSignup");
                String confirmPassword = request.getParameter("confirmPasswordSignup");
                boolean checkerUsername = true;
                
                Captcha captcha = (Captcha) session.getAttribute(Captcha.NAME);  
                request.setCharacterEncoding("UTF-8");  
                String captchaAnswer = request.getParameter("captchaSignup");  
                
                String errorMessage = "";
                
                if(username.equals("") || password.equals("") || confirmPassword.equals("") || captchaAnswer.equals(""))
                {
                    if(username.equals("") && (password.equals("") || confirmPassword.equals("")) && captchaAnswer.equals(""))
                        errorMessage = "username, password, and captcha were";
                    else if(username.equals("") && (password.equals("") || confirmPassword.equals("")))
                        errorMessage = "username and password were";
                    else if(username.equals("") && captchaAnswer.equals(""))
                        errorMessage = "username and captcha were";
                    else if((password.equals("") || confirmPassword.equals("")) && captchaAnswer.equals(""))
                        errorMessage = "password and captcha were";
                    else if(username.equals(""))
                        errorMessage = "username was";
                    else if(password.equals("") || confirmPassword.equals(""))
                        errorMessage = "password was";
                    else
                        errorMessage = "captcha was";
                    request.setAttribute("nullSignup", errorMessage);
                    throw new NullSignupValueException();
                }
                
                else
                {
                    for(int i = 0; i < lengthOne; i++)
                    {
                        for(int j = 0; j < lengthTwo; j++)
                        {
                            if(info[i][j].equals(username))
                            {
                                checkerUsername = false;
                            }
                        }
                    }
                    
                    if(checkerUsername == true && captcha.isCorrect(captchaAnswer) == true && password.equals(confirmPassword))
                    {
                        pstmtSignup.setString(1, username);
                        pstmtSignup.setString(2, Security.encrypt(password));
                        pstmtSignup.executeUpdate();
                    }
                    
                    else
                    {
                        if(checkerUsername == false && !password.equals(confirmPassword) && !captcha.isCorrect(captchaAnswer))
                            errorMessage = "a used username, mismatched password, and incorrect captcha were ";
                        else if(checkerUsername == false && !password.equals(confirmPassword))
                            errorMessage = "a used username and mismatched password were";
                        else if(checkerUsername == false && !captcha.isCorrect(captchaAnswer))
                            errorMessage = "a used username and incorrect captcha were";
                        else if(!password.equals(confirmPassword) && !captcha.isCorrect(captchaAnswer))
                            errorMessage = "mismatched password and incorrect captcha were";
                        else if(checkerUsername == false)
                            errorMessage = "a used username was ";
                        else if(!password.equals(confirmPassword))
                            errorMessage = "mismatched password was";
                        else 
                            errorMessage = "incorrect captcha was";
                        request.setAttribute("invalidSignup", errorMessage);
                        throw new SignupException();
                    }
                        
                    response.sendRedirect(request.getContextPath() + "/Sign-up/signup-success.jsp");
                }
            }
        }
        
        catch(SQLException e)
        {
            throw new SqlException();
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
