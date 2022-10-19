package com.imooc.reader;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imooc.reader.entity.Test;
import com.imooc.reader.mapper.TestMapper;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MyBatisPlusTest {
    @Resource
    private TestMapper testMapper;

    @org.junit.Test
    public void testInsert() {
        Test test = new Test();
        test.setContent("MyBatisPlus");
        testMapper.insert(test);
    }

    @org.junit.Test
    public void testUpdate() {
        Test test = testMapper.selectById(7);
        test.setContent("MyBatisPlus2");
        testMapper.updateById(test);
    }

    @org.junit.Test
    public void testSelect() {
        // 查询生成器，可以多个条件链接查询
        QueryWrapper<Test> testQueryWrapper = new QueryWrapper<>();
        testQueryWrapper.eq("id", 3);
        List<Test> list = testMapper.selectList(testQueryWrapper);
        System.out.println(list);
    }
}
