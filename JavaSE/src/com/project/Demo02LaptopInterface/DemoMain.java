package com.project.Demo02LaptopInterface;

public class DemoMain {
    public static void main(String[] args) {
        // 首先创建一个电脑
        Laptop laptop = new Laptop();

        // 开机
        laptop.powerOn();

        // 准备一个鼠标
        //Mouse mouse = new Mouse();
        // -1 向上转型，上面也可
        USB usb = new Mouse();

        // 使用鼠标， 传递一个 USB类型
        laptop.useDevice(usb);

        // 创建一个USB键盘
        Keyboard keyboard = new Keyboard();
        // 方法参数是 USB 类型，传递的参数是实现类
        laptop.useDevice(keyboard); // 正确写法

        // 关机
        laptop.powerOff();
    }
}
