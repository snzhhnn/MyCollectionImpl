public class InvalidIndexException extends Exception {

    private static final String message = "index out of bounds-_-";

    public InvalidIndexException() {
        super(message);
    }
}
