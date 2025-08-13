package com.streamApp.toolkit.controllers;

import com.streamApp.toolkit.entities.Viewer;
import com.streamApp.toolkit.service.ViewerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/viewer")
@CrossOrigin(origins = "*")
public class ViewerController {

  private final ViewerService viewerService;

  @Autowired
  public ViewerController(final ViewerService viewerService) {
    this.viewerService = viewerService;
  }

  @GetMapping("/all")
  public List<Viewer> getViewers() {
    return viewerService.getAllViewers();
  }

  @PostMapping("/add")
  public Viewer addViewer(@RequestBody final Viewer viewer) {
    return viewerService.addViewer(viewer);
  }

  @GetMapping("/{id}")
  public Viewer getViewer(@PathVariable("id") final UUID id) {
    return viewerService.getViewer(id);
  }

  @PutMapping("/{id}")
  public Viewer updateViewer(@PathVariable final UUID id, @RequestBody final Viewer viewer) {
    return viewerService.updateViewer(id, viewer);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteViewer(@PathVariable final UUID id) {
    viewerService.deleteViewer(id);
    return ResponseEntity.noContent().build();
  }
}