package com.project.Demo02LaptopInterface;

public class Laptop {

    public void powerOn(){
        System.out.println("开机");
    }

    public void powerOff(){
        System.out.println("关机");
    }

    // 使用 USB 接口的方法，使用接口作为参数
    public void useDevice(USB usb){
        usb.open();

        if (usb instanceof Mouse){
            Mouse mouse = (Mouse) usb;
            mouse.click();
        }
        else if (usb instanceof Keyboard){
            Keyboard keyboard = (Keyboard) usb;
            keyboard.type();
        }

        usb.close();
    }
}
