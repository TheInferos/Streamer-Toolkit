package com.stream_app.toolkit.controllers;

import com.stream_app.toolkit.entities.Viewer;
import com.stream_app.toolkit.service.ViewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/viewer")
public class ViewerController {

    private final ViewerService viewerService;

    @Autowired
    public ViewerController(ViewerService viewerService) {
        this.viewerService = viewerService;
    }

    @GetMapping("/all")
    public List<Viewer> getViewers() {
        return viewerService.getAllViewers();
    }

    @PostMapping("/add")
    public Viewer addViewer(@RequestBody Viewer viewer) {
        return viewerService.addViewer(viewer);
    }
}