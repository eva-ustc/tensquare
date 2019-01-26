package ustc.sse.qa.client;

import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ustc.sse.qa.client.impl.LabelClientImpl;

/**
 * @author LRK
 * @project_name tensquare_parent
 * @package_name ustc.sse.qa.client
 * @date 2019/1/25 15:07
 * @description God Bless, No Bug!
 */
@FeignClient(value = "tensquare-base",fallback = LabelClientImpl.class)
public interface LabelClient {

    @RequestMapping(value = "/label/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable("id") String id);
}
