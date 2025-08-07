package com.stream_app.toolkit.service;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.stream_app.toolkit.entities.Game;
import com.stream_app.toolkit.repositories.GameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameService gameService;
    private static FixtureMonkey FIXTURE_MONKEY = FixtureMonkey.create();

    @Test
    void testGetGames_ShouldReturnAllGames() {
        // Given
        List<Game> expectedGames = FIXTURE_MONKEY.giveMe(Game.class, 3);
        when(gameRepository.findAll()).thenReturn(expectedGames);

        // When
        List<Game> result = gameService.getGames();

        // Then
        assertEquals(expectedGames, result);
        verify(gameRepository, times(1)).findAll();
    }

    @Test
    void testAddGame_ShouldReturnSavedGame() {
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
    void testGetGame_ShouldReturnGameWhenFound() {
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
    void testGetGame_ShouldReturnNullWhenNotFound() {
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
    void testGetGameByName_ShouldReturnGameWhenFound() {
        // Given
        String gameName = "Test Game";
        Game expectedGame = FIXTURE_MONKEY.giveMeOne(Game.class);
        when(gameRepository.findByName(gameName)).thenReturn(expectedGame);

        // When
        Game result = gameService.getGameByName(gameName);

        // Then
        assertEquals(expectedGame, result);
        verify(gameRepository, times(1)).findByName(gameName);
    }

    @Test
    void testGetGameByName_ShouldReturnNullWhenNotFound() {
        // Given
        String gameName = "Non-existent Game";
        when(gameRepository.findByName(gameName)).thenReturn(null);

        // When
        Game result = gameService.getGameByName(gameName);

        // Then
        assertNull(result);
        verify(gameRepository, times(1)).findByName(gameName);
    }

    @Test
    void testGetGameByGenre_ShouldReturnGameWhenFound() {
        // Given
        String genre = "Action";
        Game expectedGame = FIXTURE_MONKEY.giveMeOne(Game.class);
        when(gameRepository.findByGenreListContaining(genre)).thenReturn(expectedGame);

        // When
        Game result = gameService.getGameByGenre(genre);

        // Then
        assertEquals(expectedGame, result);
        verify(gameRepository, times(1)).findByGenreListContaining(genre);
    }

    @Test
    void testGetGameByGenre_ShouldReturnNullWhenNotFound() {
        // Given
        String genre = "Non-existent Genre";
        when(gameRepository.findByGenreListContaining(genre)).thenReturn(null);

        // When
        Game result = gameService.getGameByGenre(genre);

        // Then
        assertNull(result);
        verify(gameRepository, times(1)).findByGenreListContaining(genre);
    }

    @Test
    void testAddGame_ShouldHandleNullValues() {
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
    void testGetGames_ShouldReturnEmptyListWhenNoGames() {
        // Given
        when(gameRepository.findAll()).thenReturn(List.of());

        // When
        List<Game> result = gameService.getGames();

        // Then
        assertTrue(result.isEmpty());
        verify(gameRepository, times(1)).findAll();
    }
} 