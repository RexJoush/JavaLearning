package com.joush.test;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.joush.domain.Person;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class JacksonTest {

    @Test
    // 对象转为 json 字符串，测试注解
    public void test1() throws IOException {

        // 1.创建person对象
        Person p = new Person();
        p.setName("Joush");
        p.setAge(23);
        p.setGender("男");

        // 2.创建 Jackson 核心对象 ObjectMapper
        ObjectMapper mapper = new ObjectMapper();

        // 3.调用方法转换

        /*
            转换方法
                writeValue(P1, obj);
                    P1:
                        File: 将obj对象转换为JSON字符串，并保存在指定的文件中
                        Writer: 将obj对象转换为JSON字符串，填充到字符输出流
                        OutputStream: 将obj对象转换为JSON字符串，填充到字节输出流
                writeValueAsString(obj):将对象转为字符串
         */

        String json = mapper.writeValueAsString(p);
        System.out.println(json); // {"name":"Joush","age":23,"gender":"男"}

        // 文件对象
        mapper.writeValue(new File("d:\\a.txt"), p);

        // 字节输出流
        mapper.writeValue(new FileWriter("d:\\a.txt"), p);

    }

    @Test
    // 对象转为 json 字符串，测试注解
    public void test2() throws IOException {
        // 1.创建Person对象
        Person p = new Person();
        p.setName("Joush");
        p.setAge(23);
        p.setGender("男");
        p.setBirthday(new Date());

        // 2.创建 Jackson 核心对象 ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(p);

        System.out.println(json); // {"name":"Joush","age":23,"gender":"男","birthday":"2020-10-11"}
    }

    @Test
    // list 集合转为 json 字符串
    public void test3() throws IOException{
        // 1.创建Person对象
        Person p1 = new Person();
        p1.setName("Joush");
        p1.setAge(23);
        p1.setGender("男");
        p1.setBirthday(new Date());

        Person p2 = new Person();
        p2.setName("Rex");
        p2.setAge(23);
        p2.setGender("男");
        p2.setBirthday(new Date());

        Person p3 = new Person();
        p3.setName("Ice");
        p3.setAge(23);
        p3.setGender("男");
        p3.setBirthday(new Date());

        // 创建list集合
        List<Person> list = new ArrayList<>();

        list.add(p1);
        list.add(p2);
        list.add(p3);
        // 2.转换
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(list);

        System.out.println(json);
        // [{"name":"Joush","age":23,"gender":"男","birthday":"2020-10-11"},
        // {"name":"Rex","age":23,"gender":"男","birthday":"2020-10-11"},
        // {"name":"Ice","age":23,"gender":"男","birthday":"2020-10-11"}]


    }


    @Test
    // map 集合转为 json 字符串
    public void test4() throws IOException{
        // 1.创建Person对象
        Map<String, Object> map = new HashMap<>();

        map.put("name","Joush");
        map.put("age",23);
        map.put("gander","男");

        // 2.转换
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(map);

        System.out.println(json); // {"gander":"男","name":"Joush","age":23}


    }

    @Test
    // json 字符串转为 java 对象
    public void test5() throws IOException{
        // 1.拿到json字符串

        String json = "{\"name\":\"Joush\",\"age\":23,\"gender\":\"男\"}";

        // 2.创建 ObjectMapper 对象

        ObjectMapper mapper = new ObjectMapper();

        Person person = mapper.readValue(json, Person.class);

        System.out.println(person); // Person{name='Joush', age=23, gender='男', birthday='null'}


    }



}
