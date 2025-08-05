package com.stream_app.toolkit.service;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.stream_app.toolkit.entities.Stream;
import com.stream_app.toolkit.repositories.StreamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
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
class StreamServiceTest {

    @Mock
    private StreamRepository streamRepository;

    @InjectMocks
    private StreamService streamService;

    private static FixtureMonkey FIXTURE_MONKEY = FixtureMonkey.create();
    @Test
    void testGetStreams() {
        // Given
        List<Stream> expectedStreams = FIXTURE_MONKEY.giveMe(Stream.class, 3);
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
    void testAddMultipleStreams() {
        // Given
        List<Stream> streams = FIXTURE_MONKEY.giveMe(Stream.class, 5);

        for (int i = 0; i < streams.size(); i++) {
            when(streamRepository.save(streams.get(i))).thenReturn(streams.get(i));
        }

        // When & Then
        for (int i = 0; i < streams.size(); i++) {
            Stream result = streamService.addStream(streams.get(i));
            assertEquals(streams.get(i), result);
        }

        verify(streamRepository, times(5)).save(any(Stream.class));
    }
} 