package xin.stxkfzx.noshy.exception;

/**
 * @author fmy
 * @date 2018-11-01 14:20
 */
public class BrowseException extends RuntimeException {
    public BrowseException() {
        super();
    }

    public BrowseException(String message) {
        super(message);
    }

    public BrowseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BrowseException(Throwable cause) {
        super(cause);
    }
}
