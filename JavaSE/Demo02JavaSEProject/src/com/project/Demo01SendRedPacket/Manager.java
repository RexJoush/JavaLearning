package com.project.Demo01SendRedPacket;

import java.util.ArrayList;

/*
    群主类
 */
public class Manager extends User{

    public Manager(){}

    public Manager(String name, int money) {
        super(name, money);
    }

    // 发红包
    public ArrayList<Integer> send(int totalMoney, int count){
        // 首先需要一个集合，存储若干个红包的金额
        ArrayList<Integer> redList = new ArrayList<>();

        // 看群主有多少钱
        int leftMoney = super.getMoney();
        if (totalMoney > leftMoney){
            System.out.println("余额不足!");
            return redList;
        }

        // 扣钱,重新设置余额
        super.setMoney(leftMoney - totalMoney);

        // 发红包均分
        int avg = totalMoney / count;
        int mod = totalMoney % count; // 余数

        // 除不开的情况，把剩余的钱放在最后一个红包里
        for (int i = 0; i < count - 1; i++) {
            redList.add(avg);
        }
        redList.add(avg + mod);

        return redList;
    }

}
