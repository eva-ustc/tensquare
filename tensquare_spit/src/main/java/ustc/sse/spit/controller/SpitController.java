package ustc.sse.spit.controller;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import ustc.sse.spit.pojo.Spit;
import ustc.sse.spit.service.SpitService;

/**
 * @author LRK
 * @project_name tensquare_parent
 * @package_name ustc.sse.spit.controller
 * @date 2019/1/21 16:13
 * @description God Bless, No Bug!
 */
@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {

    @Autowired
    SpitService spitService;

    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        return new Result(true, StatusCode.OK,"查询全部吐槽成功",
                spitService.findAll());
    }

    @RequestMapping(value = "/{spitId}",method = RequestMethod.GET)
    public Result findById(@PathVariable String spitId){

        return new Result(true,StatusCode.OK,"查询单个吐槽成功",
                spitService.findById(spitId));
    }

    /**
     * 添加
     * @param spit
     * @return
     */
    @RequestMapping(method=RequestMethod.POST)
    public Result add(@RequestBody Spit spit){
        spitService.add(spit);
        return new Result(true,StatusCode.OK,"增加吐槽成功");
    }

    /**
     * 修改
     * @param spit
     * @param spitId
     * @return
     */
    @RequestMapping(value="/{spitId}",method=RequestMethod.PUT)
    public Result update(@RequestBody Spit spit,@PathVariable String spitId )
    {
        spit.set_id(spitId);
        spitService.update(spit);
        return new Result(true,StatusCode.OK,"修改吐槽成功");
    }

    /**
     * 删除
     * @param spitId
     * @return
     */
    @RequestMapping(value="/{spitId}",method=RequestMethod.DELETE)
    public Result deleteById(@PathVariable String spitId ){
        spitService.deleteById(spitId);
        return new Result(true,StatusCode.OK,"删除吐槽成功");
    }

    /**
     * 根据父级id查询吐槽
     * @param parentid
     * @param page
     * @param size
     * @return
     */
    @RequestMapping(value = "/comment/{parentid}/{page}/{size}",method = RequestMethod.GET)
    public Result findByParentId(@PathVariable String parentid,@PathVariable int page,@PathVariable int size){
        Page<Spit> spitPage = spitService.findByParentId(parentid, page, size);
        return new Result(true,StatusCode.OK,"根据父级id查询吐槽成功",
                new PageResult<Spit>(spitPage.getTotalElements(),spitPage.getContent()));
    }

    /**
     * 点赞
     * @param spitId
     * @return
     */
    @RequestMapping(value = "/thumbup/{spitId}",method = RequestMethod.PUT)
    public Result thumbup(@PathVariable String spitId){

        String userId = "2023";
        if (redisTemplate.opsForValue().get("thumbup_"+userId
        +"_"+spitId)!=null){
            return new Result(false,StatusCode.REPEERROR,"您已经点过赞了");
        }

        spitService.thumbup(spitId);
        redisTemplate.opsForValue().set("thumbup_"+userId
                +"_"+spitId,"1");
        return new Result(true,StatusCode.OK,"点赞成功");
    }
}
