package com.joush.day17.demo01Net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/*
    TCP 通信的服务端，接受客户端的请求，读取客户端发送的数据，给客户端会回写数据
    表示服务器的类：
        java.net.ServerSocket，此类实现服务器套接字

    构造方法
        ServerSocket(int port); 创建绑定到特定端口的服务器套接字
    服务器必须知道哪个客户端请求的服务器
    使用 accept 方法获取到请求的客户端对象 Socket
    成员方法
        Socket accept(); 侦听并接收到此套接字的连接

    服务器实现步骤：
        1.创建服务器 ServerSocket 对象和系统要指定的端口号
        2.使用 ServerSocket 对象中的方法 accept 获取到请求的客户端对象 Socket
        3.使用 Socket 对象中的方法 getInputStream() 获取网络字节输入流对象 InputStream 对象
        4.使用 InputStream 中的方法 read，读取服务器返回的数据
        5.使用 Socket 对象的方法 getOutputStream() 获取网络字节输出流 OutputStream 对象
        6.使用网络字节输出流 OutputStream 对象中的方法 write，给客户端发送数据
        7.释放资源
 */
public class TCPServer {
    public static void main(String[] args) throws IOException {
        // 1.创建服务器 ServerSocket 对象和系统要指定的端口号
        ServerSocket server = new ServerSocket(8888);

        // 2.使用 ServerSocket 对象中的方法 accept 获取到请求的客户端对象 Socket
        Socket socket = server.accept();

        // 3.使用 Socket 对象中的方法 getInputStream() 获取网络字节输入流对象 InputStream 对象
        InputStream is = socket.getInputStream();

        // 4.使用 InputStream 中的方法 read，读取服务器返回的数据
        byte[] bytes = new byte[1024];
        int len = is.read(bytes);
        System.out.println(new String(bytes,0,len));

        // 5.使用 Socket 对象的方法 getOutputStream() 获取网络字节输出流 OutputStream 对象
        OutputStream os = socket.getOutputStream();

        // 6.使用网络字节输出流 OutputStream 对象中的方法 write，给客户端发送数据
        os.write("connected success".getBytes());

        // 7.释放资源
        socket.close();
        server.close();
    }
}
