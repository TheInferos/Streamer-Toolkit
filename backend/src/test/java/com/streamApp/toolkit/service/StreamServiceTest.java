package com.streamApp.toolkit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.streamApp.toolkit.entities.Game;
import com.streamApp.toolkit.entities.Stream;
import com.streamApp.toolkit.repositories.StreamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class StreamServiceTest {

  @Mock
  private StreamRepository streamRepository;

  @InjectMocks
  private StreamService streamService;

  private static final int SAMPLE_LIST_SIZE = 3;

  private static FixtureMonkey FIXTURE_MONKEY = FixtureMonkey.create();
  
  @Test
  void testGetStreams() {
    // Given
    List<Stream> expectedStreams = FIXTURE_MONKEY.giveMe(Stream.class, SAMPLE_LIST_SIZE);
    when(streamRepository.findAll()).thenReturn(expectedStreams);

    // When
    List<Stream> result = streamService.getStreams();

    // Then
    assertEquals(expectedStreams, result);
    verify(streamRepository, times(1)).findAll();
  }

  @Test
  void testAddStream() {
    // Given
    Stream streamToSave = FIXTURE_MONKEY.giveMeOne(Stream.class);
    Stream savedStream = FIXTURE_MONKEY.giveMeOne(Stream.class);
    when(streamRepository.save(any(Stream.class))).thenReturn(savedStream);

    // When
    Stream result = streamService.addStream(streamToSave);

    // Then
    assertEquals(savedStream, result);
    verify(streamRepository, times(1)).save(streamToSave);
  }

  @Test
  void testGetStream() {
    // Given
    UUID testId = UUID.randomUUID();
    Stream expectedStream = FIXTURE_MONKEY.giveMeOne(Stream.class);
    when(streamRepository.findById(testId)).thenReturn(Optional.of(expectedStream));

    // When
    Stream result = streamService.getStream(testId);

    // Then
    assertEquals(expectedStream, result);
    verify(streamRepository, times(1)).findById(testId);
  }

  @Test
  void testGetStreamNotFound() {
    // Given
    UUID nonExistentId = UUID.randomUUID();
    when(streamRepository.findById(nonExistentId)).thenReturn(Optional.empty());

    // When
    Stream result = streamService.getStream(nonExistentId);

    // Then
    assertNull(result);
    verify(streamRepository, times(1)).findById(nonExistentId);
  }

  @Test
  void testAddStreamWithNullValues() {
    // Given
    Stream streamWithNulls = FIXTURE_MONKEY.giveMeBuilder(Stream.class)
        .setNull("description")
        .setNull("games")
        .setNull("tags")
        .setNull("categories")
        .sample();

    when(streamRepository.save(any(Stream.class))).thenReturn(streamWithNulls);

    // When
    Stream result = streamService.addStream(streamWithNulls);

    // Then
    assertEquals(streamWithNulls, result);
    verify(streamRepository, times(1)).save(streamWithNulls);
  }

  @Test
  void testGetStreamsEmptyList() {
    // Given
    when(streamRepository.findAll()).thenReturn(List.of());

    // When
    List<Stream> result = streamService.getStreams();

    // Then
    assertTrue(result.isEmpty());
    verify(streamRepository, times(1)).findAll();
  }

  @Test
  void testAddStreamWithComplexData() {
    // Given
    Stream complexStream = FIXTURE_MONKEY.giveMeOne(Stream.class);

    when(streamRepository.save(any(Stream.class))).thenReturn(complexStream);

    // When
    Stream result = streamService.addStream(complexStream);

    // Then
    assertEquals(complexStream, result);
    verify(streamRepository, times(1)).save(complexStream);
  }

  @Test
  void testAddStreamWithNullStream() {
    // Given
    Stream nullStream = null;
    when(streamRepository.save(null))
        .thenThrow(new IllegalArgumentException("Stream cannot be null"));

    // When & Then
    assertThrows(IllegalArgumentException.class, () -> {
      streamService.addStream(nullStream);
    });
  }

  @Test
  void testGetStreamWithNullId() {
    // Given
    when(streamRepository.findById(null)).thenReturn(java.util.Optional.empty());

    // When
    Stream result = streamService.getStream(null);

    // Then
    assertNull(result);
    verify(streamRepository, times(1)).findById(null);
  }

  @Test
  void testGetStreamGames_shouldReturnGamesList() {
    // Given
    Stream stream = FIXTURE_MONKEY.giveMeOne(Stream.class);
    UUID streamId = stream.getId();
    List<Game> expectedGames = FIXTURE_MONKEY.giveMe(Game.class, SAMPLE_LIST_SIZE);
    stream.setGames(expectedGames);
    when(streamRepository.findById(streamId)).thenReturn(Optional.of(stream));

    // When
    List<Game> result = streamService.getStreamGames(streamId);

    // Then
    assertEquals(expectedGames, result);
    verify(streamRepository, times(1)).findById(streamId);
  }

  @Test
  void testGetStreamGames_shouldReturnEmptyListWhenStreamNotFound() {
    // Given
    UUID streamId = UUID.randomUUID();
    when(streamRepository.findById(streamId)).thenReturn(Optional.empty());

    // When
    List<Game> result = streamService.getStreamGames(streamId);

    // Then
    assertTrue(result.isEmpty());
    verify(streamRepository, times(1)).findById(streamId);
  }

  @Test
  void testGetStreamGames_shouldReturnEmptyListWhenGamesIsNull() {
    // Given
    UUID streamId = UUID.randomUUID();
    Stream stream = FIXTURE_MONKEY.giveMeOne(Stream.class);
    stream.setGames(null);
    when(streamRepository.findById(streamId)).thenReturn(Optional.of(stream));

    // When
    List<Game> result = streamService.getStreamGames(streamId);

    // Then
    assertTrue(result.isEmpty());
    verify(streamRepository, times(1)).findById(streamId);
  }

  @Test
  void testAddGamesToStream_shouldReturnUpdatedStreamWithGames() {
    // Given
    Stream existingStream = FIXTURE_MONKEY.giveMeOne(Stream.class);
    UUID streamId = existingStream.getId();
    List<Game> existingGames = FIXTURE_MONKEY.giveMe(Game.class, 2);
    existingStream.setGames(existingGames);
    
    List<Game> newGames = FIXTURE_MONKEY.giveMe(Game.class, 2);
    Stream updatedStream = FIXTURE_MONKEY.giveMeOne(Stream.class);
    
    when(streamRepository.findById(streamId)).thenReturn(Optional.of(existingStream));
    when(streamRepository.save(any(Stream.class))).thenReturn(updatedStream);

    // When
    Stream result = streamService.addGamesToStream(streamId, newGames);

    // Then
    assertEquals(updatedStream, result);
    verify(streamRepository, times(1)).findById(streamId);
    verify(streamRepository, times(1)).save(existingStream);
  }

  @Test
  void testAddGamesToStream_shouldReturnStreamWithInitializedGames() {
    // Given
    Stream existingStream = FIXTURE_MONKEY.giveMeOne(Stream.class);
    UUID streamId = existingStream.getId();
    existingStream.setGames(null);
    
    List<Game> newGames = FIXTURE_MONKEY.giveMe(Game.class, 2);
    Stream updatedStream = FIXTURE_MONKEY.giveMeOne(Stream.class);
    
    when(streamRepository.findById(streamId)).thenReturn(Optional.of(existingStream));
    when(streamRepository.save(any(Stream.class))).thenReturn(updatedStream);

    // When
    Stream result = streamService.addGamesToStream(streamId, newGames);

    // Then
    assertEquals(updatedStream, result);
    verify(streamRepository, times(1)).findById(streamId);
    verify(streamRepository, times(1)).save(existingStream);
  }

  @Test
  void testAddGamesToStream_shouldReturnNullWhenStreamNotFound() {
    // Given
    UUID streamId = UUID.randomUUID();
    List<Game> newGames = FIXTURE_MONKEY.giveMe(Game.class, 2);
    when(streamRepository.findById(streamId)).thenReturn(Optional.empty());

    // When
    Stream result = streamService.addGamesToStream(streamId, newGames);

    // Then
    assertNull(result);
    verify(streamRepository, times(1)).findById(streamId);
    verify(streamRepository, never()).save(any());
  }

  @Test
  void testRemoveGamesFromStream_shouldReturnUpdatedStreamWithoutGames() {
    // Given
    Stream existingStream = FIXTURE_MONKEY.giveMeOne(Stream.class);
    UUID streamId = UUID.randomUUID(); // Use separate UUID to avoid null issues
    
    // Create games with proper IDs
    Game game1 = new Game(UUID.randomUUID(), "Game 1", new ArrayList<>(List.of("Action")));
    Game game2 = new Game(UUID.randomUUID(), "Game 2", new ArrayList<>(List.of("Adventure")));
    Game game3 = new Game(UUID.randomUUID(), "Game 3", new ArrayList<>(List.of("RPG")));
    List<Game> existingGames = new ArrayList<>(List.of(game1, game2, game3));
    existingStream.setGames(existingGames);
    
    List<UUID> gameIdsToRemove = new ArrayList<>(List.of(game1.getId(), game2.getId()));
    Stream updatedStream = FIXTURE_MONKEY.giveMeOne(Stream.class);
    
    when(streamRepository.findById(streamId)).thenReturn(Optional.of(existingStream));
    when(streamRepository.save(any(Stream.class))).thenReturn(updatedStream);

    // When
    Stream result = streamService.removeGamesFromStream(streamId, gameIdsToRemove);

    // Then
    assertEquals(updatedStream, result);
    verify(streamRepository, times(1)).findById(streamId);
    verify(streamRepository, times(1)).save(existingStream);
  }

  @Test
  void testRemoveGamesFromStream_shouldReturnStreamWhenGamesIsNull() {
    // Given
    Stream existingStream = FIXTURE_MONKEY.giveMeOne(Stream.class);
    UUID streamId = existingStream.getId();
    existingStream.setGames(null);
    
    List<UUID> gameIdsToRemove = List.of(UUID.randomUUID());
    
    when(streamRepository.findById(streamId)).thenReturn(Optional.of(existingStream));

    // When
    Stream result = streamService.removeGamesFromStream(streamId, gameIdsToRemove);

    // Then
    assertEquals(existingStream, result);
    verify(streamRepository, times(1)).findById(streamId);
    verify(streamRepository, never()).save(any());
  }

  @Test
  void testRemoveGamesFromStream_shouldReturnStreamWhenStreamNotFound() {
    // Given
    UUID streamId = UUID.randomUUID();
    List<UUID> gameIdsToRemove = List.of(UUID.randomUUID());
    when(streamRepository.findById(streamId)).thenReturn(Optional.empty());

    // When
    Stream result = streamService.removeGamesFromStream(streamId, gameIdsToRemove);

    // Then
    assertNull(result);
    verify(streamRepository, times(1)).findById(streamId);
    verify(streamRepository, never()).save(any());
  }
} 