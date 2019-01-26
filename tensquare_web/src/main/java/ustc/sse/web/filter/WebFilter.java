package ustc.sse.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LRK
 * @project_name tensquare_parent
 * @package_name ustc.sse.manager.filter
 * @date 2019/1/26 15:56
 * @description God Bless, No Bug!
 */
@Component
public class WebFilter extends ZuulFilter {

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
        // 经过网关请求的头信息如果不做处理会丢失
        // 可以在这里转发头信息

        // 获取请求上下文
        RequestContext context = RequestContext.getCurrentContext();
        // 获取请求
        HttpServletRequest request = context.getRequest();
        // 获取头信息
        String authorization = request.getHeader("Authorization");
        if (authorization != null && !"".equals(authorization)) {// 转发头信息,继续向下传递
            context.addZuulRequestHeader("Authorization",authorization);
        }
        return null;
    }
}
