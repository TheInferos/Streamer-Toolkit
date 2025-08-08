package com.streamApp.toolkit.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class BaseController {

  public BaseController() {
    // Default constructor
  }

  @GetMapping("/hello")
  public String getMessages() {
    return "Hello it's BunBun";
  }

  @GetMapping("/status")
  public String getStream() {
    return "Getting Milk";
  }
}
