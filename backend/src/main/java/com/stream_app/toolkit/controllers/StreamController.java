package com.stream_app.toolkit.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stream_app.toolkit.entities.Stream;
import com.stream_app.toolkit.service.StreamService;

@RestController
@RequestMapping("/api/stream")
public class StreamController {

    private final StreamService streamService;

    @Autowired
    public StreamController(StreamService streamService) {
        this.streamService = streamService;
    }

    @GetMapping("/all")
    public List<Stream> getStreams() {
        return streamService.getStreams();
    }

    @PostMapping("/add")
    public ResponseEntity<Stream> addStream(@RequestBody Stream stream) {
        Stream savedStream = streamService.addStream(stream);
        return ResponseEntity.ok(savedStream);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stream> getStream(@PathVariable UUID id) {
        Stream stream = streamService.getStream(id);
        return ResponseEntity.ok(stream);
    }
}
