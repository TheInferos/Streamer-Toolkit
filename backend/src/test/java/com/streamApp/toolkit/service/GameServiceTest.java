package com.streamApp.toolkit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.streamApp.toolkit.entities.Game;
import com.streamApp.toolkit.repositories.GameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

  @Mock
  private GameRepository gameRepository;

  private static final int SAMPLE_LIST_SIZE = 3;

  @InjectMocks
  private GameService gameService;
  
  private static FixtureMonkey FIXTURE_MONKEY = FixtureMonkey.create();

  @Test
  void testGetGames_shouldReturnAllGames() {
    // Given
    List<Game> expectedGames = FIXTURE_MONKEY.giveMe(Game.class, SAMPLE_LIST_SIZE);
    when(gameRepository.findAll()).thenReturn(expectedGames);

    // When
    List<Game> result = gameService.getGames();

    // Then
    assertEquals(expectedGames, result);
    verify(gameRepository, times(1)).findAll();
  }

  @Test
  void testAddGame_shouldReturnSavedGame() {
    // Given
    Game gameToSave = FIXTURE_MONKEY.giveMeOne(Game.class);
    Game savedGame = FIXTURE_MONKEY.giveMeOne(Game.class);
    when(gameRepository.save(any(Game.class))).thenReturn(savedGame);

    // When
    Game result = gameService.addGame(gameToSave);

    // Then
    assertEquals(savedGame, result);
    verify(gameRepository, times(1)).save(gameToSave);
  }

  @Test
  void testGetGame_shouldReturnGameWhenFound() {
    // Given
    UUID gameId = UUID.randomUUID();
    Game expectedGame = FIXTURE_MONKEY.giveMeOne(Game.class);
    when(gameRepository.findById(gameId)).thenReturn(Optional.of(expectedGame));

    // When
    Game result = gameService.getGame(gameId);

    // Then
    assertEquals(expectedGame, result);
    verify(gameRepository, times(1)).findById(gameId);
  }

  @Test
  void testGetGame_shouldReturnNullWhenNotFound() {
    // Given
    UUID gameId = UUID.randomUUID();
    when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

    // When
    Game result = gameService.getGame(gameId);

    // Then
    assertNull(result);
    verify(gameRepository, times(1)).findById(gameId);
  }

  @Test
  void testGetGameByName_shouldReturnGameWhenFound() {
    // Given
    String gameName = "Test Game";
    Game expectedGame = FIXTURE_MONKEY.giveMeOne(Game.class);
    when(gameRepository.findByName(gameName)).thenReturn(Optional.of(expectedGame));

    // When
    Game result = gameService.getGameByName(gameName);

    // Then
    assertEquals(expectedGame, result);
    verify(gameRepository, times(1)).findByName(gameName);
  }

  @Test
  void testGetGameByName_shouldReturnNullWhenNotFound() {
    // Given
    String gameName = "Non-existent Game";
    when(gameRepository.findByName(gameName)).thenReturn(Optional.empty());

    // When
    Game result = gameService.getGameByName(gameName);

    // Then
    assertNull(result);
    verify(gameRepository, times(1)).findByName(gameName);
  }

  @Test
  void testGetGameByGenre_shouldReturnGameWhenFound() {
    // Given
    String genre = "Action";
    Game expectedGame = FIXTURE_MONKEY.giveMeOne(Game.class);
    when(gameRepository.findByGenreListContaining(genre)).thenReturn(Optional.of(expectedGame));

    // When
    Game result = gameService.getGameByGenre(genre);

    // Then
    assertEquals(expectedGame, result);
    verify(gameRepository, times(1)).findByGenreListContaining(genre);
  }

  @Test
  void testGetGameByGenre_shouldReturnNullWhenNotFound() {
    // Given
    String genre = "Non-existent Genre";
    when(gameRepository.findByGenreListContaining(genre)).thenReturn(Optional.empty());

    // When
    Game result = gameService.getGameByGenre(genre);

    // Then
    assertNull(result);
    verify(gameRepository, times(1)).findByGenreListContaining(genre);
  }

  @Test
  void testAddGameWithNullValues_shouldReturnGame() {
    // Given
    Game gameWithNulls = FIXTURE_MONKEY.giveMeBuilder(Game.class)
        .setNull("genreList")
        .sample();
    when(gameRepository.save(any(Game.class))).thenReturn(gameWithNulls);

    // When
    Game result = gameService.addGame(gameWithNulls);

    // Then
    assertEquals(gameWithNulls, result);
    verify(gameRepository, times(1)).save(gameWithNulls);
  }

  @Test
  void testGetGames_shouldReturnEmptyListWhenNoGames() {
    // Given
    when(gameRepository.findAll()).thenReturn(List.of());

    // When
    List<Game> result = gameService.getGames();

    // Then
    assertTrue(result.isEmpty());
    verify(gameRepository, times(1)).findAll();
  }
} 