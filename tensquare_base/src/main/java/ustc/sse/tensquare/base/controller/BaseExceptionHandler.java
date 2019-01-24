package ustc.sse.tensquare.base.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author LRK
 * @project_name tensquare_parent
 * @package_name ustc.sse.tensquare.base.controller
 * @date 2019/1/2 15:25
 * @description God Bless, No Bug!
 */
@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        System.out.println("basehandler:"+e.getMessage());
        return new Result(false, StatusCode.ERROR,e.getMessage());
    }
}
