package com.imooc.reader.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("test") //说明实体对应哪一张表
public class Test {
    @TableId(type = IdType.AUTO) //说明数据库主键，并描述其为自增
    // 如果字段名与属性名相同或者符合驼峰命名规则，则TableField可以省略
    @TableField("id") // 说明属性对应于哪一个字段
    private Integer id;
    @TableField("content")
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
