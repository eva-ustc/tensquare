package ustc.sse.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * @author LRK
 * @project_name tensquare_parent
 * @package_name ustc.sse.user
 * @date 2019/1/23 15:16
 * @description God Bless, No Bug!
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserApplication.class)
public class UserTest {

    @Test
    public void showMessage(){
    }

}
