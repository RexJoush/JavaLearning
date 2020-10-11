package com.project.Demo03DouDiZhu;

import java.util.ArrayList;
import java.util.Collections;

/*
    斗地主综合案例
    1.准备牌
    2.洗牌
    3.发牌
    4.看牌
 */
public class DouDiZhu {
    public static void main(String[] args) {
        // 1.准备牌
        ArrayList<String> poker = new ArrayList<>();
        String[] colors = {"红桃","黑桃","方块","梅花"};
        String[] numbers = {"2","A","K","Q","J","10","9","8","7","6","5","4","3"};

        // 先把大王小王加入
        poker.add("大王");
        poker.add("小王");
        // 循环添加普通牌
        for (String color : colors){
            for (String number : numbers){
                poker.add(color + number);
            }
        }
        System.out.println(poker);

        // 2.洗牌
        // 使用 Collection 的工具类 Collections 进行集合的打乱
        Collections.shuffle(poker);
        System.out.println(poker);

        // 3.发牌
        ArrayList<String> play01 = new ArrayList<>();
        ArrayList<String> play02 = new ArrayList<>();
        ArrayList<String> play03 = new ArrayList<>();
        ArrayList<String> diPai = new ArrayList<>();

        /*
            遍历 poker 集合，获取然后发给每一个玩家
         */

        for (int i = 0; i < poker.size(); i++) {
            String p = poker.get(i);
            if (i >= 51){
                diPai.add(poker.get(i));
            }
            else if (i % 3 == 0){
                play01.add(poker.get(i));
            }else if (i % 3 == 1){
                play02.add(poker.get(i));
            }else if (i % 3 == 2) {
                play03.add(poker.get(i));
            }
        }

        // 4.看牌
        System.out.println("底牌： " + diPai);
        System.out.println("玩家1： " + play01);
        System.out.println("玩家2： " + play02);
        System.out.println("玩家3： " + play03);

    }
}
