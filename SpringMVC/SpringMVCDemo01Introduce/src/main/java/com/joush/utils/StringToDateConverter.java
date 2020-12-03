package com.joush.utils;

import org.springframework.core.convert.converter.Converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 字符串转日期
 */
public class StringToDateConverter implements Converter<String, Date> {

    /**
     * @param s 传入的字符串
     * @return 转换后的日期
     */
    @Override
    public Date convert(String s) {
        if (s == null){
            throw new RuntimeException("please input date string.");
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // 把字符串转换日期
        try {
            return dateFormat.parse(s);
        } catch (ParseException e) {
            throw new RuntimeException("data type transfer error");
        }
    }
}
