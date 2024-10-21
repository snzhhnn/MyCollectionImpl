package exception;

public class IncorrectValueException extends Exception{
    private static final String message = "give me non-negative value :(";

    public IncorrectValueException() {
        super(message);
    }
}