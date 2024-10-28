package exception;


public class IncompatibleClassException extends ClassCastException{
    private static final String message = "incompatible types -_-";

    public IncompatibleClassException() {
        super(message);
    }
}