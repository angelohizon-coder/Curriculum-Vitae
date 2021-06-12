<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Triple A</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="style-signup.css">
    </head>
    <body>
        <div class="main">
            <div class="logo_bar">
                <div class="logo">
                     <a href="#">
                         <strong>
                             <!-- takes the value from the parameter name of header and display it to JSP -->
                             <header><% out.print(getServletContext().getInitParameter("HEADER")); %></header>
                         </strong>
                     </a>
                </div>
            </div>
            <div class="signup_bar">
                <div class="signup">
                    <h1>Sign-up</h1>  
                    <form action="<%= request.getContextPath() %>/SignupServlet" method="post">
                        <div class="textbox">
                            <input type="text" placeholder="Username" name="usernameSignup">
                        </div>
                        <div class="textbox">
                            <input type="password" placeholder="Password" name="passwordSignup">
                        </div>
                        <div class="textbox">
                            <input type="password" placeholder="Confirm Password" name="confirmPasswordSignup">
                        </div>
                        <div class="textbox">
                            <center><img src="<%= request.getContextPath() %>/SimpleCaptchaServlet"></center><br/>
                            <input type="text" placeholder="Captcha" name="captchaSignup">
                        </div>
                        <input class="btn" type="submit" value="Sign-up" name="submitButton">
                    </form>
                    <center><h4>Already have an account? <a href="<%= request.getContextPath() %>/index.jsp">Click Here</a></center>
                </div>        
            </div>   
            <div class="signup_footer">
                <!-- takes the value from the parameter name of footer and display it to JSP -->
                <footer><h4><% out.print(getServletContext().getInitParameter("FOOTER")); %></h4></footer>
            </div>
        </div>
    </body>
</html>
