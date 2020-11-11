package com.joush.day19.demo05SpringJDBC;

import com.joush.day19.demo04DataBasePoolDruid.JDBCUtils;
import org.junit.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Map;

public class Demo02JdbcTemplateTest {

    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /*
        查询结果封装为 map 集合
        注意，查询的结果只能是一条记录
        key是列名，value是值
    */
    @Test
    public void test1(){
        String sql = "select * from meeting where meeting_id = ?";

        Map<String, Object> map = template.queryForMap(sql, "20200427081101722Z132483");
        System.out.println(map);
        // {meeting_id=20200427081101722Z132483, meeting_name=五一, applicant_id=joush970918@sina.com, applicant_name=苏小陨, meeting_date=20200501, start_time=10:00:00, end_time=22:00:00, display_date=2020-05-01, display_time=10:00:00 - 22:00:00, apply_time=2020-4-27 16:11:01}
    }


    /*
        查询结果封装为 map 集合
        注意，查询的结果只能是一条记录
        key是列名，value是值
    */
    @Test
    public void test2(){
        String sql = "select * from meeting";


        List<Map<String, Object>> list = template.queryForList(sql);

        for (Map<String, Object> map: list){
            System.out.println(map);
        }

    }

    /*
        查询结果封装为 JavaBean 对象
     */
    @Test
    public void test3(){
        String sql = "select * from meeting";
        List<Meeting> list = template.query(sql, new BeanPropertyRowMapper<Meeting>(Meeting.class));
        for (Meeting meeting: list){
            System.out.println(meeting);
        }

    }
    /*
        查询记录数
     */
    @Test
    public void test4(){
        String sql = "select count(meeting_id) from meeting";

        Long total = template.queryForObject(sql, Long.class);
        System.out.println(total);

    }
}
