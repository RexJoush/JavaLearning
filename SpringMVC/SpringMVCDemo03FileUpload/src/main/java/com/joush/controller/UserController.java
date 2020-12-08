package com.joush.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

@Controller
@RequestMapping("user")
public class UserController {

    @RequestMapping("fileUpload1")
    public String fileUpload1(HttpServletRequest request) throws Exception {
        System.out.println("文件上传");

        // 使用 fileupload 组件完成上传
        // 指定上传位置
        String path = request.getSession().getServletContext().getRealPath("/uploads/");

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
                // 说明普通表单项
            }
            else {
                // 说明上传文件项

                // 获取上传文件名称
                String fileName = item.getName();

                // 完成文件上传
                item.write(new File(path, fileName));

                // 删除临时文件
                item.delete();
            }
        }

        return "success";
    }

}
