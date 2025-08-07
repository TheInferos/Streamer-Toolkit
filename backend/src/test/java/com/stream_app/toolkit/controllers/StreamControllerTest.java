package com.stream_app.toolkit.controllers;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.stream_app.toolkit.entities.Stream;
import com.stream_app.toolkit.entities.Game;
import com.stream_app.toolkit.service.StreamService;
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
class StreamControllerTest {

    @Mock
    private StreamService streamService;

    @InjectMocks
    private StreamController streamController;
    private static FixtureMonkey FIXTURE_MONKEY = FixtureMonkey.create();

    @Test
    void testGetStreams_ShouldReturnListFromService() {
        // Given
        List<Stream> expectedStreams = FIXTURE_MONKEY.giveMe(Stream.class, 3);
        when(streamService.getStreams()).thenReturn(expectedStreams);

        // When
        List<Stream> result = streamController.getStreams();

        // Then
        assertEquals(expectedStreams, result);
        verify(streamService, times(1)).getStreams();
    }

    @Test
    void testAddStream_ShouldReturnSavedStream() {
        // Given
        Stream streamToSave = FIXTURE_MONKEY.giveMeOne(Stream.class);
        Stream savedStream = FIXTURE_MONKEY.giveMeOne(Stream.class);
        when(streamService.addStream(any(Stream.class))).thenReturn(savedStream);

        // When
        ResponseEntity<Stream> response = streamController.addStream(streamToSave);

        // Then
        assertEquals(savedStream, response.getBody());
        verify(streamService, times(1)).addStream(streamToSave);
    }

    @Test
    void testGetStream_ShouldReturnStreamWhenFound() {
        // Given
        UUID streamId = UUID.randomUUID();
        Stream expectedStream = FIXTURE_MONKEY.giveMeOne(Stream.class);
        when(streamService.getStream(streamId)).thenReturn(expectedStream);

        // When
        ResponseEntity<Stream> response = streamController.getStream(streamId);

        // Then
        assertEquals(expectedStream, response.getBody());
        verify(streamService, times(1)).getStream(streamId);
    }

    @Test
    void testGetStream_ShouldReturnNullStreamWhenNotFound() {
        // Given
        UUID streamId = UUID.randomUUID();
        when(streamService.getStream(streamId)).thenReturn(null);

        // When
        ResponseEntity<Stream> response = streamController.getStream(streamId);

        // Then
        assertNull(response.getBody());
        verify(streamService, times(1)).getStream(streamId);
    }

    @Test
    void testAddStream_ShouldHandleNullValues() {
        // Given
        Stream streamWithNulls = FIXTURE_MONKEY.giveMeBuilder(Stream.class)
                .setNull("description")
                .setNull("game")
                .setNull("tags")
                .setNull("categories")
                .sample();
        when(streamService.addStream(any(Stream.class))).thenReturn(streamWithNulls);

        // When
        ResponseEntity<Stream> response = streamController.addStream(streamWithNulls);

        // Then
        assertEquals(streamWithNulls, response.getBody());
        verify(streamService, times(1)).addStream(streamWithNulls);
    }

    @Test
    void testGetStreams_ShouldReturnEmptyListWhenNoStreams() {
        // Given
        when(streamService.getStreams()).thenReturn(List.of());

        // When
        List<Stream> result = streamController.getStreams();

        // Then
        assertTrue(result.isEmpty());
        verify(streamService, times(1)).getStreams();
    }

    @Test
    void testGetStreamGames_ShouldReturnGamesList() {
        // Given
        UUID streamId = UUID.randomUUID();
        List<Game> expectedGames = FIXTURE_MONKEY.giveMe(Game.class, 3);
        when(streamService.getStreamGames(streamId)).thenReturn(expectedGames);

        // When
        ResponseEntity<List<Game>> response = streamController.getStreamGames(streamId);

        // Then
        assertEquals(expectedGames, response.getBody());
        verify(streamService, times(1)).getStreamGames(streamId);
    }

    @Test
    void testAddGamesToStream_ShouldReturnUpdatedStream() {
        // Given
        UUID streamId = UUID.randomUUID();
        List<Game> gamesToAdd = FIXTURE_MONKEY.giveMe(Game.class, 2);
        Stream updatedStream = FIXTURE_MONKEY.giveMeOne(Stream.class);
        when(streamService.addGamesToStream(streamId, gamesToAdd)).thenReturn(updatedStream);

        // When
        ResponseEntity<Stream> response = streamController.addGamesToStream(streamId, gamesToAdd);

        // Then
        assertEquals(updatedStream, response.getBody());
        verify(streamService, times(1)).addGamesToStream(streamId, gamesToAdd);
    }

    @Test
    void testRemoveGamesFromStream_ShouldReturnUpdatedStream() {
        // Given
        UUID streamId = UUID.randomUUID();
        List<UUID> gameIdsToRemove = List.of(UUID.randomUUID(), UUID.randomUUID());
        Stream updatedStream = FIXTURE_MONKEY.giveMeOne(Stream.class);
        when(streamService.removeGamesFromStream(streamId, gameIdsToRemove)).thenReturn(updatedStream);

        // When
        ResponseEntity<Stream> response = streamController.removeGamesFromStream(streamId, gameIdsToRemove);

        // Then
        assertEquals(updatedStream, response.getBody());
        verify(streamService, times(1)).removeGamesFromStream(streamId, gameIdsToRemove);
    }
}