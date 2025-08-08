package com.streamApp.toolkit.service;

import com.streamApp.toolkit.entities.Game;
import com.streamApp.toolkit.repositories.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GameService {

  private final GameRepository gameRepository;

  @Autowired
  public GameService(final GameRepository gameRepository) {
    this.gameRepository = gameRepository;
  }

  public List<Game> getGames() {
    return gameRepository.findAll();
  }

  public Game addGame(final Game game) {
    return gameRepository.save(game);
  }

  public Game getGame(final UUID id) {
    return gameRepository.findById(id).orElse(null);
  }

  public Game getGameByName(final String name) {
    return gameRepository.findByName(name).orElse(null);
  }

  public Game getGameByGenre(final String genre) {
    return gameRepository.findByGenreListContaining(genre).orElse(null);
  }
} 