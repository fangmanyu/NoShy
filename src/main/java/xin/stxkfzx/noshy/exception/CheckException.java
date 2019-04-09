package xin.stxkfzx.noshy.exception;

/**
 * @author fmy
 * @date 2019-04-08 14:31
 */
public class CheckException extends RuntimeException {

    private static final long serialVersionUID = -7873090514780852879L;

    public CheckException() {
        super();
    }

    public CheckException(String message) {
        super(message);
    }

    public CheckException(String message, Throwable cause) {
        super(message, cause);
    }

    public CheckException(Throwable cause) {
        super(cause);
    }
}
