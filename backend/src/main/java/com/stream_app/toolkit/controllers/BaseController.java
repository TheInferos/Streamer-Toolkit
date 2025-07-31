package com.stream_app.toolkit.controllers;

import org.springframework.web.bind.annotation.*;

        import java.util.*;

@RestController
@RequestMapping("/api")
public class BaseController {

    @GetMapping
    @RequestMapping("/hello")
    public String getMessages() {
        return "Hello it's BunBun";
    }

    @GetMapping
    @RequestMapping("/status")
    public String getStream() {
        return "Getting Milk";
    }
}