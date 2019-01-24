package ustc.sse.sms.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ustc.sse.sms.common.HttpUtil;
import ustc.sse.sms.utils.SmsUtil;

import java.net.URLEncoder;
import java.util.Map;

/**
 * @author LRK
 * @project_name tensquare_parent
 * @package_name ustc.sse.sms.listener
 * @date 2019/1/23 16:27
 * @description God Bless, No Bug!
 */
@Component
@RabbitListener(queues = "sms")
public class SmsListener {

    @Autowired
    SmsUtil smsUtil;

    @Value("${aliyun.sms.template_code}")
    String template_code;
    @Value("${aliyun.sms.sign_name}")
    String sign_name;
    @Value("${miaodi.sms.operation}")
    String operation; // /industrySMS/sendSMS
    @Value("${miaodi.sms.accountSid}")
    String accountSid; // = Config.ACCOUNT_SID
    @Value("${miaodi.sms.base_url}")
    String base_url; // https://api.miaodiyun.com/20150822

    @RabbitHandler
    public void sendSms(Map<String,String> message){

        String mobile = message.get("mobile");
        System.out.println("手机号: " + mobile);
        String code = message.get("code");
        System.out.println("验证码: " + code);

        String smsContent = "【迈普科技】登录验证码："+code+"，如非本人操作，请忽略此短信。";
        String tmpSmsContent = null;
        try{
            tmpSmsContent = URLEncoder.encode(smsContent, "UTF-8");
        }catch(Exception e){

        }
        String url = base_url + operation;
        String body = "accountSid=" + accountSid + "&to=" + mobile + "&smsContent=" + tmpSmsContent
                + HttpUtil.createCommonParam();

        // 提交请求
        String result = HttpUtil.post(url, body);
        System.out.println("result:" + System.lineSeparator() + result);

        /* try {
            // 阿里云短信服务
            smsUtil.sendSms(mobile,template_code,sign_name,"{\"code\",\"code\"}");
        } catch (ClientException e) {
            e.printStackTrace();
        }*/
    }
}
