package com.imooc.reader.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.reader.entity.Book;
import com.imooc.reader.entity.Evaluation;
import com.imooc.reader.entity.Member;
import com.imooc.reader.mapper.BookMapper;
import com.imooc.reader.mapper.EvaluationMapper;
import com.imooc.reader.mapper.MemberMapper;
import com.imooc.reader.service.EvaluationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("evaluationService")
@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
public class EvaluationServiceImpl implements EvaluationService {
    @Resource
    private EvaluationMapper evaluationMapper;
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private BookMapper bookMapper;
    /**
     * 根据图书编号查询有效短评
     *
     * @param bookId 图书编号
     * @return 评论列表
     */
    @Override
    @ResponseBody
    public List<Evaluation> selectByBookId(Long bookId) {
        Book book = bookMapper.selectById(bookId);
        QueryWrapper<Evaluation> evaluationQueryWrapper = new QueryWrapper<>();
        evaluationQueryWrapper.eq("book_id", bookId);
        evaluationQueryWrapper.eq("state", "enable");
        evaluationQueryWrapper.orderByDesc("create_time");
        List<Evaluation> evaluationList = evaluationMapper.selectList(evaluationQueryWrapper);
        // 将Member对象加入Evaluation对象中
        for (Evaluation eva : evaluationList) {
            Member member = memberMapper.selectById(eva.getMemberId());
            eva.setMember(member);
            eva.setBook(book);
        }
        return evaluationList;
    }

    /**
     * 对评论信息进行分页展示
     *
     * @param page
     * @param rows
     * @return
     */
    @Override
    @ResponseBody
    public List<Evaluation> paging(Integer page, Integer rows) {
        Page<Evaluation> p = new Page<>(page, rows);
        QueryWrapper<Evaluation> evaluationQueryWrapper = new QueryWrapper<>();
        Page<Evaluation> evaluationPage = evaluationMapper.selectPage(p, evaluationQueryWrapper);
        List<Evaluation> records = evaluationPage.getRecords();
        for (Evaluation r : records) {
            Book book = bookMapper.selectById(r.getBookId());
            r.setBook(book);
            Member member = memberMapper.selectById(r.getMemberId());
            r.setMember(member);
        }
        return records;
    }

    /**
     * 对非法的评论信息进行禁用
     *
     * @param evaluationId 评论id号
     * @param value        禁用原因
     * @return 当前评论
     */
    @Override
    @ResponseBody
    @Transactional
    public Evaluation disable(Long evaluationId, String value) {
        Evaluation evaluation = evaluationMapper.selectById(evaluationId);
        evaluation.setState("disable");
        evaluation.setDisableReason(value);
        evaluation.setDisableTime(new Date());
        evaluationMapper.updateById(evaluation);
        return evaluation;
    }
}
