package com.example.demo.controller;

import com.example.demo.security.URLEncryption;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.security.NoSuchAlgorithmException;

@RestController
public class ShortURLController {

    @PostMapping(value = "shorturl",consumes = MediaType.APPLICATION_JSON_VALUE,produces =MediaType.APPLICATION_JSON_VALUE)
    public String getShortURL(@RequestParam String url) throws NoSuchAlgorithmException {

        //url 判断
        if(null == url){
            return "a";
        }
        System.out.printf(url);
        //url
        url.getBytes();
        return URLEncryption.md5(url);
    }
    @GetMapping(value = "/image",produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getImage() throws Exception {

        File file = new File("D:\\dowload\\shaosiming.jpg");
        FileInputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes, 0, inputStream.available());
        return bytes;

    }

}
