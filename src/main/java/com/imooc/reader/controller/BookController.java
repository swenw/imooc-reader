package com.imooc.reader.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.reader.entity.*;
import com.imooc.reader.service.BookService;
import com.imooc.reader.service.CategoryService;
import com.imooc.reader.service.EvaluationService;
import com.imooc.reader.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class BookController {
    @Resource
    private CategoryService categoryService;
    @Resource
    private BookService bookService;
    @Resource
    private EvaluationService evaluationService;
    @Resource
    private MemberService memberService;

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
    public IPage<Book> selectBook(Long categoryId, String order, Integer p) {
        if (p == null) p = 1;
        IPage<Book> pageObject = bookService.paging(categoryId, order, p, 10);
        return pageObject;
    }

    // 在index页面的参数传输形式是：/book/{{bookId}}
    @GetMapping("/book/{id}")
    @ResponseBody
    public ModelAndView showDetail(@PathVariable("id") Long id, HttpSession httpSession) {
        Book book = bookService.selectById(id);
        List<Evaluation> evaluationList = evaluationService.selectByBookId(id);
        Member loginMember = (Member) httpSession.getAttribute("loginMember");
        // 跳转到detail页面
        ModelAndView view = new ModelAndView("/detail");
        if (loginMember != null) {
            //获取会员状态
            MemberReadState readState = memberService.selectMemberReadState(loginMember.getMemberId(), id);
            view.addObject("memberReadState", readState);
        }
        view.addObject("book", book);
        view.addObject("evaluationList", evaluationList);
        return view;
    }

}
