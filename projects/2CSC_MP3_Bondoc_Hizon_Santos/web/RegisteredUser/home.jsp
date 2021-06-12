<%@ page import="java.util.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="javax.servlet.http.HttpSession"%>
<!DOCTYPE html>

<html>
    <!-- HEAD -->
    <head>
        <title>Triple A</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/RegisteredUser/style-home.css">
    </head>
    
    <!-- BODY -->
    <body>        
        
        <% 
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); 
            
            String user = (String)session.getAttribute("user");
            String role = (String)session.getAttribute("role");
            
            
            if(session.getAttribute("loginTest") == null)
                response.sendRedirect("/MachineProblem3/Error/SessionDestroyed.jsp");
            else if(session.getAttribute("user") == null && session.getAttribute("role") == null)
                response.sendRedirect("/MachineProblem3/index.jsp");
            
                session.setAttribute("loginTest", null);
        %>
        
        <div class="main-container" id="home">
            <!-- MENU -->
            <div class="navigation_bar">
	        <div class="logo">
                    <a href="#">
                        <strong>
                            <!-- takes the value from the parameter name of header and display it to JSP -->
                            <header><% out.print(getServletContext().getInitParameter("HEADER")); %></header>
                        </strong>
                    </a>
	        </div>

	        <div class="navigation_bar_items">
                    <ul>
                        <form action="<%= request.getContextPath() %>/LogoutServlet">
                            <b><li><input type="submit" value="logout" class="test"></li></b>
                        </form>
                    </ul>
	        </div>
	    </div>
        	
            <!-- BANNER IMAGE -->
            <div class="image">
                <div class="image_content">
                    <h1>
                        IT APPEARS YOU HAVE REACHED MY DOMAIN!
                        <br/>
                        WE WELCOME YOU TO OUR WEBPAGE,
                        <br/>
                        <span>
                            <%= user %>(<%= role %>)
                        </span>
                    </h1>
                </div>
            </div>
            
            <!-- INFO -->
            <div class="info" id="info">
                <h1 class="info_title">More Information</h1>
                <div class="info_compartments">
                <!-- LEFT SIDE -->
                    <div class="info_information">
                        <div class="about">
                            <h3 align="center">PDF Records</h3>
                            <form action="<%= request.getContextPath() %>/RecordServlet">
				<% if (role.equalsIgnoreCase("Admin")) { %>
					<input class="btn" type="submit" value="Admin Record" name="record">
					<input class="btn" type="submit" value="All Record" name="record">
				<% } else { %>    
				 	<input class="btn" type="submit" value="Guest Record" name="record">
				<% } %>
                            </form>
                            <div class="about_description">
                                <p>
                                    <br/>
                                    iText is a global leader in innovative award-winning PDF software, <br/>
                                    offering open-source and enterprise solutions that streamline the <br/>
                                    generation and consumption of documents and data. It is used by <br/>
                                    millions of users, both open source and commercial, to create <br/>
                                    digital documents for a variety of uses: invoices, credit card <br/>
                                    statements, mobile boarding passes, legal archiving, and more. <br/>
                                    <br/>
                                </p>
                            </div>
                        </div>
                    </div> 
                
                    <!-- RIGHT SIDE -->
                    <div class="info_information">
                        <div class="about">
                            <!-- Series of images for the anime section in information.jsp -->
                            <h3 align="center">About Us</h3>
                            <div class="about_image">
                                <a href="https://www.facebook.com/aewonsantos/" target="_blank">
                                    <img src="/2CSC_MP3_Bondoc_Hizon_Santos/Images/Picture_1.jpg" alt="Aaron"><br/>
                                </a>
                                <a href="https://www.facebook.com/anton.bondoc" target="_blank">
                                    <img src="/2CSC_MP3_Bondoc_Hizon_Santos/Images/Picture_2.jpg" alt="Anton"><br/>
                                </a>
                                <a href="https://www.facebook.com/xRusian/" target="_blank">
                                    <img src="/2CSC_MP3_Bondoc_Hizon_Santos/Images/Picture_3.jpg" alt="Angelo"><br/>
                                </a>
                            </div>
                            <div class="about_description">
                                <p>
                                    <!-- Short description to why I added this to information.jsp -->
                                    The Straw Hat crew, led by Monkey D. Luffy, consists of some of the most interesting characters in all of anime 
                                    and manga. The crew is filled with great characters and the author,Eiichiro Oda, has created some really interesting groups among them, the most 
                                    popular of the lot being the Monster Trio, made up of Luffy, Zoro, and Sanji. The Monster Trio attracts the best of the villains to fight the 
                                    crew and that's another great  quality that Oda has associated with them. They're more than capable of taking care of who they're fighting most 
                                    of the time and inevitably, they will always end up fighting the strongest enemies. Even though the enemies faced by the Weak Trio are 
                                    also impressive, they'll never be as menacing as those faced by the Monster Trio.
                                </p>
                            </div>
                        </div>
                    </div> 
                </div>
                <div class="home_footer">
                    <!-- takes the value from the parameter name of footer and display it to JSP -->
                    <h4><% out.print(getServletContext().getInitParameter("FOOTER")); %></h4>
                </div>
            </div>  
            
        </div>
    </body>
</html>
