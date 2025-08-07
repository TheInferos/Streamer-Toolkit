package com.stream_app.toolkit.controllers;

import com.stream_app.toolkit.entities.Game;
import com.stream_app.toolkit.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/game")
@CrossOrigin(origins = "*")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/all")
    public List<Game> getGames() {
        return gameService.getGames();
    }

    @PostMapping("/add")
    public ResponseEntity<Game> addGame(@RequestBody Game game) {
        Game savedGame = gameService.addGame(game);
        return ResponseEntity.ok(savedGame);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> getGame(@PathVariable UUID id) {
        Game game = gameService.getGame(id);
        return ResponseEntity.ok(game);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Game> getGameByName(@PathVariable String name) {
        Game game = gameService.getGameByName(name);
        return ResponseEntity.ok(game);
    }

    @GetMapping("/genre")
    public ResponseEntity<Game> getGameByGenre(@RequestParam String genre) {
        Game game = gameService.getGameByGenre(genre);
        return ResponseEntity.ok(game);
    }
} 