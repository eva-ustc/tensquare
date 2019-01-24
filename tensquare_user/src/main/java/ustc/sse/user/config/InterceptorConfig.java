package ustc.sse.user.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import ustc.sse.user.interceptor.JwtInterceptor;

/**
 * @author LRK
 * @project_name tensquare_parent
 * @package_name ustc.sse.user.config
 * @date 2019/1/24 16:31
 * @description God Bless, No Bug!
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Autowired
    JwtInterceptor jwtInterceptor;

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(jwtInterceptor)
        .addPathPatterns("/**")
        .excludePathPatterns("/**/login/**");
    }
}
