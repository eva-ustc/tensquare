package entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author LRK
 * @project_name tensquare_parent
 * @package_name entity
 * @date 2019/1/1 21:50
 * @description God Bless, No Bug!
 */
@Setter@Getter
public class PageResult<T> {

    private Long total; // 总记录数
    private List<T> rows; //每页的list记录

    public PageResult(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }
}
