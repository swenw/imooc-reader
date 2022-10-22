package com.imooc.reader.controller;

import com.google.code.kaptcha.Producer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class KaptchaController {
    @Resource
    // 需与配置中的kaptcha的bean id保持一致
    private Producer kaptchaProducer;

    @GetMapping("/verify_code")
    public void createVerifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //响应立即过期
        response.setDateHeader("Expires",0);
        //不缓存任何图片数据
        response.setHeader("Cache-Control" , "no-store,no-cache,must-revalidate");
        response.setHeader("Cache-Control" , "post-check=0,pre-check=0");
        response.setHeader("Pragma" , "no-cache");
        // 对外输出为图片
        response.setContentType("image/png");
        //生成验证码字符文本
        String verifyCode = kaptchaProducer.createText();
        request.getSession().setAttribute("kaptchaVerifyCode",verifyCode);
        System.out.println(request.getSession().getAttribute("kaptchaVerifyCode"));
        BufferedImage image = kaptchaProducer.createImage(verifyCode);//创建验证码图片
        ServletOutputStream out = response.getOutputStream();
        //图片从服务器端通过响应发送到客户端，客户端收到png，直接当作图片进行展示
        ImageIO.write(image, "png", out);//输出图片流
        out.flush();
        out.close();
    }
}
