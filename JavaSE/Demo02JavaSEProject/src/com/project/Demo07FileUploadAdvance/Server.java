package com.project.Demo07FileUploadAdvance;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Server {
    public static void main(String[] args) throws IOException {
        // 1.创建服务器 ServerSocket 对象
        ServerSocket server = new ServerSocket(8888);

        /*
            让服务器一直处于监听状态（死循环 accept 方法 ）
         */

        while (true){
            // 2.使用 accept 获取请求客户端 Socket 对象
            Socket socket = server.accept();

            /*
                使用多线程提高效率
                有一个客户端就开启线程完成文件上传
             */
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // 完成文件上传
                        // 3.使用 getInputStream 获取网络输入流对象
                        InputStream is = socket.getInputStream();

                        // 4.判断路径是否存在，不存在则创建，创建本地字节输出流，绑定网络输入流的目的地
                        File file = new File("d:\\upload");
                        if (!file.exists()){
                            file.mkdirs();
                        }

                        /*
                        自定义一个文件名，毫秒值加随机数
                        */
                        String fileName = System.currentTimeMillis() + new Random().nextInt(999999) + ".txt";

                        FileOutputStream fos = new FileOutputStream(file + "\\" + fileName);

                        // 5.使用 read 读取客户端上传的文件
                        byte[] bytes = new byte[1024];
                        int len = 0;
                        while ((len = is.read(bytes)) != -1){
                            // 6.使用本地输出流写入文件
                            fos.write(bytes,0,len);
                        }

                        // 7.给客户端回写数据
                        OutputStream os = socket.getOutputStream();
                        os.write("上传成功".getBytes());
                        // 8.释放资源
                        fos.close();
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
