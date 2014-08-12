package com.infoklinik.rsvp.client;

import java.io.Serializable;

/**
 * @author muquit@muquit.com Aug 19, 2012 10:04:36 AM
 */
public class OAuthException extends Exception implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String message;
    public OAuthException()
    {
    }

    public OAuthException(String message)
    {
        super(message);
        this.message=message;
    }

    public OAuthException(Throwable cause)
    {
        super(cause);
        if (cause != null)
        {
            if (this.message == null)
            {
                this.message=cause.getMessage();
            }
        }
    }

    public OAuthException(String message,Throwable cause)
    {
        super(message,cause);
        this.message=message;
    }

    public String getMessage()
    {
        return(message);
    }
    
}
