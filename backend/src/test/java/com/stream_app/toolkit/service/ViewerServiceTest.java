package com.stream_app.toolkit.service;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.stream_app.toolkit.entities.Viewer;
import com.stream_app.toolkit.repositories.ViewerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ViewerServiceTest {

    @Mock
    private ViewerRepository viewerRepository;

    @InjectMocks
    private ViewerService viewerService;


    private static FixtureMonkey FIXTURE_MONKEY = FixtureMonkey.create();

    @Test
    void testGetAllViewers() {
        // Given
        List<Viewer> expectedViewers = FIXTURE_MONKEY.giveMe(Viewer.class, 5);
        when(viewerRepository.getAllViewers()).thenReturn(expectedViewers);

        // When
        List<Viewer> result = viewerService.getAllViewers();

        // Then
        assertEquals(expectedViewers, result);
        verify(viewerRepository, times(1)).getAllViewers();
    }

    @Test
    void testAddViewer() {
        // Given
        Viewer viewer = FIXTURE_MONKEY.giveMeOne(Viewer.class);
        when(viewerRepository.save(viewer)).thenReturn(viewer);

        // When
        Viewer result = viewerService.addViewer(viewer);

        // Then
        assertEquals(viewer, result);
        verify(viewerRepository, times(1)).save(viewer);
    }

    @Test
    void testGetViewer() {
        // Given
        Viewer viewer = FIXTURE_MONKEY.giveMeOne(Viewer.class);
        UUID viewerId = viewer.getId();
        when(viewerRepository.getViewer(viewerId)).thenReturn(viewer);

        // When
        Viewer result = viewerService.getViewer(viewerId);

        // Then
        assertEquals(viewer, result);
        verify(viewerRepository, times(1)).getViewer(viewerId);
    }

    @Test
    void testGetViewerNotFound() {
        // Given
        UUID nonExistentId = UUID.randomUUID();
        when(viewerRepository.getViewer(nonExistentId)).thenReturn(null);

        // When
        Viewer result = viewerService.getViewer(nonExistentId);

        // Then
        assertNull(result);
        verify(viewerRepository, times(1)).getViewer(nonExistentId);
    }

    @Test
    void testAddViewerWithNullValues() {
        // Given
        Viewer viewerWithNulls = FIXTURE_MONKEY.giveMeBuilder(Viewer.class)
                .setNull("twitchHandle")
                .sample();

        when(viewerRepository.save(any(Viewer.class))).thenReturn(viewerWithNulls);

        // When
        Viewer result = viewerService.addViewer(viewerWithNulls);

        // Then
        assertEquals(viewerWithNulls, result);
        verify(viewerRepository, times(1)).save(viewerWithNulls);
    }

    @Test
    void testGetAllViewersEmptyList() {
        // Given
        when(viewerRepository.getAllViewers()).thenReturn(List.of());

        // When
        List<Viewer> result = viewerService.getAllViewers();

        // Then
        assertTrue(result.isEmpty());
        verify(viewerRepository, times(1)).getAllViewers();
    }

    @Test
    void testAddViewerWithAllFields() {
        // Given
        Viewer completeViewer = FIXTURE_MONKEY.giveMeOne(Viewer.class);
        // Ensure the viewer has non-null values for testing
        completeViewer.setTwitchHandle("testuser123");
        completeViewer.setName("Test User");

        when(viewerRepository.save(any(Viewer.class))).thenReturn(completeViewer);

        // When
        Viewer result = viewerService.addViewer(completeViewer);

        // Then
        assertEquals(completeViewer, result);
        assertEquals("testuser123", result.getTwitchHandle());
        assertEquals("Test User", result.getName());
        verify(viewerRepository, times(1)).save(completeViewer);
    }

    @Test
    void testAddMultipleViewers() {
        // Given
        List<Viewer> viewersToSave = FIXTURE_MONKEY.giveMe(Viewer.class, 10);
        List<Viewer> savedViewers = FIXTURE_MONKEY.giveMe(Viewer.class, 10);

        for (int i = 0; i < viewersToSave.size(); i++) {
            when(viewerRepository.save(viewersToSave.get(i))).thenReturn(savedViewers.get(i));
        }

        // When & Then
        for (int i = 0; i < viewersToSave.size(); i++) {
            Viewer result = viewerService.addViewer(viewersToSave.get(i));
            assertEquals(savedViewers.get(i), result);
        }

        verify(viewerRepository, times(10)).save(any(Viewer.class));
    }

    @Test
    void testGetViewerWithSpecificData() {
        // Given
        UUID testId = UUID.randomUUID();
        Viewer expectedViewer = FIXTURE_MONKEY.giveMeOne(Viewer.class);
        expectedViewer.setTwitchHandle("specificuser123");
        expectedViewer.setName("Specific User");

        when(viewerRepository.getViewer(testId)).thenReturn(expectedViewer);

        // When
        Viewer result = viewerService.getViewer(testId);

        // Then
        assertEquals(expectedViewer, result);
        assertEquals("specificuser123", result.getTwitchHandle());
        assertEquals("Specific User", result.getName());
        verify(viewerRepository, times(1)).getViewer(testId);
    }
}