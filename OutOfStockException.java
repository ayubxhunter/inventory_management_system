/**
 * Exception thrown when attempting to buy an item that is out of stock.
 * @author Ayub Hunter
 * @version 1.0
 */
public class OutOfStockException extends RuntimeException {

    /**
     * Constructor that takes a message and passes it to the superclass constructor.
     *
     * @param message the detail message for this exception.
     */
    public OutOfStockException(String message) {
        super(message);
    }

    /**
     * Constructor with no arguments that passes a default message to the superclass constructor.
     */
    public OutOfStockException() {
        super("There is no stock remaining!");
    }
}

