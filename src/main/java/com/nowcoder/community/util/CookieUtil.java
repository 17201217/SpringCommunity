package com.nowcoder.community.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtil {
    public static String getValue(HttpServletRequest request, String name) {
        //检测请求与名字是否为空，为空就抛出异常
        if(request == null || name == null){
            throw new IllegalArgumentException("参数为空");
        }
        //请求中获取的是多个cookie,是一个cookie数组
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals(name)){
                    return cookie.getValue();//这里的cookie主要存入是登录凭证
                }
            }
        }

        return null;
    }
}
