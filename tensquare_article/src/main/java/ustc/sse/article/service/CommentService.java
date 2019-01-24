package ustc.sse.article.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ustc.sse.article.dao.CommentDao;
import ustc.sse.article.pojo.Comment;
import util.IdWorker;

import java.util.List;

/**
 * @author LRK
 * @project_name tensquare_parent
 * @package_name ustc.sse.article.service
 * @date 2019/1/21 18:15
 * @description God Bless, No Bug!
 */
@Service
public class CommentService {
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private IdWorker idWorker;

    /**
     * 添加评论
     * @param comment
     */
    public void add(Comment comment){
        comment.set_id( idWorker.nextId()+"" );
        commentDao.save(comment);
    }

    public List<Comment> findByArticleid(String articleid){
        return commentDao.findByArticleid(articleid);
    }
}
