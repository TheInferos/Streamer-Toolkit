package com.stream_app.toolkit.controllers;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.stream_app.toolkit.entities.Game;
import com.stream_app.toolkit.service.GameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameControllerTest {

    @Mock
    private GameService gameService;

    @InjectMocks
    private GameController gameController;
    private static FixtureMonkey FIXTURE_MONKEY = FixtureMonkey.create();

    @Test
    void testGetGames_ShouldReturnListFromService() {
        // Given
        List<Game> expectedGames = FIXTURE_MONKEY.giveMe(Game.class, 3);
        when(gameService.getGames()).thenReturn(expectedGames);

        // When
        List<Game> result = gameController.getGames();

        // Then
        assertEquals(expectedGames, result);
        verify(gameService, times(1)).getGames();
    }

    @Test
    void testAddGame_ShouldReturnSavedGame() {
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
    void testGetGame_ShouldReturnGameWhenFound() {
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
    void testGetGame_ShouldReturnNullGameWhenNotFound() {
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
    void testGetGameByName_ShouldReturnGameWhenFound() {
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
    void testGetGameByName_ShouldReturnNullGameWhenNotFound() {
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
    void testGetGameByGenre_ShouldReturnGameWhenFound() {
        // Given
        Game expectedGame = FIXTURE_MONKEY.giveMeOne(Game.class);
        String genre = expectedGame.getGenreList().get(0);
        when(gameService.getGameByGenre(genre)).thenReturn(expectedGame);

        // When
        ResponseEntity<Game> response = gameController.getGameByGenre(genre);

        // Then
        assertEquals(expectedGame, response.getBody());
        verify(gameService, times(1)).getGameByGenre(genre);
    }

    @Test
    void testGetGameByGenre_ShouldReturnNullGameWhenNotFound() {
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
    void testAddGame_ShouldHandleNullValues() {
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
    void testGetGames_ShouldReturnEmptyListWhenNoGames() {
        // Given
        when(gameService.getGames()).thenReturn(List.of());

        // When
        List<Game> result = gameController.getGames();

        // Then
        assertTrue(result.isEmpty());
        verify(gameService, times(1)).getGames();
    }
} 