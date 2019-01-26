package ustc.sse.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ustc.sse.user.pojo.User;
/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User>{
    /**
     * 根据手机号查询用户
     * @param mobile
     * @return
     */
    public User findByMobile(String mobile);

    @Modifying
    @Query(value = "UPDATE tb_user SET fanscount=fanscount+? WHERE id=?",nativeQuery = true)
    public void incFansCount(int x,String userid);

    @Modifying
    @Query(value = "UPDATE tb_user SET followcount=followcount+? WHERE id=?",nativeQuery = true)
    public void incFollowCount(int x,String userid);
}
