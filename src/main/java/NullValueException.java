public class NullValueException extends Exception {
    private static final String message = "it's so sad, but null :(";

    public NullValueException() {
        super(message);
    }
}
