package com.joush.service.impl;

import com.joush.service.AccountService;

import java.util.*;

/**
 * 账户业务层的实现类
 */
public class AccountServiceImpl3 implements AccountService {

    private String[] myStrings;
    private List<String> myList;
    private Set<String> mySet;
    private Map<String, String> myMap;
    private Properties properties;

    public void setMyStrings(String[] myStrings) {
        this.myStrings = myStrings;
    }

    public void setMyList(List<String> myList) {
        this.myList = myList;
    }

    public void setMySet(Set<String> mySet) {
        this.mySet = mySet;
    }

    public void setMyMap(Map<String, String> myMap) {
        this.myMap = myMap;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public void saveAccount() {
        System.out.println(Arrays.toString(myStrings));
        System.out.println(myList);
        System.out.println(mySet);
        System.out.println(myMap);
        System.out.println(properties);
    }
}
