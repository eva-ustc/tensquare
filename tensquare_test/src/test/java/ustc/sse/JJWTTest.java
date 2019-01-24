package ustc.sse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.util.Date;

/**
 * @author LRK
 * @project_name tensquare_parent
 * @package_name ustc.sse
 * @date 2019/1/24 14:24
 * @description God Bless, No Bug!
 */
public class JJWTTest {

    @Test
    public void testEncode(){
        // setIssuedAt()--设置签发时间
        // signWith()--设置签名秘钥
        // setExpiration()--设置过期时间
        // claim()--设置自定义信息
        JwtBuilder builder = Jwts.builder().setId("888")
                .setSubject("小白")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,"eva1ne")
                .setExpiration(new Date(System.currentTimeMillis()+1000*60))
                .claim("roles","admin")
                .claim("logo","logo.png");

        System.out.println(builder.compact());
    }
    @Test
    public void testDecode(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ODgiLCJzdWIiOiLlsI_nmb0iLCJpYXQiOjE1NDgzMTI1MzAsImV4cCI6MTU0ODMxMjU5MCwicm9sZXMiOiJhZG1pbiIsImxvZ28iOiJsb2dvLnBuZyJ9.Gm5w-iJQb7dL_DyQXcyU0SECOCJRpxYzk4i7cijb7Vw";
        Claims claims = Jwts.parser().setSigningKey("eva1ne").parseClaimsJws(token)
                .getBody();

        System.out.println(claims.getId());
        System.out.println(claims.getSubject());
        System.out.println("签发时间: "+claims.getIssuedAt());
        System.out.println("过期时间: "+claims.getExpiration());
        System.out.println("自定义信息roles: "+claims.get("roles"));
        System.out.println("自定义信息logo: "+claims.get("logo"));
    }
}
