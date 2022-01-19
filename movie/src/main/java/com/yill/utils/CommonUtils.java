package com.yill.utils;

import java.util.Random;

public class CommonUtils {

    /**
     * 随机生成6位数的验证码
     * @return String code
     */
    public static String randomCode(){
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }

}
