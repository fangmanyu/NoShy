package xin.stxkfzx.config;

        import com.alibaba.druid.pool.DruidDataSource;
        import com.alibaba.druid.support.http.StatViewServlet;
        import com.alibaba.druid.support.http.WebStatFilter;
        import org.springframework.boot.context.properties.ConfigurationProperties;
        import org.springframework.boot.web.servlet.FilterRegistrationBean;
        import org.springframework.boot.web.servlet.ServletRegistrationBean;
        import org.springframework.context.annotation.Bean;
        import org.springframework.context.annotation.Configuration;

        import javax.sql.DataSource;
        import java.util.HashMap;
        import java.util.Map;

/**
 * Druid配置类
 *
 * @author fmy
 * @date 2018-07-21 7:01
 */
@Configuration
public class DruidConfig {

    /**
     * 配置数据源
     *
     * @author fmy
     * @date 2018-07-21 12:21
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource() {
        return new DruidDataSource();
    }

    /**
     * 配置druid页面的登录账号密码，及黑白名单
     *
     * @author fmy
     * @date 2018-07-21 11:53
     */
    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean<StatViewServlet> servletRegistrationBean = new ServletRegistrationBean<>();
        servletRegistrationBean.setServlet(new StatViewServlet());
        servletRegistrationBean.addUrlMappings("/druid/*");

        Map<String, String> initParameters = new HashMap<>(10);
        initParameters.put("loginUsername", "admin");
        initParameters.put("loginPassword", "admin");
        // 禁用HTML页面上的"Reset All" 功能
        initParameters.put("resetEnable", "false");
        // 设置白名单，没有或为空则允许所有访问
        initParameters.put("allow", "");
        // 设置黑名单，优先级高于白名单
        /// initParameters.put("deny", "192.168.0.1");

        servletRegistrationBean.setInitParameters(initParameters);

        return servletRegistrationBean;
    }

    /**
     * 配置druid过滤规则
     *
     * @author fmy
     * @date 2018-07-21 7:13
     */
    @Bean
    public FilterRegistrationBean<WebStatFilter> filterFilterRegistrationBean() {
        FilterRegistrationBean<WebStatFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new WebStatFilter());

        // 添加过滤规则
        filterFilterRegistrationBean.addUrlPatterns("/*");
        // 添加忽略的格式信息
        filterFilterRegistrationBean.addInitParameter("exclusions", "*.js, *.gif, *,bmp, *.png, *.css, *.ico, /druid/*");

        return filterFilterRegistrationBean;
    }
}

