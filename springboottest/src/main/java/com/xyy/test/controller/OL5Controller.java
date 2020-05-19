package com.xyy.test.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import com.xyy.test.config.ConfigBeanValue;
import com.xyy.test.entity.AircraftCarrier;
import com.xyy.test.entity.User;
import com.xyy.test.mapper.AircraftCarrierMapper;
import com.xyy.test.mapper.UserMapper;
import com.xyy.test.rocketmq.Consumer;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NacosPropertySource(dataId = "springboot2-nacos-config", autoRefreshed = true)
@Controller
public class OL5Controller {

    @Autowired
    private ConfigBeanValue configBeanValue;

    @Autowired
    private Consumer consumer;

    @Autowired
    private AircraftCarrierMapper aircraftCarrierMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    RedisTemplate redisTemplate;


    @ApiOperation(value = "获取所有user", tags = "xyy")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "名字", required = false, dataType = "String", defaultValue = "aaa"),
    })
    @GetMapping(value = {"/users"})
    public String getAllUser(Model model, @RequestParam(required = false) String name) {
        List<User> allUser = userMapper.getAllUser(name);
        model.addAttribute("name", allUser);

        return "yml";
    }


    //@PathVariable 可以不传的需求，用多url
    @RequestMapping(value = {"/index/{order}", "/index"})
    public String sayHello(Model model, @PathVariable(required = false) String order) {

        List<AircraftCarrier> allData = aircraftCarrierMapper.getAllData(order);
        model.addAttribute("emp", allData);

        return "index";
    }

    @RequestMapping(value = {"/redis"})
    public String testRedis(Model model) {


        redisTemplate.opsForValue().set("num", "123");
        String num = (String) redisTemplate.opsForValue().get("num");


        Map<String, Object> testMap = new HashMap();
        testMap.put("name", "六六");
        testMap.put("age", 11);
        testMap.put("class", "1");
        redisTemplate.opsForHash().putAll("redisHash1", testMap);
        Map redisHash1 = redisTemplate.opsForHash().entries("redisHash1");


        model.addAllAttributes(redisHash1);

        return "redis";
    }


    @RequestMapping(value = {"/yml"})
    public String testYml(Model model) throws Exception {
        model.addAttribute("name", configBeanValue.name);
        return "yml";
    }


    @Autowired
    Test3ServiceFeign test3ServiceFeign;

    @RequestMapping(value = "/getTest3", method = RequestMethod.GET)
    public String getTest3(Model model, @RequestParam(required = false) String s) {
        String name = test3ServiceFeign.getRestStr(s);
        model.addAttribute("name", name);
        return "yml";
    }


    @NacosValue(value = "${nacos.test.propertie:123}", autoRefreshed = true)
    private String testProperties;

    @RequestMapping(value = "/getNacos", method = RequestMethod.GET)
    public String getNacos(Model model) {
        model.addAttribute("name", testProperties);
        return "yml";
    }


}