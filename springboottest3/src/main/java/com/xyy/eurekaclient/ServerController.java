package com.xyy.eurekaclient;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerController {
    @RequestMapping("/getRestStr")
    private String getName(@RequestParam String str) {
        return "来自test3**" + str;
    }

    @GetMapping(value = "/tt", produces = "application/json") //application/json
    private String getTT() {
        return "[{\"com_id\":\"42fd49bb923dc561a4bdfbfcecd9436c\",\"symbol\":\"ub232\"},{\"com_id\":\"42fd49bb923dc561a4bdfbfcecd9436c\",\"symbol\":\"PP\"}]";
    }

}

