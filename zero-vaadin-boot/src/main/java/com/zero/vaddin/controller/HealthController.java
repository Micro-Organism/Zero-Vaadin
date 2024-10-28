package com.zero.vaddin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 注意： 此处不可以执行直接使用 “/” 路由，因为加载view时候需要这个路由被占用了
 */
@RestController
@RequestMapping("/health")
public class HealthController {

    @RequestMapping("/")
    public Map<String, Object> checkHealth() {
        Map<String, Object> map = new HashMap<>();
        map.put("code", 200);
        map.put("status", "success");
        map.put("message", "Welcome to Service, please visit http://localhost:8080/vaadin");
        map.put("result", "服务正常运行中, 请访问 http://localhost:8080/vaadin");
        return map;
    }

}