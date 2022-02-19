package com.example.springdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/wallpaper")
public class WallPaperController {

    @GetMapping
    public String getWallPaperUrl() {
        RestTemplate restTemplate = new RestTemplate();
        String forObject = restTemplate.getForObject("https://cn.bing.com/HPImageArchive.aspx?format=js&idx=0&n=1&mkt=zh-CN", String.class);
        System.out.println(forObject);
        return forObject;
    }
}
