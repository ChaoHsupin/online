package com.example.online;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class test {
 @RequestMapping(value = "/hello",method = RequestMethod.GET)
    public String hello(){
     return "hh you are zz!";
 }
}
