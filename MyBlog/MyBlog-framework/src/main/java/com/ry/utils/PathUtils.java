package com.ry.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class PathUtils {
    public static String generateFilePath(String fileName){
        // 根据日期生成路径 2022/10/12
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
        String datePath = sdf.format(new Date());
        // uuid作为文件名
        String uuid = UUID.randomUUID().toString().replaceAll("-","");
        // 后缀名和文件后缀一致  test.jpg -> .jpg
        /**
         * substring该方法的含义是截取，从当前下标开始，去掉前面对应下标的字符，截取出后面的字符；
         * lastIndexOf的含义是获得int类型的值，值的大小为字符前面的文本数量（从0开始）。
         * 此两个方法一起使用，一般用于取文件后缀（.jpg/.txt等）
         */
        int index = fileName.lastIndexOf(".");
        String fileType = fileName.substring(index);
        return new StringBuffer().append(datePath).append(uuid).append(fileType).toString();
    }
}
