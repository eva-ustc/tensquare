package entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author LRK
 * @project_name tensquare_parent
 * @package_name entity
 * @date 2019/1/1 21:44
 * @description God Bless, No Bug!
 */
@Setter@Getter@ToString
public class Result {

    private boolean flag;//是否成功
    private Integer code;//返回码
    private String message;//返回信息
    private Object data;//返回数据

    public Result() {
    }

    public Result(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public Result(boolean flag, Integer code, String message, Object data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
