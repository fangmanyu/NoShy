package xin.stxkfzx.noshy.exception;

/**
 * @author fmy
 * @date 2018-09-17 19:05
 */
public class ChallengeServiceException extends RuntimeException {
    public ChallengeServiceException() {
        super();
    }

    public ChallengeServiceException(String message) {
        super(message);
    }

    public ChallengeServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChallengeServiceException(Throwable cause) {
        super(cause);
    }
}
