package com.imooc.reader.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.reader.entity.Evaluation;

import java.util.List;

public interface EvaluationService {
    /**
     * 根据图书编号查询有效短评
     * @param bookId 图书编号
     * @return 评论列表
     */
    public List<Evaluation> selectByBookId(Long bookId);

    /**
     * 对评论信息进行分页展示
     * @param page
     * @param rows
     * @return
     */
    public IPage<Evaluation> paging(Integer page, Integer rows);
}
