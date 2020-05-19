package com.xyy.test.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 读取配置类
 */
@Component
public class ConfigBeanValue {
    @Value("${person.name}")
    public String name;

    @Value("${rocketmqaddr}")
    public String rocketmqaddr;
}

