package ustc.sse.friend.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author LRK
 * @project_name tensquare_parent
 * @package_name ustc.sse.friend.pojo
 * @date 2019/1/25 17:00
 * @description God Bless, No Bug!
 */
@Entity
@Table(name = "tb_friend")
@IdClass(Friend.class)
@Setter@Getter
public class Friend implements Serializable{
    @Id
    private String userid;
    @Id
    private String friendid;

    private String islike;
}
