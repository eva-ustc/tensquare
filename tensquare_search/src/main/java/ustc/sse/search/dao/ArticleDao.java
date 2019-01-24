package ustc.sse.search.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import ustc.sse.search.pojo.Article;

/**
 * @author LRK
 * @project_name tensquare_parent
 * @package_name ustc.sse.search.dao
 * @date 2019/1/22 16:13
 * @description God Bless, No Bug!
 */
public interface ArticleDao extends ElasticsearchRepository<Article,String> {

    public Page<Article> findByTitleOrContentLike(String title, String content, Pageable pageable);
}
