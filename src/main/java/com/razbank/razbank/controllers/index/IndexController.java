package com.razbank.razbank.controllers.index;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String showLanding() {
        return "index";
    }
}
