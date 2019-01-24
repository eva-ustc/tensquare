package ustc.sse.qa.interceptor;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author LRK
 * @project_name tensquare_parent
 * @package_name ustc.sse.user.interceptor
 * @date 2019/1/24 16:29
 * @description God Bless, No Bug!
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        // 验证是否包含Authorization请求头,是否包含token令牌信息,如果包含,提取出来
        String header = request.getHeader("Authorization");
        if (header != null && !"".equals(header)) {
            if (header.startsWith("Bearer ")) {
                String token = header.substring(7);
                try {

                    Claims claims = jwtUtil.parseJWT(token);
                    String roles = (String) claims.get("roles");
                    if (roles != null) {
                        if ("admin".equals(roles)) {
                            request.setAttribute("claims_admin", claims);
                        }
                        if ("user".equals(roles)) {
                            request.setAttribute("claims_user", claims);
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException("令牌信息有误");
                }
            }
        }
        return true;
    }
}
