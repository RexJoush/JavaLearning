package com.joush.proxy;


public interface IProducer {
    /**
     * 销售
     * @param money
     */
    void saleProduct(double money);

    /**
     * 售后
     * @param money
     */
    void afterService(double money);
}
