package com.stream_app.toolkit.service;

import com.stream_app.toolkit.entities.Viewer;
import com.stream_app.toolkit.repositories.ViewerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service

public class ViewerService {
    private final ViewerRepository viewerRepository;

    @Autowired
    public ViewerService(ViewerRepository viewerRepository) {
        this.viewerRepository = viewerRepository;
    }

    public List<Viewer> getAllViewers() {
        return viewerRepository.getAllViewers();
    }

    public Viewer addViewer( Viewer viewer) {
        return viewerRepository.save(viewer);
    }

    public Viewer getViewer (UUID id) {
        return viewerRepository.getViewer(id);
    }

}
