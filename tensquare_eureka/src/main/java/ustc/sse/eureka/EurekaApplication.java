package ustc.sse.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author LRK
 * @project_name tensquare_parent
 * @package_name ustc.sse.eureka
 * @date 2019/1/24 23:14
 * @description God Bless, No Bug!
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class,args);
    }
}
