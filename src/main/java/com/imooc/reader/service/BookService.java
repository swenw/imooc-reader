package com.imooc.reader.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.reader.entity.Book;

public interface BookService {
    /**
     * 分页查询图书
     * @param categoryId 图书分类编号
     * @oaram order 排序规则
     * @param page 页号
     * @param rows 每页记录数
     * @return 分页对象
     */
    public IPage<Book> paging(Long categoryId, String order, Integer page, Integer rows);

    /**
     * 根据图书编号查询图书对象
     * @param bookId 图书编号
     * @return 图书对象
     */
    public Book selectById(Long bookId);

    /**
     * 更新图书评分/评价数量
     */
    public void updateEvaluation();

    /**
     * 创建新的图书
     * @param book
     * @return
     */
    public Book createBook(Book book);

    /**
     * 对图书内容实现更新
     * @param book
     * @return
     */
    public Book updateBook(Book book);

    /**
     * 根据图书id删除对应图书
     * @param bookId
     */
    public void deleteBook(Long bookId);
}
