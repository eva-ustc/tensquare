package ustc.sse.spit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

/**
 * @author LRK
 * @project_name tensquare_parent
 * @package_name ustc.sse.spit
 * @date 2019/1/21 15:50
 * @description God Bless, No Bug!
 */
@SpringBootApplication
@EnableEurekaClient
public class SpitApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpitApplication.class,args);
    }

    @Bean
    public IdWorker idWorkker(){
        return new IdWorker(1, 1);
    }
}
