/**
 * Exception thrown when the first line of a ShoppingList CSV does not contain the required magic number.
 * @author Ayub Hunter
 * @version 1.0
 */
public class InvalidFormatException extends Exception {

    /**
     * Constructor that takes a message and passes it to the superclass constructor.
     *
     * @param message the detail message for this exception.
     */
    public InvalidFormatException(String message) {
        super(message);
    }

    /**
     * Constructor with no arguments that calls the superclass constructor.
     */
    public InvalidFormatException() {
        super("Invalid format.");
    }
}
