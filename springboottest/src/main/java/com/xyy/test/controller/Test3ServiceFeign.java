package com.xyy.test.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


//client-3 是springboottest3的application.name
@FeignClient(name = "client-3")
public interface Test3ServiceFeign {

    @RequestMapping(value = "/getRestStr", method = RequestMethod.GET)
    String getRestStr(@RequestParam("str") String str);
}
