package com.stream_app.toolkit.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stream_app.toolkit.entities.Game;
import com.stream_app.toolkit.repositories.GameRepository;

@Service
public class GameService {
    
    private final GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> getGames() {
        return gameRepository.findAll();
    }

    public Game addGame(Game game) {
        return gameRepository.save(game);
    }

    public Game getGame(UUID id) {
       return gameRepository.findById(id).orElse(null);
    }

    public Game getGameByName(String name) {
        return gameRepository.findByName(name);
    }

    public Game getGameByGenre(String genre) {
        return gameRepository.findByGenreListContaining(genre);
    }
} 