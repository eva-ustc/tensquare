package ustc.sse.friend.controller;

import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ustc.sse.friend.client.UserClient;
import ustc.sse.friend.service.FriendService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LRK
 * @project_name tensquare_parent
 * @package_name ustc.sse.friend.controller
 * @date 2019/1/25 17:15
 * @description God Bless, No Bug!
 */
@RestController
@RequestMapping("/friend")
public class FriendController {

    @Autowired
    HttpServletRequest request;
    @Autowired
    FriendService friendService;

    @Autowired
    UserClient userClient;


    @RequestMapping(value = "/{friendid}",method = RequestMethod.DELETE)
    public Result remove(@PathVariable String friendid){

        Claims claims = (Claims) request.getAttribute("claims_user");
        if (claims == null) {
            return new Result(false,StatusCode.ACCESSERROR,"权限不足");
        }
        friendService.deleteFriend(claims.getId(),friendid);
        userClient.updateFansCount(friendid,-1);
        userClient.updateFollowCount(claims.getId(),-1);
        return new Result(true,StatusCode.OK,"取关成功");
    }

    @RequestMapping(value = "/like/{friendId}/{type}",method = RequestMethod.PUT)
    public Result opsForFriend(@PathVariable("friendId") String friendId, @PathVariable("type") String type){

        // 1 验证用户登录
        // String token = jwtUtil.createJWT(loginUser.getId(),loginUser.getMobile(),"user");
        Claims claims = (Claims) request.getAttribute("claims_user");
        if (claims == null){
            return new Result(false, StatusCode.ERROR,"权限不足");
        }
        // 2 操作好友关系
        if (type != null) {
            String userId = claims.getId();
            if ("1".equals(type)) {//关注
                int flag = friendService.addFriend(userId,friendId);
                if (flag == 0){
                    return new Result(false,StatusCode.ERROR,"您已关注该好友");
                }
                if (flag == 1){
                    userClient.updateFansCount(friendId,1);
                    userClient.updateFollowCount(userId,1);
                    return new Result(true,StatusCode.OK,"关注成功");
                }
            }else if ("2".equals(type)){//拉黑
                int flag = friendService.addNoFriend(userId,friendId);
                if (flag == 0){
                    return new Result(false,StatusCode.ERROR,"您已拉黑该好友");
                }
                if (flag == 1){
                    return new Result(true,StatusCode.OK,"拉黑成功");
                }
            }else {
                return new Result(false, StatusCode.ERROR,"参数异常");
            }
        }else {
            return new Result(false, StatusCode.ERROR,"参数异常");
        }
        return new Result(true,StatusCode.OK,"操作好友关系成功");
    }
}
