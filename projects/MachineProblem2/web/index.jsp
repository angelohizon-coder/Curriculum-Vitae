<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Triple A</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="style-index.css">
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

            <div class="login_bar">
                <div class="login">
                    <h1>Login</h1>
                    <!-- Passes the information to the LoginServlet where it requires the context path of the said Servlet -->
                    <form action="<%= request.getContextPath() %>/LoginServlet" method="post">
                        <div class="textbox">
                            <!-- textbox that requires the user's input for username -->
                            <input type="text" placeholder="Username" name="un">
                        </div>
                        <div class="textbox">
                            <!-- textbox that requires the user's input for password -->
                            <input type="password" placeholder="Password" name="pw">
                        </div>
                        <input class="btn" type="submit" value="Log-in" name="submitButton">
                    </form>
                </div>        
            </div>   
            <div class="login_footer">
                <!-- takes the value from the parameter name of footer and display it to JSP -->
                <footer><h4><% out.print(getServletContext().getInitParameter("FOOTER")); %></h4></footer>
            </div>
        </div>
    </body>
</html>
