package com.nowcoder.community.util;

public class FileTypeJudge {

    //根据图片的后缀名来判断是否是图片
    public  static boolean judgeIsPhoto(String str){
        String[] strs = {".png",".jpg",".jpeg"};
        for(String str1 : strs){
            if(str.equals(str1)){
                return  true;
            }
        }

        return  false;
    }

}
