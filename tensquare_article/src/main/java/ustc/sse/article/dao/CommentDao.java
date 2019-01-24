package ustc.sse.article.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ustc.sse.article.pojo.Comment;

import java.util.List;

/**
 * @author LRK
 * @project_name tensquare_parent
 * @package_name ustc.sse.article.dao
 * @date 2019/1/21 18:14
 * @description God Bless, No Bug!
 */
public interface CommentDao extends MongoRepository<Comment,String> {
    // 根据文章id查询评论列表
    public List<Comment> findByArticleid(String articleid);
}
