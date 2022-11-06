package com.imooc.reader.task;

import com.imooc.reader.service.BookService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 完成自动计算任务
 */
@Component
public class ComputeTask {
    @Resource
    private BookService bookService;
    //任务调度，每分钟调度一次
    @Scheduled(cron = "0 0 0 * * ?") // 每天的0点执行任务
    public void updateEvaluation() {
        bookService.updateEvaluation();
        System.out.println(new Date() + " 已更新所有评分");
    }
}
