package ustc.sse.article.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @author LRK
 * @project_name tensquare_parent
 * @package_name ustc.sse.article.pojo
 * @date 2019/1/21 18:12
 * @description God Bless, No Bug!
 */
@Setter@Getter@ToString
public class Comment implements Serializable {

    @Id
    private String _id;
    private String articleid;
    private String content;
    private String userid;
    private String parentid;
    private Date publishdate;
}
