package xin.stxkfzx.noshy.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import xin.stxkfzx.noshy.exception.UnLoginException;
import xin.stxkfzx.noshy.vo.JsonResponse;

import javax.validation.ValidationException;

/**
 * 封装Controller返回值和处理异常
 *
 * @author fmy
 * @date 2019-04-06 18:02
 */
@Component
@Aspect
public class ControllerAOP {
    private static final Logger log = LoggerFactory.getLogger(ControllerAOP.class);

    @Pointcut("execution(public xin.stxkfzx.noshy.vo.JsonResponse xin.stxkfzx.noshy.controller.*.*(..))")
    public void restfulApi() {
    }

    @Around("restfulApi()")
    public JsonResponse<?> handlerControllerMethod(ProceedingJoinPoint point) {
        JsonResponse<?> entity;

        long startTime = System.currentTimeMillis();

        try {
            entity = (JsonResponse<?>) point.proceed();
            entity.setCode(JsonResponse.OK);

            // 执行时长
            long coastTime = System.currentTimeMillis() - startTime;
            log.info("[{}] 执行时长： {}", point.getSignature(), coastTime);
        } catch (Throwable throwable) {
            entity = handlerException(throwable, point);
        }

        return entity;
    }

    private JsonResponse<?> handlerException(Throwable e, ProceedingJoinPoint point) {
        JsonResponse<?> jsonResponse = new JsonResponse<>();

        if (e instanceof UnLoginException) {
            // 未登录
            jsonResponse.setCode(JsonResponse.NO_LOGIN);
            jsonResponse.setMessage(e.getLocalizedMessage());
            log.warn("{} -- {}", point.getSignature(), e.getLocalizedMessage());
        } else if (e instanceof ValidationException || e instanceof IllegalArgumentException) {
            // Bean Validation 单属性校验异常 ValidationException
            jsonResponse.setCode(JsonResponse.CHECK_FAIL);
            jsonResponse.setMessage(e.getLocalizedMessage());
            log.warn("{} -- {}", point.getSignature(), e.getLocalizedMessage());
        } else if (e instanceof MethodArgumentNotValidException) {
            // Bean Validation Bean校验
            jsonResponse.setCode(JsonResponse.CHECK_FAIL);
            jsonResponse.setMessage(e.getLocalizedMessage());
            log.warn("{} -- {}", point.getSignature(), e.getLocalizedMessage());
        } else {
            // 未知异常处理
            log.error(point.getSignature() + " error ", e);

            jsonResponse.setCode(JsonResponse.UNKNOWN_EXCEPTION);
            jsonResponse.setMessage(e.toString());
        }

        return jsonResponse;
    }
}
