package com.joush.day05.demo04Override;

public class NewPhone extends Phone {
    @Override
    public void show() {
        super.show(); // 把父类的 show() 拿来使用
        System.out.println("显示姓名");
        System.out.println("显示头像");
    }
}
