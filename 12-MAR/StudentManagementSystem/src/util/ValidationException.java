package util;

public class ValidationException extends Exception {
    
    /**
     * Default constructor
     */
    public ValidationException() {
        super();
    }
    /**
     * Constructor with error message
     * @param message The error message
     */
    public ValidationException(String message) {
        super(message);
    }
    
    /**
     * Constructor with error message and cause
     * @param message The error message
     * @param cause The underlying cause
     */
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }
    /**
     * Constructor with cause
     * @param cause The underlying cause
     */
    public ValidationException(Throwable cause) {
        super(cause);
    }
    
}
