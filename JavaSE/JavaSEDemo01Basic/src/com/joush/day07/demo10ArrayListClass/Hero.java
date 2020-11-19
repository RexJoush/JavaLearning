package com.joush.day07.demo10ArrayListClass;

public class Hero {
    private String name; // 英雄名字
    private Skill skill; // 英雄技能

    public Hero() {

    }

    public Hero(String name, Skill skill) {
        this.name = name;
        this.skill = skill;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }
}
