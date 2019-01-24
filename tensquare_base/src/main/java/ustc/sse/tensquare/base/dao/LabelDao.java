package ustc.sse.tensquare.base.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ustc.sse.tensquare.base.pojo.Label;

/**
 * @author LRK
 * @project_name tensquare_parent
 * @package_name ustc.sse.tensquare.base.dao
 * @date 2019/1/2 14:08
 * @description God Bless, No Bug!
 *  JpaRepository: 基本的增删改查
 *  JpaSpecificationExecutor: 复杂查询
 */
public interface LabelDao extends JpaRepository<Label,String>,JpaSpecificationExecutor<Label>{

}
