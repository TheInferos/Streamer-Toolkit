package com.stream_app.toolkit.unit.controllers;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.stream_app.toolkit.controllers.ViewerController;
import com.stream_app.toolkit.entities.Viewer;
import com.stream_app.toolkit.service.ViewerService;
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
class ViewerControllerTest {

    @Mock
    private ViewerService viewerService;

    @InjectMocks
    private ViewerController viewerController;
    private static FixtureMonkey FIXTURE_MONKEY = FixtureMonkey.create();

    @Test
    void testGetViewers_ShouldReturnListFromService() {
        // Given
        List<Viewer> expectedViewers = FIXTURE_MONKEY.giveMe(Viewer.class, 3);
        when(viewerService.getAllViewers()).thenReturn(expectedViewers);

        // When
        List<Viewer> result = viewerController.getViewers();

        // Then
        assertEquals(expectedViewers, result);
        verify(viewerService, times(1)).getAllViewers();
    }

    @Test
    void testAddViewer_ShouldReturnSavedViewer() {
        // Given
        Viewer viewerToSave = FIXTURE_MONKEY.giveMeOne(Viewer.class);
        Viewer savedViewer = FIXTURE_MONKEY.giveMeOne(Viewer.class);
        when(viewerService.addViewer(any(Viewer.class))).thenReturn(savedViewer);

        // When
        Viewer result = viewerController.addViewer(viewerToSave);

        // Then
        assertEquals(savedViewer, result);
        verify(viewerService, times(1)).addViewer(viewerToSave);
    }

    @Test
    void testGetViewer_ShouldReturnViewerWhenFound() {
        // Given
        Viewer expectedViewer = FIXTURE_MONKEY.giveMeOne(Viewer.class);
        UUID viewerId = expectedViewer.getId();
        when(viewerService.getViewer(viewerId)).thenReturn(expectedViewer);

        // When
        Viewer result = viewerController.getViewer(viewerId);

        // Then
        assertEquals(expectedViewer, result);
        verify(viewerService, times(1)).getViewer(viewerId);
    }

    @Test
    void testGetViewer_ShouldReturnNullWhenNotFound() {
        // Given
        UUID viewerId = UUID.randomUUID();
        when(viewerService.getViewer(viewerId)).thenReturn(null);

        // When
        Viewer result = viewerController.getViewer(viewerId);

        // Then
        assertNull(result);
        verify(viewerService, times(1)).getViewer(viewerId);
    }

    @Test
    void testAddViewer_ShouldHandleNullValues() {
        // Given
        Viewer viewerWithNulls = FIXTURE_MONKEY.giveMeBuilder(Viewer.class)
                .setNull("twitchHandle")
                .sample();
        when(viewerService.addViewer(any(Viewer.class))).thenReturn(viewerWithNulls);

        // When
        Viewer result = viewerController.addViewer(viewerWithNulls);

        // Then
        assertEquals(viewerWithNulls, result);
        verify(viewerService, times(1)).addViewer(viewerWithNulls);
    }

    @Test
    void testGetViewers_ShouldReturnEmptyListWhenNoViewers() {
        // Given
        when(viewerService.getAllViewers()).thenReturn(List.of());

        // When
        List<Viewer> result = viewerController.getViewers();

        // Then
        assertTrue(result.isEmpty());
        verify(viewerService, times(1)).getAllViewers();
    }
} 