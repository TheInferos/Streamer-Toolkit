package com.streamApp.toolkit.service;

import com.streamApp.toolkit.entities.Game;
import com.streamApp.toolkit.entities.Stream;
import com.streamApp.toolkit.repositories.StreamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class StreamService {

  private final StreamRepository streamRepository;

  @Autowired
  public StreamService(final StreamRepository streamRepository) {
    this.streamRepository = streamRepository;
  }

  public List<Stream> getStreams() {
    return streamRepository.findAll();
  }

  public Stream addStream(final Stream stream) {
    return streamRepository.save(stream);
  }

  public Stream getStream(final UUID id) {
    return streamRepository.findById(id).orElse(null);
  }

  public List<Game> getStreamGames(final UUID streamId) {
    Stream stream = getStream(streamId);
    if (stream == null) {
      return new ArrayList<>();
    }
    return stream.getGames() != null ? stream.getGames() : new ArrayList<>();
  }

  public Stream addGamesToStream(final UUID streamId, final List<Game> games) {
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

  public Stream removeGamesFromStream(final UUID streamId, final List<UUID> gameIds) {
    Stream stream = getStream(streamId);
    if (stream == null || stream.getGames() == null) {
      return stream;
    }

    stream.getGames().removeIf(game -> gameIds.contains(game.getId()));
    return streamRepository.save(stream);
  }

  public Stream updateStream(final UUID id, final Stream stream) {
    Stream existingStream = getStream(id);
    if (existingStream == null) {
      return null;
    }
    existingStream.setName(stream.getName());
    existingStream.setDescription(stream.getDescription());
    return streamRepository.save(existingStream);
  }
}
