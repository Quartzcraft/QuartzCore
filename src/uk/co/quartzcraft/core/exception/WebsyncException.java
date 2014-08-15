package uk.co.quartzcraft.core.exception;

public class WebsyncException extends Exception {

    //Parameterless Constructor
    public WebsyncException() {}

    //Constructor that accepts a message
    public WebsyncException(String message)
    {
        super(message);
    }
}
