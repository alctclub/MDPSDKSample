package com.alct.mdpsdksample.demo;

import android.annotation.SuppressLint;

import com.alct.mdp.model.Location;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MockLocation {

    @SuppressLint("SimpleDateFormat")
    public static Location init() {
        Location location = new Location();
        location.setLocation("中国上海市浦东新区郭守路498号");
        location.setBaiduLatitude(31.2169490751);
        location.setBaiduLongitude(121.6060207284);
        location.setTime(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date()));

        return location;
    }
}