package xin.stxkfzx.noshy.dto;

/**
 * @author fmy
 * @date 2018-11-01 14:02
 */
public class BaseDTO {
    private Boolean success;
    private String message;

    protected BaseDTO() {
    }

    protected BaseDTO(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BaseDTO{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}
