// Location of the class
package com.login;

// The class is public so that it can be used by other classes 
public class AuthenticationException extends RuntimeException
{
    // Inherits the RuntimeException which will be used to be thrown in the LoginServlet 
    // If the username and/or password is wrong
    
    public AuthenticationException()
    {
        super();
    }
}
