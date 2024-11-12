package com.inha.springbootapp.domain.ping;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 컨트롤러에서 CORS를 개별적으로 설정하는 방법
// @CrossOrigin(origins = "http://localhost:3001") // React 애플리케이션의 URL
@RestController
@RequestMapping("/api")
public class HelloController {

    Dotenv dotenv = Dotenv.load();
    private final String reactURL = "http://" + dotenv.get("IP_ADDRESS") + ":" + dotenv.get("REACT_HOST_PORT");

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello: " + reactURL;
    }
}