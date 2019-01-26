package ustc.sse.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author LRK
 * @project_name tensquare_parent
 * @package_name ustc.sse.config
 * @date 2019/1/26 17:48
 * @description God Bless, No Bug!
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class);
    }
}
