package xin.stxkfzx.noshy.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xin.stxkfzx.noshy.vo.JSONResponse;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;

import static java.util.regex.Pattern.compile;

/**
 * 全局异常处理
 *
 * @author fmy
 * @date 2018-08-01 20:56
 */
@RestControllerAdvice
@Component
public class GlobalExceptionHandler {

    private static final Logger log = LogManager.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理单属性校验异常
     *
     * @param request
     * @param e
     * @return
     * @author fmy
     * @date 2018-08-01 21:34
     */
    @ExceptionHandler(value = ValidationException.class)
    public JSONResponse validationExceptionHandle(HttpServletRequest request, Exception e) {
        log.error("error url: " + request.getRequestURI());
        log.error(e.getMessage());

        return new JSONResponse(false, e.getLocalizedMessage());
    }

    /**
     * 处理Bean校验异常
     *
     * @param request
     * @param e
     * @return
     * @author fmy
     * @date 2018-08-01 21:37
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public JSONResponse bindExceptionHandle(HttpServletRequest request, Exception e) {
        log.error("error url: " + request.getRequestURI());
        log.error(e.getMessage());

        String errField = null;
        Matcher matcher = compile("(default\\smessage\\s\\[(\\w+)\\])").matcher(e.getMessage());
        if (matcher.find()) {
            errField = matcher.group(2);
        }
        log.error("errField: {}", errField);

        StringBuilder sb = new StringBuilder();
        MethodArgumentNotValidException bindException = (MethodArgumentNotValidException) e;
        List<ObjectError> allErrors = bindException.getBindingResult().getAllErrors();
        for (ObjectError error :
                allErrors) {
            sb.append(Optional.ofNullable(errField).orElse("")).append(error.getDefaultMessage()).append(";");
        }

        return new JSONResponse(false, sb.toString());
    }

}
