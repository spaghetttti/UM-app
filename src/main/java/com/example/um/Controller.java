package com.example.um;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/api/testing")
    public String index() {
        return "server is working";
    }

}
