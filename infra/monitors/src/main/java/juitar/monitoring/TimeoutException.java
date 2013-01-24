package juitar.monitoring;

/**
 * @author sha1n
 * Date: 1/3/13
 */
public class TimeoutException extends RuntimeException {
    public TimeoutException(String message) {
        super(message);
    }
}
