package xin.stxkfzx.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xin.stxkfzx.noshy.interceptor.UserLoginInterceptor;

/**
 * 拦截器配置
 *
 * @author fmy
 * @date 2019-03-05 17:26
 */
@SpringBootConfiguration
public class InterceptorConfig implements WebMvcConfigurer {
    private final UserLoginInterceptor userLoginInterceptor;

    @Autowired
    public InterceptorConfig(UserLoginInterceptor userLoginInterceptor) {
        this.userLoginInterceptor = userLoginInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userLoginInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/webjars/**")
                .excludePathPatterns("/swagger-*/**")
                .excludePathPatterns("/upload/**");
    }

}
