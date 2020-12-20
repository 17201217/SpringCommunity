package com.nowcoder.community.util;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CommunityUtil {
    //生成随机字符串
    public static String gennerateUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    // MD5加密
    // hello -> abc123def456
    // hello + 3e4a8 -> abc123def456abc,数据库用户表中加上salt字段是为了防止密码泄露
      public static String md5(String key) {
        if(StringUtils.isBlank(key)){
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
      }

      /*
      得到json格式的字符串
       */
      public static String getJOSNString(int code, String msg, Map<String,Object> map){
          JSONObject json = new JSONObject();
          json.put("code",code);
          json.put("msg",msg);
          if(map != null){
              //遍历key
              for(String key: map.keySet()){
                  json.put(key,map.get(key));
              }
          }
          return json.toJSONString();
      }

    public static String getJOSNString(int code, String msg){
          return getJOSNString(code,msg,null);
    }

    public static String getJOSNString(int code){
          return getJOSNString(code,null,null);
    }


    //测试是否可以转化为json格式的数据
    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "zhangsan");
        map.put("age", 25);
        System.out.println(getJOSNString(0,"ok",map));

    }
      
      
      

}
