package com.streamApp.toolkit.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.streamApp.toolkit.entities.Game;
import com.streamApp.toolkit.service.GameService;
import com.streamApp.toolkit.testutils.TestFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;


@ExtendWith(MockitoExtension.class)
class GameControllerTest {

  @Mock
  private GameService gameService;

  @InjectMocks
  private GameController gameController;

  private static FixtureMonkey FIXTURE_MONKEY = TestFixtures.FIXTURE_MONKEY;

  private static final int SAMPLE_LIST_SIZE = 3;

  @Test
  void testGetGames_shouldReturnListFromService() {
    // Given
    List<Game> expectedGames = FIXTURE_MONKEY.giveMe(Game.class, SAMPLE_LIST_SIZE);
    when(gameService.getGames()).thenReturn(expectedGames);

    // When
    List<Game> result = gameController.getGames();

    // Then
    assertEquals(expectedGames, result);
    verify(gameService, times(1)).getGames();
  }

  @Test
  void testAddGame_shouldReturnSavedGame() {
    // Given
    Game gameToSave = FIXTURE_MONKEY.giveMeOne(Game.class);
    Game savedGame = FIXTURE_MONKEY.giveMeOne(Game.class);
    when(gameService.addGame(any(Game.class))).thenReturn(savedGame);

    // When
    ResponseEntity<Game> response = gameController.addGame(gameToSave);

    // Then
    assertEquals(savedGame, response.getBody());
    verify(gameService, times(1)).addGame(gameToSave);
  }

  @Test
  void testGetGame_shouldReturnGameWhenFound() {
    // Given
    Game expectedGame = FIXTURE_MONKEY.giveMeOne(Game.class);
    UUID gameId = expectedGame.getId();
    when(gameService.getGame(gameId)).thenReturn(expectedGame);

    // When
    ResponseEntity<Game> response = gameController.getGame(gameId);

    // Then
    assertEquals(expectedGame, response.getBody());
    verify(gameService, times(1)).getGame(gameId);
  }

  @Test
  void testGetGame_shouldReturnNullGameWhenNotFound() {
    // Given
    UUID gameId = UUID.randomUUID();
    when(gameService.getGame(gameId)).thenReturn(null);

    // When
    ResponseEntity<Game> response = gameController.getGame(gameId);

    // Then
    assertNull(response.getBody());
    verify(gameService, times(1)).getGame(gameId);
  }

  @Test
  void testGetGameByName_shouldReturnGameWhenFound() {
    // Given
    Game expectedGame = FIXTURE_MONKEY.giveMeOne(Game.class);
    String gameName = expectedGame.getName();
    when(gameService.getGameByName(gameName)).thenReturn(expectedGame);

    // When
    ResponseEntity<Game> response = gameController.getGameByName(gameName);

    // Then
    assertEquals(expectedGame, response.getBody());
    verify(gameService, times(1)).getGameByName(gameName);
  }

  @Test
  void testGetGameByName_shouldReturnNullGameWhenNotFound() {
    // Given
    String gameName = "Non-existent Game";
    when(gameService.getGameByName(gameName)).thenReturn(null);

    // When
    ResponseEntity<Game> response = gameController.getGameByName(gameName);

    // Then
    assertNull(response.getBody());
    verify(gameService, times(1)).getGameByName(gameName);
  }

  @Test
  void testGetGameByGenre_shouldReturnGameWhenFound() {
    // Given
    Game expectedGame = FIXTURE_MONKEY.giveMeBuilder(Game.class)
        .set("genreList", List.of("Action", "Adventure"))
        .sample();
    String genre = "Action";
    when(gameService.getGameByGenre(genre)).thenReturn(expectedGame);

    // When
    ResponseEntity<Game> response = gameController.getGameByGenre(genre);

    // Then
    assertEquals(expectedGame, response.getBody());
    verify(gameService, times(1)).getGameByGenre(genre);
  }

  @Test
  void testGetGameByGenre_shouldReturnNullGameWhenNotFound() {
    // Given
    String genre = "Non-existent Genre";
    when(gameService.getGameByGenre(genre)).thenReturn(null);

    // When
    ResponseEntity<Game> response = gameController.getGameByGenre(genre);

    // Then
    assertNull(response.getBody());
    verify(gameService, times(1)).getGameByGenre(genre);
  }

  @Test
  void testAddGameWithNullValues_shouldReturnGame() {
    // Given
    Game gameWithNulls = FIXTURE_MONKEY.giveMeBuilder(Game.class)
        .setNull("genreList")
        .sample();
    when(gameService.addGame(any(Game.class))).thenReturn(gameWithNulls);

    // When
    ResponseEntity<Game> response = gameController.addGame(gameWithNulls);

    // Then
    assertEquals(gameWithNulls, response.getBody());
    verify(gameService, times(1)).addGame(gameWithNulls);
  }

  @Test
  void testGetGames_shouldReturnEmptyListWhenNoGames() {
    // Given
    when(gameService.getGames()).thenReturn(List.of());

    // When
    List<Game> result = gameController.getGames();

    // Then
    assertTrue(result.isEmpty());
    verify(gameService, times(1)).getGames();
  }

  @Test
  void testUpdateGame_shouldReturnUpdatedGame() {
    // Given
    UUID gameId = UUID.randomUUID();
    Game gameToUpdate = FIXTURE_MONKEY.giveMeOne(Game.class);
    Game updatedGame = FIXTURE_MONKEY.giveMeOne(Game.class);
    when(gameService.updateGame(gameId, gameToUpdate)).thenReturn(updatedGame);

    // When
    ResponseEntity<Game> response = gameController.updateGame(gameId, gameToUpdate);

    // Then
    assertEquals(updatedGame, response.getBody());
    verify(gameService, times(1)).updateGame(gameId, gameToUpdate);
  }

  @Test
  void testUpdateGame_shouldReturnNullWhenGameNotFound() {
    // Given
    UUID gameId = UUID.randomUUID();
    Game gameToUpdate = FIXTURE_MONKEY.giveMeOne(Game.class);
    when(gameService.updateGame(gameId, gameToUpdate)).thenReturn(null);

    // When
    ResponseEntity<Game> response = gameController.updateGame(gameId, gameToUpdate);

    // Then
    assertNull(response.getBody());
    verify(gameService, times(1)).updateGame(gameId, gameToUpdate);
  }
} 