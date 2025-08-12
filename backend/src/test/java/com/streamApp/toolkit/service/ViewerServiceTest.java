package com.streamApp.toolkit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.streamApp.toolkit.entities.Viewer;
import com.streamApp.toolkit.repositories.ViewerRepository;
import com.streamApp.toolkit.service.ViewerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;


@ExtendWith(MockitoExtension.class)
class ViewerServiceTest {

  @Mock
  private ViewerRepository viewerRepository;

  @InjectMocks
  private ViewerService viewerService;

  private static FixtureMonkey FIXTURE_MONKEY = FixtureMonkey.create();

  private static final int SAMPLE_LIST_SIZE = 3;

  @Test
  void testGetAllViewers() {
    // Given
    List<Viewer> expectedViewers = FIXTURE_MONKEY.giveMe(Viewer.class, SAMPLE_LIST_SIZE);
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
    List<Viewer> viewers = FIXTURE_MONKEY.giveMe(Viewer.class, SAMPLE_LIST_SIZE);

    for (int identifier = 0; identifier < viewers.size(); identifier++) {
      when(viewerRepository.save(viewers.get(identifier))).thenReturn(viewers.get(identifier));
    }

    // When & Then
    for (int identifier = 0; identifier < viewers.size(); identifier++) {
      Viewer result = viewerService.addViewer(viewers.get(identifier));
      assertEquals(viewers.get(identifier), result);
    }

    verify(viewerRepository, times(SAMPLE_LIST_SIZE)).save(any(Viewer.class));
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

  @Test
  void testAddViewerWithNullViewer() {
    // Given
    Viewer nullViewer = null;
    when(viewerRepository.save(null))
        .thenThrow(new IllegalArgumentException("Viewer cannot be null"));

    // When & Then
    assertThrows(IllegalArgumentException.class, () -> {
      viewerService.addViewer(nullViewer);
    });
  }

  @Test
  void testGetViewerWithNullId() {
    // Given
    when(viewerRepository.getViewer(null)).thenReturn(null);

    // When
    Viewer result = viewerService.getViewer(null);

    // Then
    assertNull(result);
    verify(viewerRepository, times(1)).getViewer(null);
  }

  @Test
  void testUpdateViewer_shouldReturnUpdatedViewer() {
    // Given
    Viewer existingViewer = FIXTURE_MONKEY.giveMeOne(Viewer.class);
    UUID viewerId = existingViewer.getId();
    Viewer updateData = FIXTURE_MONKEY.giveMeBuilder(Viewer.class)
        .set("name", "Updated Viewer")
        .sample();
    
    when(viewerRepository.getViewer(viewerId)).thenReturn(existingViewer);
    when(viewerRepository.save(any(Viewer.class))).thenReturn(existingViewer);

    // When
    Viewer result = viewerService.updateViewer(viewerId, updateData);

    // Then
    assertEquals(existingViewer, result);
    verify(viewerRepository).getViewer(viewerId);
    verify(viewerRepository).save(existingViewer);
  }

  @Test
  void testUpdateViewer_shouldReturnNullWhenViewerNotFound() {
    // Given
    UUID viewerId = UUID.randomUUID();
    Viewer updateData = FIXTURE_MONKEY.giveMeOne(Viewer.class);
    when(viewerRepository.getViewer(viewerId)).thenReturn(null);

    // When
    Viewer result = viewerService.updateViewer(viewerId, updateData);

    // Then
    assertNull(result);
    verify(viewerRepository).getViewer(viewerId);
  }
}