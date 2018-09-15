package xin.stxkfzx.noshy.vo;

/**
 * socket request message
 *
 * @author fmy
 * @date 2018-09-12 23:39
 */
public class RequestSocketMessage {
    private String message;

    @Override
    public String toString() {
        return "RequestSocketMessage{" +
                "message='" + message + '\'' +
                '}';
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
