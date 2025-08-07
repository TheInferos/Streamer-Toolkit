package com.stream_app.toolkit.service;

import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stream_app.toolkit.entities.Stream;
import com.stream_app.toolkit.entities.Game;
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

    public List<Game> getStreamGames(UUID streamId) {
        Stream stream = getStream(streamId);
        if (stream == null) {
            return new ArrayList<>();
        }
        return stream.getGames() != null ? stream.getGames() : new ArrayList<>();
    }

    public Stream addGamesToStream(UUID streamId, List<Game> games) {
        Stream stream = getStream(streamId);
        if (stream == null) {
            return null;
        }
        
        if (stream.getGames() == null) {
            stream.setGames(new ArrayList<>());
        }
        
        stream.getGames().addAll(games);
        return streamRepository.save(stream);
    }

    public Stream removeGamesFromStream(UUID streamId, List<UUID> gameIds) {
        Stream stream = getStream(streamId);
        if (stream == null || stream.getGames() == null) {
            return stream;
        }
        
        stream.getGames().removeIf(game -> gameIds.contains(game.getId()));
        return streamRepository.save(stream);
    }
}
