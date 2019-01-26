package ustc.sse.friend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ustc.sse.friend.client.UserClient;
import ustc.sse.friend.dao.FriendDao;
import ustc.sse.friend.dao.NoFriendDao;
import ustc.sse.friend.pojo.Friend;
import ustc.sse.friend.pojo.NoFriend;

/**
 * @author LRK
 * @project_name tensquare_parent
 * @package_name ustc.sse.friend.service
 * @date 2019/1/25 17:40
 * @description God Bless, No Bug!
 */
@Service
@Transactional
public class FriendService {

    @Autowired
    FriendDao friendDao;
    @Autowired
    NoFriendDao noFriendDao;


    /**
     * 删除好友(取消关注)
     */
    public void deleteFriend(String userid,String friendid){

        friendDao.deleteFriendByUseridAndFriendid(userid,friendid);
        friendDao.updateIslike("0",friendid,userid);

        // 顺带拉黑
        // addNoFriend(userid,friendid);
    }
    /**
     * 添加喜欢(关注)
     * @param userId
     * @param friendId
     * @return
     */
    public int addFriend(String userId, String friendId) {
        // 判断user-->friend是否有数据,如果有则返回0
        Friend friend = friendDao.findByUseridAndFriendid(userId,friendId);
        if (friend != null) {
            return 0;
        }
        // 如果user-->friend没有数据,添加user-->friend 设置islike=0
        friend = new Friend();
        friend.setUserid(userId);
        friend.setFriendid(friendId);
        friend.setIslike("0");
        friendDao.save(friend);
        // 判断friend-->user是否有数据,如果有,则更新双方islike=1
        Friend friendCheck = friendDao.findByUseridAndFriendid(friendId, userId);
        if (friendCheck != null) {
           friendDao.updateIslike("1",userId,friendId);
           friendDao.updateIslike("1",friendId,userId);
        }
        return 1;
    }

    /**
     * 添加非好友(拉黑)
     * @param userId
     * @param friendId
     * @return
     */
    public int addNoFriend(String userId, String friendId) {
        NoFriend noFriend = noFriendDao.findByUseridAndFriendid(userId, friendId);
        if (noFriend != null) {
            return 0;
        }
        noFriend = new NoFriend();
        noFriend.setUserid(userId);
        noFriend.setFriendid(friendId);
        noFriendDao.save(noFriend);
        return 1;
    }
}
