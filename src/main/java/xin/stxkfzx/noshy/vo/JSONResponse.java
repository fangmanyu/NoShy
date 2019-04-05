package xin.stxkfzx.noshy.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import xin.stxkfzx.noshy.enums.ResponseCodeEnum;

/**
 * json对象封装
 *
 * @author fmy
 * @date 2018-07-20 22:11
 */
@JsonInclude(JsonInclude.Include.NON_NULL)  // 序列化时忽略值为null的字段
public class JSONResponse {
    private boolean success;

    private String message;

    private Object data;
    private ResponseCodeEnum code;

    public JSONResponse() {
    }

    public JSONResponse(boolean success, ResponseCodeEnum code) {
        this.success = success;
        this.code = code;
    }

    public JSONResponse(boolean success, String errorMsg) {
        this.success = success;
        message = errorMsg;
    }

    public JSONResponse(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "JSONResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
