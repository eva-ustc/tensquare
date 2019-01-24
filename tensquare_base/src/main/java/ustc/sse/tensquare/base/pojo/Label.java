package ustc.sse.tensquare.base.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author LRK
 * @project_name tensquare_parent
 * @package_name ustc.sse.tensquare.base.pojo
 * @date 2019/1/2 14:00
 * @description God Bless, No Bug!
 */
@Setter@Getter@ToString
@Entity
@Table(name = "tb_label")
public class Label {

    @Id
    private String id;

    private String labelname; //标签名称
    private String state; // 状态
    private Long count; // 使用数量
    private Long fans; // 关注数
    private String recommend; // 是否推荐
}
