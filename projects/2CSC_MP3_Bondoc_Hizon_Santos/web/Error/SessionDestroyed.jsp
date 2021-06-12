<%-- 
    Document   : error
    Created on : Sep 26, 2020, 7:35:33 PM
    Author     : Angelo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Session Destroyed</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/Error/style-session-destroyed.css">
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
            
            <div class="error_message">
                <div class="error">
                    <h1>
                        It appears you did not login properly
                    </h1>
                    <a href="<%= request.getContextPath() %>/index.jsp">
                        <input class="btn" type="button" value="Try Again?">
                    </a>
                </div>                
            </div>
            
            <div class="error_footer">
                <!-- takes the value from the parameter name of footer and display it to JSP -->
                <h4><footer><% out.print(getServletContext().getInitParameter("FOOTER")); %></footer></h4>
            </div>
        </div>    
    </body>
</html>
