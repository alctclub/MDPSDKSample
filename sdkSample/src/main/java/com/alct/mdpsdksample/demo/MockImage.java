package com.alct.mdpsdksample.demo;

import android.annotation.SuppressLint;

import com.alct.mdp.model.Image;
import com.alct.mdpsdksample.constant.FileBase64Constant;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MockImage {

    @SuppressLint("SimpleDateFormat")
    public static Image init(String fileName) {
        Image image = new Image();
        image.setFileName(fileName);
        image.setFileExt("jpeg");
        image.setFileData(FileBase64Constant.image);
        image.setLocation("中国上海市浦东新区郭守路498号");
        image.setBaiduLatitude(31.2169490751);
        image.setBaiduLongitude(121.6060207284);
        image.setImageTakenDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date()));

        return image;
    }
}