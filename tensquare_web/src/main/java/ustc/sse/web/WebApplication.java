package ustc.sse.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author LRK
 * @project_name tensquare_parent
 * @package_name ustc.sse.web
 * @date 2019/1/26 15:20
 * @description God Bless, No Bug!
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class);
    }
}
