package fun.krits.educenter.utils;

import fun.krits.commonutils.JwtUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/***
 * 配置Token信息的工具类
 */
public class TokenUtil {
    public static String getUidFromToken(HttpServletRequest request){
        Map<String, Object> m = getTokenBody(request);
        if (m == null)
            return null;
        String uid = m.get("uid").toString();
        return uid;
    }
    public static Map<String, Object> getTokenBody(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (cookies == null)
            return null;
        for (Cookie c : cookies){
            if (c.getName().equals("guli_token")){
                String token = c.getValue();
                int verify = JwtUtil.verifyToken(token);
                if (verify != 0)
                    return null;
                return JwtUtil.parseToken(token);
            }
        }
        return null;
    }
    public static void setToken(String token, HttpServletResponse response){
        Cookie cookie = new Cookie("guli_token", token);
        cookie.setMaxAge(60*60*24);         //一天
        response.addCookie(cookie);
    }
    public static void removeToken(HttpServletResponse response){
        Cookie cookie=new Cookie("guli_token",null); //假如要删除名称为username的Cookie
        cookie.setMaxAge(0); //立即删除型
        cookie.setPath("/"); //项目所有目录均有效，这句很关键，否则不敢保证删除
        response.addCookie(cookie); //重新写入，将覆盖之前的
    }
}