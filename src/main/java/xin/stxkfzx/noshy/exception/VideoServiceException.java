package xin.stxkfzx.noshy.exception;

/**
 * 视频服务异常
 *
 * @author fmy
 * @date 2018-07-24 13:29
 */
public class VideoServiceException extends RuntimeException {

    public VideoServiceException() {
        super();
    }

    public VideoServiceException(String message) {
        super(message);
    }

    public VideoServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public VideoServiceException(Throwable cause) {
        super(cause);
    }
}
