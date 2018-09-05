package xin.stxkfzx.noshy.exception;

/**
 * @author fmy
 * @date 2018-09-05 13:50
 */
public class CommentServiceException extends RuntimeException {
    public CommentServiceException() {
        super();
    }

    public CommentServiceException(String message) {
        super(message);
    }

    public CommentServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommentServiceException(Throwable cause) {
        super(cause);
    }
}
