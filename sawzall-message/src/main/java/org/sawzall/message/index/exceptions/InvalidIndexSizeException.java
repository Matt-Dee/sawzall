package org.sawzall.message.index.exceptions;

public class InvalidIndexSizeException extends Exception{

    public InvalidIndexSizeException() { super(); }
    public InvalidIndexSizeException(String message) { super(message); }
    public InvalidIndexSizeException(String message, Throwable cause) { super(message, cause); }
    public InvalidIndexSizeException(Throwable cause) { super(cause); }
}