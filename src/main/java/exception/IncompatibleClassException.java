package exception;


//Exception 'exception.IncompatibleClassException' is never thrown in the corresponding try block
public class IncompatibleClassException extends ClassCastException{
    private static final String message = "incompatible types -_-";

    public IncompatibleClassException() {
        super(message);
    }
}