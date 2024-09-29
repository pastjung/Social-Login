package com.inha.springbootapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.cdimascio.dotenv.Dotenv;

// 컨트롤러에서 CORS를 개별적으로 설정하는 방법
// @CrossOrigin(origins = "http://localhost:3001") // React 애플리케이션의 URL
@RestController
@RequestMapping("/api")
public class HelloController {

    Dotenv dotenv = Dotenv.load();
    private final String reactURL = "http://localhost:" + dotenv.get("REACT_HOST_PORT");

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello: " + reactURL;
    }
}