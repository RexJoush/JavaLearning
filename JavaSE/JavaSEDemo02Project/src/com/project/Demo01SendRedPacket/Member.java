package com.project.Demo01SendRedPacket;

import java.util.ArrayList;
import java.util.Random;

/*
    普通成员类
 */
public class Member extends User{
    public Member() {
    }

    public Member(String name, int money) {
        super(name, money);
    }
    public void receive(ArrayList<Integer> list){
        // 从多个红包里随机抽取一个，给自己
        int index = new Random().nextInt(list.size());
        // 根据索引，删除集合中的红包，并给自己
        Integer delta = list.remove(index);
        // 当前成员的钱数
        int money = super.getMoney();
        // 加上，放回去
        super.setMoney(delta + money);

    }
}
