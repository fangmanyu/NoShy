package xin.stxkfzx.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * kaptcha配置
 *
 * @author fmy
 * @date 2018-07-21 23:34
 */
@Configuration
public class kaptchaConfig {

    @Bean
    public DefaultKaptcha captchaProducer() {
        DefaultKaptcha kaptcha = new DefaultKaptcha();

        Properties properties = new Properties();
        // 是否有边框
        properties.setProperty("kaptcha.border", "no");
        // 字体颜色
        properties.setProperty("kaptcha.textproducer.font.color", "red");
        // 图片宽度
        properties.setProperty("kaptcha.image.width", "135");
        // 图片高度
        properties.setProperty("kaptcha.image.height", "50");
        // 字体大小
        properties.setProperty("kaptcha.textproducer.font.size", "43");
        // 验证码使用的字符
        properties.setProperty("kaptcha.textproducer.char.string", "abcdefghi123456789");
        // 干扰线颜色
        properties.setProperty("kaptcha.noise.color", "black");
        // 使用字符个数
        properties.setProperty("kaptcha.textproducer.char.length", "4");
        // 字体
        properties.setProperty("kaptcha.textproducer.font.name", "Arial");

        kaptcha.setConfig(new Config(properties));

        return kaptcha;
    }
}
