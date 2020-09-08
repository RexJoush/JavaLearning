package com.project.Demo04FindFile;

import java.io.File;
import java.io.FileFilter;

/*
    创建过滤器 FileFilter 的实现类，重写过滤方法 accept，定义过滤规则
 */
public class FileFilterImpl implements FileFilter {
    @Override
    public boolean accept(File pathname) {
        // if pathname 是文件夹，返回 true，继续遍历这个文件夹

        if (pathname.isDirectory()){
            return true;
        }

        return pathname.getName().toLowerCase().endsWith("docx");

    }
}
