package com.stream_app.toolkit.controllers;

import org.springframework.web.bind.annotation.*;

        import java.util.*;

@RestController
@RequestMapping("/api")
public class BaseController {

    @GetMapping("/hello")
    public String getMessages() {
        return "Hello it's BunBun";
    }

    @GetMapping("/status")
    public String getStream() {
        return "Getting Milk";
    }
}