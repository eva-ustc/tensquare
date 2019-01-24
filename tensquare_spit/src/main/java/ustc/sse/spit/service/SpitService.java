package ustc.sse.spit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import ustc.sse.spit.dao.SpitDao;
import ustc.sse.spit.pojo.Spit;
import util.IdWorker;

import java.util.Date;
import java.util.List;

/**
 * @author LRK
 * @project_name tensquare_parent
 * @package_name ustc.sse.spit.service
 * @date 2019/1/21 15:59
 * @description God Bless, No Bug!
 */
@Service
public class SpitService {

    @Autowired
    SpitDao spitDao;

    @Autowired
    IdWorker idWorker;

    @Autowired
    MongoTemplate mongoTemplate;

    /**
     * 查询全部记录
     *
     * @return
     */
    public List<Spit> findAll() {
        return spitDao.findAll();
    }

    /**
     * 根据主键查询实体
     *
     * @param id
     * @return
     */
    public Spit findById(String id) {
        return spitDao.findById(id).get();
    }

    /**
     * 增加
     *
     * @param spit
     */
    public void add(Spit spit) {
        spit.set_id(idWorker.nextId() + "");
        spit.setPublishtime(new Date());//发布日期
        spit.setVisits(0);//浏览量
        spit.setShare(0);//分享数
        spit.setThumbup(0);//点赞数
        spit.setComment(0);//回复数
        spit.setState("1");//状态
        if (spit.getParentid()!=null && !"".equals(spit.getParentid())){
            // 如果不存在上级id,评论数+1
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update = new Update();
            update.inc("comment",1);
            mongoTemplate.updateFirst(query,update,"spit");
        }
        spitDao.save(spit);
    }

    /**
     * 修改
     *
     * @param spit
     */
    public void update(Spit spit) {
        spitDao.save(spit);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        spitDao.deleteById(id);
    }

    public Page<Spit> findByParentId(String parentId,int page,int size){
        Pageable pageable = PageRequest.of(page-1,size);
        return spitDao.findByParentid(parentId,pageable);
    }

    /**
     * db.spit.update({_id:1},{$inc:{thumbup:1}})
     * @param spitId
     */
    public void thumbup(String spitId) {

        Query query = new Query(); // 条件
        query.addCriteria(Criteria.where("_id").is(spitId));
        Update update=new Update(); // 要修改的字段
        update.inc("thumbup",1);
        mongoTemplate.updateFirst(query,update,"spit");
    }
}
