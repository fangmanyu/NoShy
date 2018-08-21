package xin.stxkfzx.noshy.exception;

/**
 * 论坛服务异常
 *
 * @author fmy
 * @date 2018-07-28 19:21
 */
public class PostServiceException extends RuntimeException {
    public PostServiceException() {
        super();
    }

    public PostServiceException(String message) {
        super(message);
    }

    public PostServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public PostServiceException(Throwable cause) {
        super(cause);
    }


}
