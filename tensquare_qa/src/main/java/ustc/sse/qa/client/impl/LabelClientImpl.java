package ustc.sse.qa.client.impl;

import entity.Result;
import entity.StatusCode;
import org.springframework.stereotype.Component;
import ustc.sse.qa.client.LabelClient;

/**
 * @author LRK
 * @project_name tensquare_parent
 * @package_name ustc.sse.qa.client.impl
 * @date 2019/1/26 14:04
 * @description God Bless, No Bug!
 */
@Component
public class LabelClientImpl implements LabelClient {
    @Override
    public Result findById(String id) {
        // 自定义异常处理
        return new Result(false, StatusCode.ERROR,"熔断器触发了...");
    }
}
