package xin.stxkfzx.noshy.exception;

/**
 * 注册异常
 *
 * @author fmy
 * @date 2018-07-21 22:17
 */
public class RegisterException extends RuntimeException {

    public RegisterException() {
        super();
    }

    public RegisterException(String message) {
        super(message);
    }

    public RegisterException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegisterException(Throwable cause) {
        super(cause);
    }
}
