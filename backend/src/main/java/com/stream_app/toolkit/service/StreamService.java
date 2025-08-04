package com.stream_app.toolkit.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stream_app.toolkit.entities.Stream;
import com.stream_app.toolkit.repositories.StreamRepository;

@Service
public class StreamService {
    
    private final StreamRepository streamRepository;

    @Autowired
    public StreamService(StreamRepository streamRepository) {
        this.streamRepository = streamRepository;
    }

    public List<Stream> getStreams() {
        return streamRepository.findAll();
    }

    public Stream addStream(Stream stream) {
        return streamRepository.save(stream);
    }

    public Stream getStream(UUID id) {
       return streamRepository.findById(id).orElse(null);
    }
}
