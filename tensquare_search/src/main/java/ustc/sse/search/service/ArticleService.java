package ustc.sse.search.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ustc.sse.search.dao.ArticleDao;
import ustc.sse.search.pojo.Article;

/**
 * @author LRK
 * @project_name tensquare_parent
 * @package_name ustc.sse.search.service
 * @date 2019/1/22 16:14
 * @description God Bless, No Bug!
 */
@Service
public class ArticleService {

    @Autowired
    ArticleDao articleDao;

    /**
     * 增加文章
     * @param article
     */
    public void add(Article article){
        articleDao.save(article);
    }

    /**
     * 检索
     * @param keywords
     * @param page
     * @param size
     * @return
     */
    public Page<Article> findByTitleLike(String keywords,int page,int size){
        PageRequest pageRequest = PageRequest.of(page-1,size);
        return articleDao.findByTitleOrContentLike(keywords,keywords,pageRequest);
    }
}
