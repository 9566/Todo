package com.tap.spring_mvc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @RequestMapping("/vikram")
    String home(){
        return "Hellow world! vikram mallu sai reddy nellore andhra india ";
    }
}
