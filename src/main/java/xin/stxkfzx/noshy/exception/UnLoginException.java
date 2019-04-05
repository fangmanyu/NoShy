package xin.stxkfzx.noshy.exception;

/**
 * 用户未登录异常
 *
 * @author fmy
 * @date 2019-04-05 17:55
 */
public class UnLoginException extends RuntimeException {

    private static final long serialVersionUID = -5626560028080912399L;

    public UnLoginException() {
        super();
    }

    public UnLoginException(String message) {
        super(message);
    }

    public UnLoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnLoginException(Throwable cause) {
        super(cause);
    }

}
