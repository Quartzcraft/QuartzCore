package uk.co.quartzcraft.core.exception;

public class NoValidGroupException extends Exception {

    //Parameterless Constructor
    public NoValidGroupException() {}

    //Constructor that accepts a message
    public NoValidGroupException(String message)
    {
        super(message);
    }
}
