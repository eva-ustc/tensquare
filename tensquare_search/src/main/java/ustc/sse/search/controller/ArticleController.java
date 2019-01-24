package ustc.sse.search.controller;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ustc.sse.search.pojo.Article;
import ustc.sse.search.service.ArticleService;

/**
 * @author LRK
 * @project_name tensquare_parent
 * @package_name ustc.sse.search.controller
 * @date 2019/1/22 16:16
 * @description God Bless, No Bug!
 */
@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    /**
     * 添加文章文档
     * @param article
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Article article){
        articleService.add(article);
        return new Result(true, StatusCode.OK,"添加文章成功");
    }

    /**
     * 搜索文章
     * @param keywords
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/search/{keywords}/{page}/{size}")
    public Result findByTitleLike(@PathVariable String keywords,@PathVariable int page,@PathVariable int size){

        Page<Article> articlePage = articleService.findByTitleLike(keywords, page, size);
        return new Result(true,StatusCode.OK,"关键字检索成功",
                new PageResult<Article>(articlePage.getTotalElements(),articlePage.getContent()));
    }
}
