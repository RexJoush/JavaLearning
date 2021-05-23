package com.joush.controller;

/**
 * @author Rex Joush
 * @time 2021.05.10 14:37
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 文件上传
 */
@Slf4j
@RestController
public class FormTestController {

    /**
     * MultipartFile 自动封装上传过来的文件
     *
     * @param email 普通属性，email
     * @param name  普通属性，name
     * @param image 单文件
     * @param photos 多文件
     * @return 上传结果
     */
    @PostMapping("/upload")
    public String upload(@RequestParam("email") String email,   // 普通属性
                         @RequestParam("name") String name,
                         @RequestPart("image") MultipartFile image, // 单文件
                         @RequestPart("photos") MultipartFile[] photos // 多文件
    ) throws IOException {

        log.info("上传信息, email={}, name={}, image={}, photos={}",
                email, name, image.getSize(), photos.length);

        // 如果头像不为空，存储
        if (!image.isEmpty()) {
            // 保存到文件服务器，或者 OOM 对象存储服务器
            String originalFilename = image.getOriginalFilename();
            image.transferTo(new File("D:\\" + originalFilename));
        }

        // 存储多文件
        if (photos.length > 0) {
            for (MultipartFile photo : photos) {
                if (!photo.isEmpty()){
                    String filename = photo.getOriginalFilename();
                    photo.transferTo(new File("D:\\cache\\" + filename));
                }
            }
        }
        return "file upload success";
    }

}
