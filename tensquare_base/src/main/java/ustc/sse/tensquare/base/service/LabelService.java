package ustc.sse.tensquare.base.service;

import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ustc.sse.tensquare.base.dao.LabelDao;
import ustc.sse.tensquare.base.pojo.Label;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author LRK
 * @project_name tensquare_parent
 * @package_name ustc.sse.tensquare.base.service
 * @date 2019/1/2 14:10
 * @description God Bless, No Bug!
 */
@Service
public class LabelService {

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;


    /**
     * 根据Id查询标签
     *
     * @param id
     * @return
     */
    public Label findById(String id) {

        return labelDao.findById(id).get();
    }

    /**
     * 添加标签
     *
     * @param label
     */
    public void add(Label label) {
        label.setId(idWorker.nextId() + ""); // 设置Id
        labelDao.save(label);
    }

    /**
     * 修改标签
     *
     * @param label
     */
    public void update(Label label) {
        labelDao.save(label);
    }

    /**
     * 删除标签
     *
     * @param id
     */
    public void deleteById(String id) {
        labelDao.deleteById(id);
    }

    /**
     * 构件查询条件
     *
     * @param searchMap
     * @return
     */
    private Specification<Label> createSpecification(Map searchMap) {
        // 构造查询条件
        return new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                // 用list封装查询条件,然后转为数组
                List<Predicate> predicateList = new ArrayList<>();
                // 添加查询条件
                if (searchMap.get("labelname") != null && !"".equals(searchMap.get("labelname"))) {
                    predicateList.add(criteriaBuilder.like(root.get("labelname").as(String.class)
                            , "%" + (String) searchMap.get("labelname") + "%"));
                }
                if (searchMap.get("state") != null && !"".equals(searchMap.get("state"))) {
                    predicateList.add(criteriaBuilder.equal(root.get("state").as(String.class), searchMap.get("state")));
                }
                if (searchMap.get("recommend") != null && !"".equals(searchMap.get("recommend"))) {
                    predicateList.add(criteriaBuilder.equal(root.get("recommend").as(String.class),
                            searchMap.get("recommend")));
                }
                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
    }

    /**
     * 查询所有标签
     *
     * @return
     */
    public List<Label> findAll() {

        return labelDao.findAll();
    }

    /**
     * 条件查询
     *
     * @param searchMap 查询条件
     * @return
     */
    public List<Label> findSearch(Map searchMap) {

        Specification<Label> specification = createSpecification(searchMap);
        return labelDao.findAll(specification);
    }

    /**
     * 条件 + 分页查询
     *
     * @param searchMap 查询条件
     * @param page      第几页
     * @param size      每页大小
     * @return
     */
    public Page<Label> pageQuery(Map searchMap, int page, int size) {

        Specification<Label> specification = createSpecification(searchMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return labelDao.findAll(specification, pageRequest);
    }
}
