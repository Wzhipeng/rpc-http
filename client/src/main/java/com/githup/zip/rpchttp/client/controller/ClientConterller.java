package com.githup.zip.rpchttp.client.controller;

import com.githup.zip.rpchttp.client.service.OneService;
import com.githup.zip.rpchttp.client.service.TwoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ClientConterller {

    @Autowired
    private OneService oneService;

    @Autowired
    private TwoService twoService;

    @RequestMapping("/getOne1")
    public String getOneQuery1() {
        return oneService.query1(1);
    }

    @RequestMapping("/getOne2")
    public String getOneQuery2() {
        Map<String, Object> params = new HashMap<>();
        params.put("haha", "heihei");
        return oneService.query2(params).toString();
    }

    @RequestMapping("/getTwo1")
    public String getTwo1() {
        return twoService.test1();
    }

    @RequestMapping("/getTwo2")
    public String getTwo2() {
        Map<String, String> params = new HashMap<>();
        params.put("haha", "heihei");
        return twoService.test2(params).toString();
    }

}
