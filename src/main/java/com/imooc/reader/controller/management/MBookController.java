package com.imooc.reader.controller.management;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.reader.entity.Book;
import com.imooc.reader.service.BookService;
import com.imooc.reader.service.BussinessException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/management/book")
public class MBookController {
    @Resource
    private BookService bookService;
    @GetMapping("/index.html")
    public ModelAndView showBook() {
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
        result.put("errno", 0);
        result.put("data", new String[]{"/upload/" + fileName + suffix}); //此路径可以直接访问
        return result;
    }

    @PostMapping("/create")
    @ResponseBody
    public Map createBook(Book book) {
        Map result = new HashMap();
        try {
            // 注意前端传来的Book对象有些值是缺失的，需自己补全
            book.setEvaluationQuantity(0);
            book.setEvaluationScore(0f);
            Document doc = Jsoup.parse(book.getDescription()); //解析图书详情
            Element img = doc.select("img").first(); //获取图书详情第一图元素对象
            String cover = img.attr("src");
            book.setCover(cover); //来自于description描述的第一幅图
            bookService.createBook(book);
            result.put("code", "0");
            result.put("msg", "success");
        } catch (BussinessException e) {
            e.printStackTrace();
            result.put("code", e.getCode());
            result.put("msg", e.getMsg());
        }
        return result;
    }

    @GetMapping("/list")
    @ResponseBody
    public Map list(Integer page, Integer limit) {
        if (page == null) page = 1;
        if (limit == null) limit = 10;
        IPage<Book> paging = bookService.paging(null, null, page, limit);
        Map result = new HashMap();
        result.put("code", "0");
        result.put("msg", "success");
        result.put("data", paging.getRecords());
        result.put("count", paging.getTotal());
        return result;
    }

    @GetMapping("/id/{id}")
    @ResponseBody
    public Map selectById(@PathVariable("id") Long bookId) {
        Book book = bookService.selectById(bookId);
        Map result = new HashMap();
        result.put("code", "0");
        result.put("msg", "success");
        result.put("data", book);
        return result;
    }

    @PostMapping("/update")
    @ResponseBody
    public Map updateBook(Book book) { //不要直接对Book进行更新，因为只包含部分信息
        Map result = new HashMap();
        try {
            Book rawBook = bookService.selectById(book.getBookId());
            rawBook.setBookName(book.getBookName());
            rawBook.setSubTitle(book.getSubTitle());
            rawBook.setAuthor(book.getAuthor());
            rawBook.setCategoryId(book.getCategoryId());
            rawBook.setDescription(book.getDescription());
            Document doc = Jsoup.parse(book.getDescription());
            String cover = doc.select("img").first().attr("src");
            rawBook.setCover(cover);
            bookService.updateBook(rawBook);
            result.put("code", "0");
            result.put("msg", "success");
        } catch (BussinessException ex) {
            ex.printStackTrace();
            result.put("code", ex.getCode());
            result.put("msg", ex.getMsg());
        }
        return result;
    }

    @GetMapping("/delete/{id}")
    @ResponseBody
    public Map deleteBook(@PathVariable("id") Long bookId) {
        Map result = new HashMap();
        try {
            bookService.deleteBook(bookId);
            result.put("code", "0");
            result.put("msg", "success");
        } catch (BussinessException ex) {
            ex.printStackTrace();
            result.put("code", ex.getCode());
            result.put("msg", ex.getMsg());
        }
        return result;
    }
}
