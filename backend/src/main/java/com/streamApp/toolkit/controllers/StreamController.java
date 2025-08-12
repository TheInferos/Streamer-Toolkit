package com.streamApp.toolkit.controllers;

import com.streamApp.toolkit.entities.Game;
import com.streamApp.toolkit.entities.Stream;
import com.streamApp.toolkit.service.StreamService;
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
@RequestMapping("/api/stream")
@CrossOrigin(origins = "*")
public class StreamController {

  private final StreamService streamService;

  @Autowired
  public StreamController(final StreamService streamService) {
    this.streamService = streamService;
  }

  @GetMapping("/all")
  public List<Stream> getStreams() {
    return streamService.getStreams();
  }

  @PostMapping("/add")
  public ResponseEntity<Stream> addStream(@RequestBody final Stream stream) {
    Stream savedStream = streamService.addStream(stream);
    return ResponseEntity.ok(savedStream);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Stream> getStream(@PathVariable final UUID id) {
    Stream stream = streamService.getStream(id);
    return ResponseEntity.ok(stream);
  }

  @GetMapping("/{id}/games")
  public ResponseEntity<List<Game>> getStreamGames(@PathVariable final UUID id) {
    List<Game> games = streamService.getStreamGames(id);
    return ResponseEntity.ok(games);
  }

  @PostMapping("/{id}/games")
  public ResponseEntity<Stream> addGamesToStream(@PathVariable final UUID id, @RequestBody final List<Game> games) {
    Stream updatedStream = streamService.addGamesToStream(id, games);
    return ResponseEntity.ok(updatedStream);
  }

  @DeleteMapping("/{id}/games")
  public ResponseEntity<Stream> removeGamesFromStream(@PathVariable final UUID id, 
                                                      @RequestBody final List<UUID> gameIds) {
    Stream updatedStream = streamService.removeGamesFromStream(id, gameIds);
    return ResponseEntity.ok(updatedStream);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Stream> updateStream(@PathVariable final UUID id, @RequestBody final Stream stream) {
    Stream updatedStream = streamService.updateStream(id, stream);
    return ResponseEntity.ok(updatedStream);
  }
}
