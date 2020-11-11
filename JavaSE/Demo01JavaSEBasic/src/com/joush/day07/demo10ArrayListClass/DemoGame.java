package com.joush.day07.demo10ArrayListClass;

public class DemoGame {
    public static void main(String[] args) {
        Hero hero = new Hero();
        hero.setName("艾希");

        // 1.设置英雄技能 -- 实现类
        Skill skill1 = new SkillImpl();
        hero.setSkill(skill1);

        // 2.设置英雄技能 -- 匿名内部类
        Skill skill2 = new Skill() {
            @Override
            public void use() {
                System.out.println("pia!pia!pia!");
            }
        };
        hero.setSkill(skill2);

        // 3.设置英雄技能 -- 匿名对象

        hero.setSkill(new Skill() {
            @Override
            public void use() {
                System.out.println("biu!pia!biu!pia!");
            }
        });

    }
}
