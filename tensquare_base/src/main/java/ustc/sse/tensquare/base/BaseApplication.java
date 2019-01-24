package ustc.sse.tensquare.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

/**
 * @author LRK
 * @project_name tensquare_parent
 * @package_name ustc.sse.tensquare.base
 * @date 2019/1/1 22:01
 * @description God Bless, No Bug!
 */
@SpringBootApplication
@EnableEurekaClient
public class BaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class,args);
    }

    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1,1);
    }
}
