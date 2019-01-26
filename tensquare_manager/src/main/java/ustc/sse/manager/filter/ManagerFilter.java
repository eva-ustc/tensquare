package ustc.sse.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LRK
 * @project_name tensquare_parent
 * @package_name ustc.sse.manager.filter
 * @date 2019/1/26 15:56
 * @description God Bless, No Bug!
 */
@Component
public class ManagerFilter extends ZuulFilter {

    @Autowired
    JwtUtil jwtUtil;
    /**
     * 过滤类型:
     *      pre--   在请求处理之前被调用
     *      route-- 在路由请求时候被调用
     *      error-- 处理请求时发生错误时被调用
     *      post--  在route和error过滤器之后被调用
     *
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 多个过滤器优先级--数字越小优先级越高
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否开启当前过滤器
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     *  过滤器内执行的操作,return 任何object都表示继续执行
     *  setSendZullResponse(false)表示不再继续执行
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        System.out.println("经过了后台过滤器...");
        // 获取请求上下文
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();

        if (request.getMethod().equals("OPTIONS")) {// 网关的分发请求
            return null;
        }
        String url = request.getRequestURL().toString();
        if (url.indexOf("/admin/login")>0){// 如果请求的是登录页面则放行
            System.out.println("登录页面: "+url);
            return null;
        }
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);
            Claims claims = jwtUtil.parseJWT(token);
            if (claims != null) {
                if ("admin".equals(claims.get("roles"))) {
                    context.addZuulRequestHeader("Authorization",authHeader);
                    System.out.println("token 验证通过,添加了头信息 "+authHeader);
                    return null;
                }
            }
        }
        context.setSendZuulResponse(false); // 过滤该请求,不进行路由
        context.setResponseStatusCode(401); // 设置http状态码
        context.setResponseBody("无权访问");
        context.getResponse().setContentType("text/html;charset=UTF-8");

        return null;
    }
}
