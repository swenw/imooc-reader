package com.imooc.reader.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.reader.entity.Book;
import com.imooc.reader.entity.Category;
import com.imooc.reader.service.BookService;
import com.imooc.reader.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class BookController {
    @Resource
    private CategoryService categoryService;
    @Resource
    private BookService bookService;

    @GetMapping("/")
    public ModelAndView showIndex() {
        ModelAndView view = new ModelAndView("/index");
        List<Category> categoryList = categoryService.selectAll();
        view.addObject("categoryList", categoryList);
        return view;
    }

    /**
     * 分页查询图书列表
     * @param p 页号
     * @return 分页对象
     */
    @GetMapping("/books")
    @ResponseBody // 用于返回Json序列化结果
    public IPage<Book> selectBook(Integer p) {
        if (p == null) p = 1;
        IPage<Book> pageObject = bookService.paging(p, 10);
        return pageObject;
    }
}
