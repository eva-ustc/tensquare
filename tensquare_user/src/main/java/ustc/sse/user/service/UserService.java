package ustc.sse.user.service;

import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import util.IdWorker;

import ustc.sse.user.dao.UserDao;
import ustc.sse.user.pojo.User;
import util.JwtUtil;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    BCryptPasswordEncoder encoder;
    @Autowired
    HttpServletRequest request;
    @Autowired
    JwtUtil jwtUtil;


    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    public User login(User user) {
        User userData = userDao.findByMobile(user.getMobile());
        if (userData != null && encoder.matches(user.getPassword(), userData.getPassword())) {
            return userData;
        }
        return null;
    }

    /**
     * 发送短信验证码
     *
     * @param mobile
     */
    public void sendSms(String mobile) {
        // 1 生成6位短信验证码
        String checkCode = RandomStringUtils.randomNumeric(6);
        System.out.println("收到的验证码是: " + checkCode);

        // 2 将验证码放入redis,5分钟过期
        redisTemplate.opsForValue().set("smscode_" + mobile, checkCode, 5, TimeUnit.HOURS);

        // 3 将验证码和手机号发送到rabbitMQ中
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("code", checkCode);

        // 发送短信验证码需要企业认证,暂时先不做
//        rabbitTemplate.convertAndSend("sms",map);
    }

    /**
     * 增加用户
     *
     * @param user
     * @param code
     */
    public void save(User user, String code) {

        String checkCode = (String) redisTemplate.opsForValue().get("smscode_" + user.getMobile());
        if (StringUtils.isEmpty(checkCode)) {
            throw new RuntimeException("请点击获取短信验证码");
        }
        if (!StringUtils.equals(checkCode, code)) {
            throw new RuntimeException("验证码输入不正确");
        }
        user.setId(idWorker.nextId() + "");
        user.setPassword(encoder.encode(user.getPassword()));
        user.setFollowcount(0);//关注数
        user.setFanscount(0);//粉丝数
        user.setOnline(0L);//在线时长
        user.setRegdate(new Date());//注册日期
        user.setUpdatedate(new Date());//更新日期
        user.setLastdate(new Date());//最后登陆日期

        userDao.save(user);
    }

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<User> findAll() {
        return userDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<User> findSearch(Map whereMap, int page, int size) {
        Specification<User> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return userDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<User> findSearch(Map whereMap) {
        Specification<User> specification = createSpecification(whereMap);
        return userDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public User findById(String id) {
        return userDao.findById(id).get();
    }

    /**
     * 增加
     *
     * @param user
     */
    public void add(User user) {
        user.setId(idWorker.nextId() + "");
        userDao.save(user);
    }

    /**
     * 修改
     *
     * @param user
     */
    public void update(User user) {
        userDao.save(user);
    }

    /**
     * 删除,必须有admin权限才能删除
     *
     * @param id
     */
    public void deleteById(String id) {
        // 验证role是否为admin
        // 前后端约定: 请求头Authorization 内容为 Bearer+空格+token
       /* String authorization = request.getHeader("Authorization");
        if (authorization==null || !authorization.startsWith("Bearer ")){
            throw new RuntimeException("权限不足");
        }
        String token = authorization.substring(7);// 提取token
        try {
            Claims claims = jwtUtil.parseJWT(token);
            if (claims==null || !"admin".equals(claims.get("roles"))){
                throw new RuntimeException("权限不足");
            }
        }catch (Exception e){
            throw new RuntimeException("权限不足");
        }*/
        String token = (String) request.getAttribute("claims_admin");
        if (token == null || "".equals(token)) {
            throw new RuntimeException("权限不足");
        }
        userDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<User> createSpecification(Map searchMap) {

        return new Specification<User>() {

            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                    predicateList.add(cb.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
                }
                // 手机号码
                if (searchMap.get("mobile") != null && !"".equals(searchMap.get("mobile"))) {
                    predicateList.add(cb.like(root.get("mobile").as(String.class), "%" + (String) searchMap.get("mobile") + "%"));
                }
                // 密码
                if (searchMap.get("password") != null && !"".equals(searchMap.get("password"))) {
                    predicateList.add(cb.like(root.get("password").as(String.class), "%" + (String) searchMap.get("password") + "%"));
                }
                // 昵称
                if (searchMap.get("nickname") != null && !"".equals(searchMap.get("nickname"))) {
                    predicateList.add(cb.like(root.get("nickname").as(String.class), "%" + (String) searchMap.get("nickname") + "%"));
                }
                // 性别
                if (searchMap.get("sex") != null && !"".equals(searchMap.get("sex"))) {
                    predicateList.add(cb.like(root.get("sex").as(String.class), "%" + (String) searchMap.get("sex") + "%"));
                }
                // 头像
                if (searchMap.get("avatar") != null && !"".equals(searchMap.get("avatar"))) {
                    predicateList.add(cb.like(root.get("avatar").as(String.class), "%" + (String) searchMap.get("avatar") + "%"));
                }
                // E-Mail
                if (searchMap.get("email") != null && !"".equals(searchMap.get("email"))) {
                    predicateList.add(cb.like(root.get("email").as(String.class), "%" + (String) searchMap.get("email") + "%"));
                }
                // 兴趣
                if (searchMap.get("interest") != null && !"".equals(searchMap.get("interest"))) {
                    predicateList.add(cb.like(root.get("interest").as(String.class), "%" + (String) searchMap.get("interest") + "%"));
                }
                // 个性
                if (searchMap.get("personality") != null && !"".equals(searchMap.get("personality"))) {
                    predicateList.add(cb.like(root.get("personality").as(String.class), "%" + (String) searchMap.get("personality") + "%"));
                }

                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };

    }


}
