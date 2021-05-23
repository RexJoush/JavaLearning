package com.joush.bean;

import lombok.Data;

import java.util.List;

/**
 * @author Rex Joush
 * @time 2021.04.29 16:08
 */

@Data
public class Person {
    private String userName;
    private int age;
    private String birth;
    private Pet pet;
    private List<String> hobbies;
}
