package com.joush.controller;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("user")
public class UserController {

    /**
     * 普通文件上传
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("fileUpload1")
    public String fileUpload1(HttpServletRequest request) throws Exception {
        System.out.println("普通文件上传");

        // 使用 fileupload 组件完成上传
        // 指定上传位置
        String path = request.getSession().getServletContext().getRealPath("/uploads/");

        System.out.println(path); // C:/Program Files/Apache Software Foundation/Tomcat 9.0/webapps/SpringMVCDemo03FileUpload/uploads/

        // 判断该路径是否存在
        File file = new File(path);

        // 不存在则创建
        if (!file.exists()){
            // 创建文件夹
            file.mkdirs();
        }

        // 解析 request 对象，获取上传文件项
        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(fileItemFactory);

        // 解析 request
        List<FileItem> fileItems = upload.parseRequest(request);

        // 遍历
        for (FileItem item : fileItems){
            // 判断当前 item 对象是否是上传文件项
            if (item.isFormField()){
                // 说明是普通表单项
            }
            else {
                // 说明是上传文件项

                // 获取上传文件名称
                String fileName = item.getName();

                // 把文件的名称设置唯一值，uuid
                String uuid = UUID.randomUUID().toString().replace("-", "");
                fileName = uuid + "_" + fileName;

                // 完成文件上传
                item.write(new File(path, fileName));

                // 删除临时文件
                item.delete();
            }
        }

        return "success";
    }

    /**
     * spring mvc 文件上传
     * @param request
     * @param upload
     * @return
     * @throws Exception
     */
    @RequestMapping("fileUpload2")
                                                                       // 此处名字必须和上传文件的表单名字一样
    public String fileUpload2(HttpServletRequest request, MultipartFile upload) throws Exception {
        System.out.println("spring mvc 文件上传");

        // 使用 fileupload 组件完成上传
        // 指定上传位置
        String path = request.getSession().getServletContext().getRealPath("/uploads/");
        System.out.println(path);  // C:\Program Files\Apache Software Foundation\Tomcat 9.0\webapps\SpringMVCDemo03FileUpload

        // 判断该路径是否存在
        File file = new File(path);

        // 不存在则创建
        if (!file.exists()){
            // 创建文件夹
            file.mkdirs();
        }

        /*
            说明上传文件项
         */
        // 获取上传文件名称
        String filename = upload.getOriginalFilename();

        // 把文件的名称设置唯一值，uuid
        String uuid = UUID.randomUUID().toString().replace("-", "");
        filename = uuid + "_" + filename;


        // 完成文件上传
        upload.transferTo(new File(path, filename));

        return "success";
    }

    /**
     * 跨服务器文件上传
     * @param upload
     * @return
     * @throws Exception
     */
    @RequestMapping("fileUpload3")
    public String fileUpload3(MultipartFile upload) throws Exception {
        System.out.println("跨服务器文件上传");

        // 定义服务器上传路径
        String path = "http://localhost:8081/SpringMVCDemo04FileUploadServer/uploads/";

        /*
            说明上传文件项
         */
        // 获取上传文件名称
        String filename = upload.getOriginalFilename();

        // 把文件的名称设置唯一值，uuid
        String uuid = UUID.randomUUID().toString().replace("-", "");
        filename = uuid + "_" + filename;

        // 完成文件上传，跨服务器

        // 1.创建客户端对象
        Client client = Client.create();

        // 2.和图片服务器进行连接
        WebResource webResource = client.resource(path + filename);// 因为 path后面加了 / 所以此处不用拼接 /

        // 3.上传文件
        webResource.put(upload.getBytes());

        return "success";
    }

}
