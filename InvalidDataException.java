/**
 * Exception thrown when encountering invalid data, such as a negative price or empty field.
 * @author Ayub Hunter
 * @version 1.0
 */
public class InvalidDataException extends RuntimeException {

    /**
     * Constructor that takes a message and passes it to the superclass constructor.
     *
     * @param message the detail message for this exception.
     */
    public InvalidDataException(String message) {
        super(message);
    }

    /**
     * Constructor with no arguments that calls the superclass constructor.
     */
    public InvalidDataException() {
        super();
    }
}
