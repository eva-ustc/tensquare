package ustc.sse.article.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ustc.sse.article.pojo.Comment;
import ustc.sse.article.service.CommentService;

/**
 * @author LRK
 * @project_name tensquare_parent
 * @package_name ustc.sse.article.controller
 * @date 2019/1/21 18:16
 * @description God Bless, No Bug!
 */
@RestController
@CrossOrigin
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @RequestMapping(method= RequestMethod.POST)

    /**
     * 添加评论
     */
    public Result save(@RequestBody Comment comment){
        commentService.add(comment);
        return new Result(true, StatusCode.OK, "提交成功 ");
    }

    @RequestMapping(value = "/article/{articleid}",method = RequestMethod.GET)
    public Result findByArticleid(String articleid){
        return new Result(true,StatusCode.OK,"根据文章id查询评论列表成功",
                commentService.findByArticleid(articleid));
    }
}
