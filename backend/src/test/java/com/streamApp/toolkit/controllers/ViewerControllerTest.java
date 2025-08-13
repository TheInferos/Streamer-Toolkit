package com.streamApp.toolkit.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.streamApp.toolkit.entities.Viewer;
import com.streamApp.toolkit.service.ViewerService;
import com.streamApp.toolkit.testutils.TestFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class ViewerControllerTest {

  @Mock
  private ViewerService viewerService;

  private static final int SAMPLE_LIST_SIZE = 3;

  @InjectMocks
  private ViewerController viewerController;
  
  private static final FixtureMonkey FIXTURE_MONKEY = TestFixtures.FIXTURE_MONKEY;

  private static final int NO_CONTENT_STATUS_CODE = 204;

  @Test
  void testGetViewers_shouldReturnListFromService() {
    // Given
    List<Viewer> expectedViewers = FIXTURE_MONKEY.giveMe(Viewer.class, SAMPLE_LIST_SIZE);
    when(viewerService.getAllViewers()).thenReturn(expectedViewers);

    // When
    List<Viewer> result = viewerController.getViewers();

    // Then
    assertEquals(expectedViewers, result);
    verify(viewerService, times(1)).getAllViewers();
  }

  @Test
  void testAddViewer_shouldReturnSavedViewer() {
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
  void testGetViewer_shouldReturnViewerWhenFound() {
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
  void testGetViewer_shouldReturnNullWhenNotFound() {
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
  void testAddViewerWithNullValues_shouldReturnViewer() {
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
  void testGetViewers_shouldReturnEmptyListWhenNoViewers() {
    // Given
    when(viewerService.getAllViewers()).thenReturn(List.of());

    // When
    List<Viewer> result = viewerController.getViewers();

    // Then
    assertTrue(result.isEmpty());
    verify(viewerService, times(1)).getAllViewers();
  }

  @Test
  void testUpdateViewer_shouldReturnUpdatedViewer() {
    // Given
    UUID viewerId = UUID.randomUUID();
    Viewer viewerToUpdate = FIXTURE_MONKEY.giveMeOne(Viewer.class);
    Viewer updatedViewer = FIXTURE_MONKEY.giveMeOne(Viewer.class);
    when(viewerService.updateViewer(viewerId, viewerToUpdate)).thenReturn(updatedViewer);

    // When
    Viewer result = viewerController.updateViewer(viewerId, viewerToUpdate);

    // Then
    assertEquals(updatedViewer, result);
    verify(viewerService, times(1)).updateViewer(viewerId, viewerToUpdate);
  }

  @Test
  void testUpdateViewer_shouldReturnNullWhenViewerNotFound() {
    // Given
    UUID viewerId = UUID.randomUUID();
    Viewer viewerToUpdate = FIXTURE_MONKEY.giveMeOne(Viewer.class);
    when(viewerService.updateViewer(viewerId, viewerToUpdate)).thenReturn(null);

    // When
    Viewer result = viewerController.updateViewer(viewerId, viewerToUpdate);

    // Then
    assertNull(result);
    verify(viewerService, times(1)).updateViewer(viewerId, viewerToUpdate);
  }

  @Test
  void testDeleteViewer_shouldReturnNoContentWhenSuccessful() {
    // Given
    UUID viewerId = UUID.randomUUID();
    when(viewerService.deleteViewer(viewerId)).thenReturn(true);

    // When
    var result = viewerController.deleteViewer(viewerId);

    // Then
    assertEquals(NO_CONTENT_STATUS_CODE, result.getStatusCode().value());
    verify(viewerService, times(1)).deleteViewer(viewerId);
  }

  @Test
  void testDeleteViewer_shouldReturnNoContentWhenViewerNotFound() {
    // Given
    UUID viewerId = UUID.randomUUID();
    when(viewerService.deleteViewer(viewerId)).thenReturn(false);

    // When
    var result = viewerController.deleteViewer(viewerId);

    // Then
    assertEquals(NO_CONTENT_STATUS_CODE, result.getStatusCode().value());
    verify(viewerService, times(1)).deleteViewer(viewerId);
  }
} 