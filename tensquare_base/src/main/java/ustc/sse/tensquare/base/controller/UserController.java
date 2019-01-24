package ustc.sse.tensquare.base.controller;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import ustc.sse.tensquare.base.pojo.Label;
import ustc.sse.tensquare.base.service.LabelService;

import java.util.List;
import java.util.Map;

/**
 * @author LRK
 * @project_name tensquare_parent
 * @package_name ustc.sse.tensquare.base.controller
 * @date 2019/1/2 14:18
 * @description God Bless, No Bug!
 */
@RestController
@RequestMapping("/label")
@CrossOrigin
public class UserController {

    @Autowired
    LabelService labelService;

    // 查询所有标签
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功",
                labelService.findAll());
    }

    // 根据Id查询
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable("id") String id) {
        return new Result(true, StatusCode.OK, "查询成功",
                labelService.findById(id));
    }

    // 添加标签
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Label label) {
        labelService.add(label);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    // 更新标签
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Label label, @PathVariable String id) {
        label.setId(id);
        labelService.update(label);
        return new Result(true, StatusCode.OK, "更新成功");
    }

    // 根据Id删除标签
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String id) {
        labelService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    // 条件查询
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        List<Label> labelList = labelService.findSearch(searchMap);
        return new Result(true, StatusCode.OK, "查询成功", labelList);
    }

    /**
     * 分页查询
     * @param searchMap 查询条件
     * @param page 第几页
     * @param size 每页记录数
     * @return 查询结果
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result pageQuery(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<Label> pageList = labelService.pageQuery(searchMap, page, size);
        return new Result(true, StatusCode.OK, "分页查询成功",
                new PageResult<>(pageList.getTotalElements(), pageList.getContent()));
    }
}
