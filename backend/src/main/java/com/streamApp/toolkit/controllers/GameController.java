package com.streamApp.toolkit.controllers;

import com.streamApp.toolkit.entities.Game;
import com.streamApp.toolkit.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "*")
public class GameController {

  private final GameService gameService;

  @Autowired
  public GameController(final GameService gameService) {
    this.gameService = gameService;
  }

  @GetMapping("/all")
  public List<Game> getGames() {
    return gameService.getGames();
  }

  @PostMapping("/add")
  public ResponseEntity<Game> addGame(@RequestBody final Game game) {
    Game savedGame = gameService.addGame(game);
    return ResponseEntity.ok(savedGame);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Game> getGame(@PathVariable final UUID id) {
    Game game = gameService.getGame(id);
    return ResponseEntity.ok(game);
  }

  @GetMapping("/name/{name}")
  public ResponseEntity<Game> getGameByName(@PathVariable final String name) {
    Game game = gameService.getGameByName(name);
    return ResponseEntity.ok(game);
  }

  @GetMapping("/genre")
  public ResponseEntity<Game> getGameByGenre(@RequestParam final String genre) {
    Game game = gameService.getGameByGenre(genre);
    return ResponseEntity.ok(game);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Game> updateGame(@PathVariable final UUID id, @RequestBody final Game game) {
    Game updatedGame = gameService.updateGame(id, game);
    return ResponseEntity.ok(updatedGame);
  }
} 