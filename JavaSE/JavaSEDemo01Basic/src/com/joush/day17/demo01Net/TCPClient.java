package com.joush.day17.demo01Net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/*
    TCP 通信的客户端，向服务器发送请求，给服务器发送数据，读取服务器回写的数据
    表示客户端的类
        java.net.Socket 此类实现客户端套接字，套接字，包含了 ip 地址和端口号
    构造方法
        Socket(String host, int port); 创建一个套接字，并将其连接到指定主机上的指定端口号
        参数：
            String host，服务器主机的名称/IP地址
            int port，服务器端口号
    成员方法：
        OutputStream getOutputStream(); 返回此套接字的输出流
        InputStream getInputStream(); 返回此套接字的输入流
        void close(); 关闭套接字
    实现步骤：
        1.创建一个客户端对象 Socket，构造方法绑定服务器的 IP 地址和端口号
        2.使用 Socket 对象的方法 getOutputStream() 获取网络字节输出流 OutputStream 对象
        3.使用网络字节输出流 OutputStream 对象中的方法 write，给服务器发送数据
        4.使用 Socket 对象中的方法 getInputStream() 获取网络字节输入流对象 InputStream 对象
        5.使用 InputStream 中的方法 read，读取服务器返回的数据
        6.释放资源
    注意：
        1.客户端与服务器进行交互，必须使用 Socket 提供的网络流，不能自定义
        2.当创建客户端对象 Socket 时，就会请求服务器和服务器三次握手创建连接
            如果服务器没有启动，抛出异常，java.net.ConnectException
            如果服务器启动，可以进行交互
 */
public class TCPClient {

    public static void main(String[] args) throws IOException {
        // 1.创建一个客户端对象 Socket
        Socket socket = new Socket("127.0.0.1",8888);

        // 2.使用 Socket 对象的方法 getOutputStream() 获取网络字节输出流 OutputStream 对象
        OutputStream os = socket.getOutputStream();

        // 3.使用网络字节输出流 OutputStream 对象中的方法 write，给服务器发送数据s
        os.write("你好服务器".getBytes());

        // 4.使用 Socket 对象中的方法 getInputStream() 获取网络字节输入流对象 InputStream 对象
        InputStream is = socket.getInputStream();

        // 5.使用 InputStream 中的方法 read，读取服务器返回的数据
        byte[] bytes = new byte[1024];
        int len = is.read(bytes);
        System.out.println((new String(bytes, 0, len)));

        // 6.释放资源
        socket.close();
        is.close();
    }

}
