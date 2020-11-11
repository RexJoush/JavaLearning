package com.joush.day07.demo09ContainsClass;

public class DemoMain {
    public static void main(String[] args) {
        // 创建一个角色
        Hero hero = new Hero();
        // 起名，设置年龄
        hero.setName("盖伦");
        hero.setAge(20);

        // 创建一个武器
        Weapon weapon = new Weapon("霜之哀伤");
        hero.setWeapon(weapon);

        hero.attack();
    }
}
