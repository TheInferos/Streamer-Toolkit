package com.stream_app.toolkit.entities;

import com.navercorp.fixturemonkey.FixtureMonkey;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private static FixtureMonkey FIXTURE_MONKEY = FixtureMonkey.create();

    @Test
    void testGameCreation_ShouldCreateValidGame() {
        // Given
        UUID id = UUID.randomUUID();
        String name = "Test Game";
        List<String> genres = List.of("Action", "Adventure");

        // When
        Game game = new Game(id, name, genres);

        // Then
        assertEquals(id, game.getId());
        assertEquals(name, game.getName());
        assertEquals(genres, game.getGenreList());
    }

    @Test
    void testGameWithNullId_ShouldAssignIdOnPrePersist() {
        // Given
        String name = "Test Game";
        List<String> genres = List.of("Action", "Adventure");

        // When
        Game game = new Game(null, name, genres);
        game.assignId(); // Manually call the @PrePersist method

        // Then
        assertNotNull(game.getId());
        assertEquals(name, game.getName());
        assertEquals(genres, game.getGenreList());
    }

    @Test
    void testGameWithExistingId_ShouldNotChangeIdOnPrePersist() {
        // Given
        UUID existingId = UUID.randomUUID();
        String name = "Test Game";
        List<String> genres = List.of("Action", "Adventure");

        // When
        Game game = new Game(existingId, name, genres);
        game.assignId(); // Manually call the @PrePersist method

        // Then
        assertEquals(existingId, game.getId()); // ID should remain unchanged
        assertEquals(name, game.getName());
        assertEquals(genres, game.getGenreList());
    }

    @Test
    void testGameWithNullValues_ShouldHandleGracefully() {
        // Given
        UUID id = UUID.randomUUID();
        String name = "Test Game";

        // When
        Game game = new Game(id, name, null);

        // Then
        assertEquals(id, game.getId());
        assertEquals(name, game.getName());
        assertNull(game.getGenreList());
    }


    @Test
    void testGameEquality_ShouldWorkCorrectly() {
        // Given
        UUID id = UUID.randomUUID();
        String name = "Test Game";
        List<String> genres = List.of("Action", "Adventure");

        Game game1 = new Game(id, name, genres);
        Game game2 = new Game(id, name, genres);

        // When & Then
        assertEquals(game1, game2);
        assertEquals(game1.hashCode(), game2.hashCode());
    }

    @Test
    void testGameInequality_ShouldWorkCorrectly() {
        // Given
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        String name = "Test Game";
        List<String> genres = List.of("Action", "Adventure");

        Game game1 = new Game(id1, name, genres);
        Game game2 = new Game(id2, name, genres);

        // When & Then
        assertNotEquals(game1, game2);
        assertNotEquals(game1.hashCode(), game2.hashCode());
    }

    @Test
    void testGameToString_ShouldContainAllFields() {
        // Given
        UUID id = UUID.randomUUID();
        String name = "Test Game";
        List<String> genres = List.of("Action", "Adventure");
        Game game = new Game(id, name, genres);

        // When
        String toString = game.toString();

        // Then
        assertTrue(toString.contains(id.toString()));
        assertTrue(toString.contains(name));
        assertTrue(toString.contains("Action"));
        assertTrue(toString.contains("Adventure"));
    }

    @Test
    void testGameWithFixtureMonkey_ShouldCreateValidGame() {
        // Given & When
        Game game = FIXTURE_MONKEY.giveMeOne(Game.class);
        
        // If the ID is null, manually trigger the @PrePersist method
        if (game.getId() == null) {
            game.assignId();
        }

        // Then
        assertNotNull(game);
        assertNotNull(game.getId());
        assertNotNull(game.getName());
    }

    @Test
    void testGameBuilder_ShouldCreateGameWithSpecificValues() {
        // Given
        UUID id = UUID.randomUUID();
        String name = "Custom Game";

        // When
        Game game = FIXTURE_MONKEY.giveMeBuilder(Game.class)
                .set("id", id)
                .set("name", name)
                .sample();

        // Then
        assertEquals(id, game.getId());
        assertEquals(name, game.getName());
    }
} 