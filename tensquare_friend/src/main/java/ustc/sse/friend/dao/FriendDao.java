package ustc.sse.friend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ustc.sse.friend.pojo.Friend;

/**
 * @author LRK
 * @project_name tensquare_parent
 * @package_name ustc.sse.friend.dao
 * @date 2019/1/25 17:48
 * @description God Bless, No Bug!
 */
public interface FriendDao extends JpaRepository<Friend,String>{

  public Friend findByUseridAndFriendid(String userid,String friendid);

  @Modifying
  @Query(value = "UPDATE tb_friend SET islike=? WHERE userid=? AND friendid=?",nativeQuery = true)
  public void updateIslike(String islike,String userid,String friendid);

  @Modifying
  public void deleteFriendByUseridAndFriendid(String userid,String friendid);
}
