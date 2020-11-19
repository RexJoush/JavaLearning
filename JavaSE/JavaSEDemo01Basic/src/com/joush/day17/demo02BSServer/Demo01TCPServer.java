package com.joush.day17.demo02BSServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/*
    创建 B/S 版本的 TCP 服务器
 */
public class Demo01TCPServer {
    public static void main(String[] args) throws IOException {
        // 1.创建一个服务器 ServerSocket 和系统指定的端口号
        ServerSocket server = new ServerSocket(8888);

        while (true){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 2.使用 accept 方法获取请求对象的客户端
                        Socket socket = server.accept();

                        // 3.使用 Socket 对象的方法 getInputStream，获取网络字节输入流 InputStream 对象
                        InputStream is = socket.getInputStream();

                        // 4.使用网络字节输入流的方法 read 读取客户端的发送的数据
                        /*byte[] bytes = new byte[1024];
                        int len = 0;
                        while ((len = is.read(bytes)) != -1){
                            System.out.println(new String(bytes,0,len));
                        }*/

                        // 把 is 转换为字符缓冲输入流
                        BufferedReader br = new BufferedReader(new InputStreamReader(is));
                        String line = br.readLine();

                        // 把读取的信息进行切割
                        String[] arr = line.split(" ");

                        // 把路径的 / 去掉
                        String htmlpath = arr[1].substring(1);

                        // 创建本地字节输入流，构造方法读取的 html 路径
                        FileInputStream fis = new FileInputStream(htmlpath);

                        // 使用 Socket 的方法 getOutputStream 获取网络字节输出流对象
                        OutputStream os = socket.getOutputStream();

                        // 写入 http 响应头
                        os.write("HTTP/1.1 200 OK\r\n".getBytes());
                        os.write("Content-Type:text/html\r\n".getBytes());
                        // 必须写入空行，否则浏览器不解析
                        os.write("\r\n".getBytes());

                        // 一读，一写复制文件，把读取的 html 协会客户端
                        byte[] bytes = new byte[1024];
                        int len = 0;
                        while ((len = fis.read(bytes)) != -1){
                            os.write(bytes,0,len);
                        }

                        // 释放资源
                        fis.close();
                        socket.close();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        // server.close();
    }
}
