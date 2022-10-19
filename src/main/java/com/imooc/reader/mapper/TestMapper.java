package com.imooc.reader.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.reader.entity.Test;

public interface TestMapper extends BaseMapper<Test> {
    // 可以在Mapper中书写自己的sql方法
    public void insertSample();
}
