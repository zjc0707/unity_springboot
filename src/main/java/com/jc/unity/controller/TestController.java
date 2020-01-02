package com.jc.unity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zjc
 * @date 2019/11/19
 */
@RestController
@RequestMapping("")
public class TestController {
    @GetMapping("/test")
    public String test(){
        return "success";
    }
}
