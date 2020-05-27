package com.alevel.library.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class StartPageController {

    @GetMapping
    public String hello() {
        return "greeting";
    }
}
