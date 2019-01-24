package ustc.sse.spit.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * @author LRK
 * @project_name tensquare_parent
 * @package_name ustc.sse.spit.pojo
 * @date 2019/1/21 15:54
 * @description God Bless, No Bug!
 */
@Setter@Getter@ToString
public class Spit implements Serializable{

    @Id
    private String _id;
    private String content;
    private Date publishtime;
    private String userid;
    private String nickname;
    private Integer visits;
    private Integer thumbup;
    private Integer share;
    private Integer comment;
    private String state;
    private String parentid;
}
