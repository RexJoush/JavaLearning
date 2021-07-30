package com.joush.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Rex Joush
 * @time 2021.04.23 14:45
 */

@Data   // 实体类
@AllArgsConstructor     // 全参构造
@NoArgsConstructor      // 空参构造
public class Payment implements Serializable {

    private long id;        // id
    private String serial;  // 流水号
}
