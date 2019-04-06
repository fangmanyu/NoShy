package xin.stxkfzx.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xin.stxkfzx.noshy.util.PathUtil;

/**
 * 静态资源配置
 *
 * @author fmy
 * @date 2018-09-03 14:00
 */
@Configuration
public class StaticResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:"+PathUtil.getImageAbsolutePath() + "/upload/");
    }
}
