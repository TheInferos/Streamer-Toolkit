package com.streamApp.toolkit.service;

import com.streamApp.toolkit.entities.Viewer;
import com.streamApp.toolkit.repositories.ViewerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ViewerService {

  private final ViewerRepository viewerRepository;

  @Autowired
  public ViewerService(final ViewerRepository viewerRepository) {
    this.viewerRepository = viewerRepository;
  }

  public List<Viewer> getAllViewers() {
    return viewerRepository.getAllViewers();
  }

  public Viewer addViewer(final Viewer viewer) {
    return viewerRepository.save(viewer);
  }

  public Viewer getViewer(final UUID id) {
    return viewerRepository.getViewer(id);
  }

  public Viewer updateViewer(final UUID id, final Viewer viewer) {
    Viewer existingViewer = getViewer(id);
    if (existingViewer == null) {
      return null;
    }
    existingViewer.setName(viewer.getName());
    return viewerRepository.save(existingViewer);
  }
}
