package ustc.sse.friend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ustc.sse.friend.pojo.Friend;
import ustc.sse.friend.pojo.NoFriend;

/**
 * @author LRK
 * @project_name tensquare_parent
 * @package_name ustc.sse.friend.dao
 * @date 2019/1/25 17:48
 * @description God Bless, No Bug!
 */
public interface NoFriendDao extends JpaRepository<NoFriend,String>{

  public NoFriend findByUseridAndFriendid(String userid, String friendid);

}
