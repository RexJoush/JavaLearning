package com.project.Demo07FileUploadAdvance;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws IOException {
        // 1.创建本地字节数日前，绑定数据源，同时创建 Socket 对象，绑定 ip 和端口号
        FileInputStream fis = new FileInputStream("D:\\WorkSpace\\Java\\day01\\src\\com\\project\\Demo06FileUploadAdvance\\a.txt");
        Socket socket = new Socket("127.0.0.1",8888);


        // 2.获取网络字节输出流对象
        OutputStream os = socket.getOutputStream();

        // 3.使用本地字节流 read 读取本地文件
        byte[] bytes = new byte[1024];
        int len = 0;
        while ((len = fis.read(bytes)) != -1){
            // 4.使用网络输出流 write 将文件上传
            os.write(bytes,0,len);
        }

        /*
            此处，因为读取到 -1 不会写入上传，所以服务器读取不结束，也不会回写，所以就陷入死锁，需要解决
            void shutdownOutput(); 禁用此套接字的输出流
            对于 TCP 套接字，任何以前写入的数据都被发送，并且后跟 TCP 的正常连续终止序列
         */

        socket.shutdownOutput();

        // 5.使用 getInputStream 获取网络字节输入流对象，并使用 read 方法读取服务器写回的数据
        InputStream is = socket.getInputStream();
        while ((len = is.read(bytes)) != -1){
            System.out.println(new String(bytes,0,len));
        }

        // 6.释放资源
        fis.close();
        //socket.close();
    }

}
