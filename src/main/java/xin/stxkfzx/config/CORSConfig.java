package xin.stxkfzx.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域资源共享(ajax实现跨域请求) 配置
 *
 * @author fmy
 * @date 2018-08-02 18:51
 */
@Configuration
public class CORSConfig {

    /**
     * 通过CorsFilter 实现全局跨域
     *
     * @param
     * @return
     * @author fmy
     * @date 2018-08-02 18:57
     */
    @Bean
    public CorsFilter corsFilter() {
        // 添加CORS配置信息
        CorsConfiguration configuration = new CorsConfiguration();
        // 放行哪些原始域
        // configuration.addAllowedOrigin("http://127.0.0.1:8080");
        configuration.addAllowedOrigin("*");
        // 是否发送cookie信息
        configuration.setAllowCredentials(true);
        // 放行哪些原始域(请求方法)
        configuration.addAllowedMethod("*");
        // 放行哪些原始域(头部信息)
        configuration.addAllowedHeader("*");

        // 添加映射路径
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", configuration);

        return new CorsFilter(corsConfigurationSource);
    }
}
