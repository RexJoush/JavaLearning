package com.joush.proxy;

/**
 * 一个生产者
 */
public class Producer implements IProducer {


    /**
     * 销售
     * @param money
     */
    public void saleProduct(double money){
        System.out.println("卖电脑，拿钱 " + money);
    }

    /**
     * 售后
     * @param money
     */
    public void afterService(double money){
        System.out.println("售后服务，拿钱 " + money);
    }

}
