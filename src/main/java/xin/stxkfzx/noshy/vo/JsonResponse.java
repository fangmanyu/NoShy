package xin.stxkfzx.noshy.vo;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.StringJoiner;

/**
 * json对象封装
 *
 * @author fmy
 * @date 2018-07-20 22:11
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JsonResponse<T> {
    private boolean success;
    private String message;
    private T data;

    /**
     * 返回码
     * 成功：code = 0
     * 已知异常：code > 0
     * 未知异常：code < 0
     */
    private int code = OK;

    public static final int OK = 0;
    public static final int NO_LOGIN = 1;
    public static final int CHECK_FAIL = 2;
    public static final int UNKNOWN_EXCEPTION = -999;

    public JsonResponse() {
    }

    public JsonResponse(boolean success, String errorMsg) {
        this.success = success;
        message = errorMsg;
    }

    public JsonResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public JsonResponse(int code, String message) {
        this.message = message;
        this.code = code;
        this.success = code == OK;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", JsonResponse.class.getSimpleName() + "[", "]")
                .add("success=" + success)
                .add("message='" + message + "'")
                .add("data=" + data)
                .add("code=" + code)
                .toString();
    }
}
