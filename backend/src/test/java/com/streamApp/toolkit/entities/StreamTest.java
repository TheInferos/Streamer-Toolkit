package com.streamApp.toolkit.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.streamApp.toolkit.entities.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

class StreamTest {

  private static FixtureMonkey FIXTURE_MONKEY = FixtureMonkey.create();

  private Stream stream;

  private static final int SAMPLE_LIST_SIZE = 3;

  @BeforeEach
  void setUp() {
    stream = new Stream();
  }

  @Test
  void testStreamCreation() {
    // Given
    Stream testStream = FIXTURE_MONKEY.giveMeOne(Stream.class);

    // When
    stream.setId(testStream.getId());
    stream.setName(testStream.getName());
    stream.setDescription(testStream.getDescription());
    stream.setUrl(testStream.getUrl());
    stream.setGames(testStream.getGames());
    stream.setTags(testStream.getTags());
    stream.setCategories(testStream.getCategories());

    // Then
    assertEquals(testStream.getId(), stream.getId());
    assertEquals(testStream.getName(), stream.getName());
    assertEquals(testStream.getDescription(), stream.getDescription());
    assertEquals(testStream.getUrl(), stream.getUrl());
    assertEquals(testStream.getGames(), stream.getGames());
    assertEquals(testStream.getTags(), stream.getTags());
    assertEquals(testStream.getCategories(), stream.getCategories());
  }

  @Test
  void testIdGeneration() {
    // Given
    stream.setName("Test Stream");

    // When
    stream.assignId();

    // Then
    assertNotNull(stream.getId());
    assertTrue(stream.getId() instanceof UUID);
  }

  @Test
  void testIdGenerationOnlyWhenNull() {
    // Given
    UUID existingId = UUID.randomUUID();
    stream.setId(existingId);
    stream.setName("Test Stream");

    // When
    stream.assignId();

    // Then
    assertEquals(existingId, stream.getId());
  }

  @Test
  void testStreamWithAllArgsConstructor() {
    // Given
    Stream streamWithAllArgs = FIXTURE_MONKEY.giveMeBuilder(Stream.class)
        .setNotNull("id")
        .setNotNull("name")
        .setNotNull("url")
        .sample();

    // When & Then
    assertNotNull(streamWithAllArgs.getId());
    assertNotNull(streamWithAllArgs.getName());
    assertNotNull(streamWithAllArgs.getUrl());
  }

  @Test
  void testStreamEquality() {
    // Given
    Stream stream1 = FIXTURE_MONKEY.giveMeBuilder(Stream.class)
        .setNotNull("id")
        .setNotNull("name")
        .setNotNull("url")
        .setNotNull("description")
        .setNotNull("games")
        .setNotNull("tags")
        .setNotNull("categories")
        .setNotNull("viewers")
        .sample();

    Stream stream2 = new Stream();
    stream2.setId(stream1.getId());
    stream2.setName(stream1.getName());
    stream2.setDescription(stream1.getDescription());
    stream2.setUrl(stream1.getUrl());
    stream2.setGames(stream1.getGames());
    stream2.setTags(stream1.getTags());
    stream2.setCategories(stream1.getCategories());
    stream2.setViewers(stream1.getViewers());

    // When & Then
    assertEquals(stream1, stream2);
    assertEquals(stream1.hashCode(), stream2.hashCode());
  }

  @Test
  void testStreamToString() {
    // Given
    Stream testStream = FIXTURE_MONKEY.giveMeOne(Stream.class);
    if (testStream.getName() == null) {
      testStream.setName("Test Stream");
    }

    // When
    String result = testStream.toString();

    // Then
    assertNotNull(result);
    assertTrue(result.contains(testStream.getName()));
  }

  @Test
  void testMultipleStreams() {
    // Given
    List<Stream> streams = FIXTURE_MONKEY.giveMeBuilder(Stream.class)
        .setNotNull("id")
        .setNotNull("name")
        .setNotNull("url")
        .sampleList(SAMPLE_LIST_SIZE);

    // When & Then
    assertEquals(SAMPLE_LIST_SIZE, streams.size());
    streams.forEach(stream -> {
      assertNotNull(stream.getId());
      assertNotNull(stream.getName());
      assertNotNull(stream.getUrl());
    });
  }

  @Test
  void testStreamWithCustomData() {
    // Given
    Stream customStream = FIXTURE_MONKEY.giveMeBuilder(Stream.class)
        .setNotNull("id")
        .setNotNull("name")
        .setNotNull("url")
        .sample();

    // When & Then
    assertNotNull(customStream.getName());
    assertNotNull(customStream.getUrl());
    assertNotNull(customStream.getId());
  }
}