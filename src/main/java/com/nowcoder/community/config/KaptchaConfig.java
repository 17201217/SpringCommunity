package com.nowcoder.community.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KaptchaConfig {

    @Bean
    public Producer kaptchaProducer() {
        Properties properties = new Properties();
        properties.setProperty("kaptcha.image.width", "100");//图片宽度
        properties.setProperty("kaptcha.image.height", "40");//图片高度
        properties.setProperty("kaptcha.textproducer.font.size", "32");//32号字体
        properties.setProperty("kaptcha.textproducer.font.color", "0,0,0");//字的颜色为黑色
        properties.setProperty("kaptcha.textproducer.char.string", "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYAZ");//随机字符的范围
        properties.setProperty("kaptcha.textproducer.char.length", "4");//生成随机字符的个数为4个
        properties.setProperty("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");//采用哪种噪声类，这里设置没有噪声

        DefaultKaptcha kaptcha = new DefaultKaptcha();
        Config config = new Config(properties);
        kaptcha.setConfig(config);
        return kaptcha;
    }

}
