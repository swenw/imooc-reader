package com.imooc.reader.controller.management;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/management/book")
public class MBookController {
    @GetMapping("/index.html")
    public ModelAndView showBok() {
        return new ModelAndView("/management/book");
    }

    @PostMapping("/upload")
    @ResponseBody
    public Map upload(@RequestParam("img") MultipartFile file, HttpServletRequest request) throws IOException {
        //这句话是应用发布后执行的，/根路径有的是out路径下，有的是target目录下
        String uploadPath = request.getServletContext().getResource("/").getPath() + "/upload/";
        //在系统中避免同名文件产生，生成毫秒级别的文件名
        String fileName = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        //获取原始文件的拓展名
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //保存文件到upload目录
        file.transferTo(new File(uploadPath + fileName + suffix));

        Map result = new HashMap();
        result.put("error", 0);
        result.put("data", new String[]{"/upload/" + fileName + suffix}); //此路径可以直接访问
        return result;
    }
}
