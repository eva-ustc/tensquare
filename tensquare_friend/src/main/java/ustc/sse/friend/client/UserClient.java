package ustc.sse.friend.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author LRK
 * @project_name tensquare_parent
 * @package_name ustc.sse.friend.client
 * @date 2019/1/25 21:10
 * @description God Bless, No Bug!
 */
@FeignClient("tensquare-user")
public interface UserClient {

    /**
     * 更新粉丝数
     * @param userid
     * @param x
     */
    @RequestMapping(value = "/user/incfans/{userid}/{x}",method = RequestMethod.POST)
    public void updateFansCount(@PathVariable("userid") String userid, @PathVariable("x") int x);

    /**
     * 更新关注数
     * @param userid
     * @param x
     */
    @RequestMapping(value = "/user/incfollow/{userid}/{x}",method = RequestMethod.POST)
    public void updateFollowCount(@PathVariable("userid") String userid,@PathVariable("x") int x);
}
