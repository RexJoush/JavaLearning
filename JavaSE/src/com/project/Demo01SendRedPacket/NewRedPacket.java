package com.project.Demo01SendRedPacket;

import java.util.ArrayList;

public class NewRedPacket {
    public static void main(String[] args) {
        Manager manager = new Manager("群主",100);

        Member one = new Member("成员A",0);
        Member two = new Member("成员B",0);
        Member three = new Member("成员C",0);

        manager.show(); // 100
        one.show(); // 0
        two.show(); // 0
        three.show(); // 0
        System.out.println("===========");

        // 群主总共发20块，三个红包
        ArrayList<Integer> redList = manager.send(20,3);

        // 三个普通成员收红包
        one.receive(redList);
        two.receive(redList);
        three.receive(redList);

        // 显示多少钱
        manager.show(); // 80
        one.show(); // 6
        two.show(); // 8
        three.show(); // 6

    }
}
