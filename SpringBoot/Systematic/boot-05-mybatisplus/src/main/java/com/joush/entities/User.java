package com.joush.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Rex Joush
 * @time 2021.05.21 10:35
 */

@Data
@TableName("user")
public class User {

    // 表示此属性不在数据库中存在，不封装
//    @TableField(exist = false)
//    private String userName;

    @TableId(value = "id", type = IdType.AUTO)
    private int id;
    private String name;
    private Integer age;
    private String email;
}
