package ustc.sse.spit.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import ustc.sse.spit.pojo.Spit;

/**
 * @author LRK
 * @project_name tensquare_parent
 * @package_name ustc.sse.spit.dao
 * @date 2019/1/21 15:58
 * @description God Bless, No Bug!
 */
public interface SpitDao extends MongoRepository<Spit,String> {

    public Page<Spit> findByParentid(String parentId, Pageable pageable);
}
