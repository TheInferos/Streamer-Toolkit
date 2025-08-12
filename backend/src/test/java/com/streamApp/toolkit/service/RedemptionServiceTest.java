package com.streamApp.toolkit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.streamApp.toolkit.entities.Redemption;
import com.streamApp.toolkit.entities.enums.RedeemTypes;
import com.streamApp.toolkit.repositories.RedemptionRepository;
import com.streamApp.toolkit.testutils.TestFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class RedemptionServiceTest {

  @Mock
  private RedemptionRepository redemptionRepository;

  @InjectMocks
  private RedemptionService redemptionService;

  private static final FixtureMonkey FIXTURE_MONKEY = TestFixtures.FIXTURE_MONKEY;

  private Redemption testRedemption;

  private UUID testId;

  @BeforeEach
  void setUp() {
    testId = UUID.randomUUID();
    UUID viewerId = UUID.randomUUID();
    testRedemption = FIXTURE_MONKEY.giveMeBuilder(Redemption.class)
        .set("id", testId)
        .set("name", "Test Redemption")
        .set("description", "Test Description")
        .set("type", RedeemTypes.GIFTED_GAME)
        .set("completed", false)
        .set("viewerId", viewerId)
        .sample();
  }

  @Test
  void testGetAllRedemptions_shouldReturnAllRedemptions() {
    // Given
    List<Redemption> expectedRedemptions = List.of(testRedemption);
    when(redemptionRepository.findAll()).thenReturn(expectedRedemptions);

    // When
    List<Redemption> result = redemptionService.getAllRedemptions();

    // Then
    assertEquals(expectedRedemptions, result);
    verify(redemptionRepository).findAll();
  }

  @Test
  void testGetRedemptionById_shouldReturnRedemption() {
    // Given
    when(redemptionRepository.findById(testId)).thenReturn(Optional.of(testRedemption));

    // When
    Optional<Redemption> result = redemptionService.getRedemptionById(testId);

    // Then
    assertTrue(result.isPresent());
    assertEquals(testRedemption, result.get());
    verify(redemptionRepository).findById(testId);
  }

  @Test
  void testGetRedemptionById_shouldReturnEmptyWhenNotFound() {
    // Given
    when(redemptionRepository.findById(testId)).thenReturn(Optional.empty());

    // When
    Optional<Redemption> result = redemptionService.getRedemptionById(testId);

    // Then
    assertFalse(result.isPresent());
    verify(redemptionRepository).findById(testId);
  }

  @Test
  void testCreateRedemption_shouldReturnAndSaveRedemption() {
    // Given
    when(redemptionRepository.save(any(Redemption.class))).thenReturn(testRedemption);

    // When
    Redemption result = redemptionService.createRedemption(testRedemption);

    // Then
    assertEquals(testRedemption, result);
    verify(redemptionRepository).save(testRedemption);
  }

  @Test
  void testUpdateRedemption_shouldReturnAndUpdateRedemption() {
    // Given
    Redemption updateDetails = FIXTURE_MONKEY.giveMeBuilder(Redemption.class)
        .set("name", "Updated Name")
        .set("description", "Updated Description")
        .set("type", RedeemTypes.PICK_GAME)
        .set("completed", true)
        .sample();

    when(redemptionRepository.findById(testId)).thenReturn(Optional.of(testRedemption));
    when(redemptionRepository.save(any(Redemption.class))).thenReturn(testRedemption);

    // When
    Optional<Redemption> result = redemptionService.updateRedemption(testId, updateDetails);

    // Then
    assertTrue(result.isPresent());
    assertEquals(testRedemption, result.get());
    verify(redemptionRepository).findById(testId);
    verify(redemptionRepository).save(testRedemption);
  }

  @Test
  void testUpdateRedemption_shouldReturnEmptyWhenNotFound() {
    // Given
    Redemption updateDetails = FIXTURE_MONKEY.giveMeOne(Redemption.class);
    when(redemptionRepository.findById(testId)).thenReturn(Optional.empty());

    // When
    Optional<Redemption> result = redemptionService.updateRedemption(testId, updateDetails);

    // Then
    assertFalse(result.isPresent());
    verify(redemptionRepository).findById(testId);
    verify(redemptionRepository, never()).save(any());
  }

  @Test
  void testDeleteRedemption_shouldReturnTrueWhenDeleted() {
    // Given
    when(redemptionRepository.existsById(testId)).thenReturn(true);
    doNothing().when(redemptionRepository).deleteById(testId);

    // When
    boolean result = redemptionService.deleteRedemption(testId);

    // Then
    assertTrue(result);
    verify(redemptionRepository).existsById(testId);
    verify(redemptionRepository).deleteById(testId);
  }

  @Test
  void testDeleteRedemption_shouldReturnFalseWhenNotFound() {
    // Given
    when(redemptionRepository.existsById(testId)).thenReturn(false);

    // When
    boolean result = redemptionService.deleteRedemption(testId);

    // Then
    assertFalse(result);
    verify(redemptionRepository).existsById(testId);
    verify(redemptionRepository, never()).deleteById(any());
  }

  @Test
  void testGetRedemptionsByType_shouldReturnFilteredRedemptions() {
    // Given
    List<Redemption> expectedRedemptions = List.of(testRedemption);
    when(redemptionRepository.findByType(RedeemTypes.GIFTED_GAME)).thenReturn(expectedRedemptions);

    // When
    List<Redemption> result = redemptionService.getRedemptionsByType(RedeemTypes.GIFTED_GAME);

    // Then
    assertEquals(expectedRedemptions, result);
    verify(redemptionRepository).findByType(RedeemTypes.GIFTED_GAME);
  }

  @Test
  void testGetRedemptionsByCompletedStatus_shouldReturnFilteredRedemptions() {
    // Given
    List<Redemption> expectedRedemptions = List.of(testRedemption);
    when(redemptionRepository.findByCompleted(false)).thenReturn(expectedRedemptions);

    // When
    List<Redemption> result = redemptionService.getRedemptionsByCompletedStatus(false);

    // Then
    assertEquals(expectedRedemptions, result);
    verify(redemptionRepository).findByCompleted(false);
  }

  @Test
  void testGetRedemptionsByTypeAndCompleted_shouldReturnFilteredRedemptions() {
    // Given
    List<Redemption> expectedRedemptions = List.of(testRedemption);
    when(
      redemptionRepository.findByTypeAndCompleted(RedeemTypes.GIFTED_GAME,
                                      false))
        .thenReturn(expectedRedemptions);

    // When
    List<Redemption> result = 
        redemptionService.getRedemptionsByTypeAndCompleted(RedeemTypes.GIFTED_GAME, 
                                            false);

    // Then
    assertEquals(expectedRedemptions, result);
    verify(redemptionRepository).findByTypeAndCompleted(RedeemTypes.GIFTED_GAME, false);
  }

  @Test
  void testSearchRedemptionsByName_shouldReturnMatchingRedemptions() {
    // Given
    List<Redemption> expectedRedemptions = List.of(testRedemption);
    when(redemptionRepository.findByNameContainingIgnoreCase("Test"))
        .thenReturn(expectedRedemptions);

    // When
    List<Redemption> result = redemptionService.searchRedemptionsByName("Test");

    // Then
    assertEquals(expectedRedemptions, result);
    verify(redemptionRepository).findByNameContainingIgnoreCase("Test");
  }

  @Test
  void testMarkRedemptionAsCompleted_shouldReturnCompletedRedemption() {
    // Given
    when(redemptionRepository.findById(testId)).thenReturn(Optional.of(testRedemption));
    when(redemptionRepository.save(any(Redemption.class))).thenReturn(testRedemption);

    // When
    Redemption result = redemptionService.markRedemptionAsCompleted(testId);

    // Then
    assertTrue(result.getCompleted());
    verify(redemptionRepository).findById(testId);
    verify(redemptionRepository).save(testRedemption);
  }

  @Test
  void testMarkRedemptionAsCompleted_shouldReturnExceptionWhenNotFound() {
    // Given
    when(redemptionRepository.findById(testId)).thenReturn(Optional.empty());

    // When & Then
    assertThrows(RuntimeException.class, () -> redemptionService.markRedemptionAsCompleted(testId));
    verify(redemptionRepository).findById(testId);
    verify(redemptionRepository, never()).save(any());
  }

  @Test
  void testGetRedemptionsByViewer_shouldReturnViewerRedemptions() {
    // Given
    UUID viewerId = UUID.randomUUID();
    List<Redemption> expectedRedemptions = List.of(testRedemption);
    when(redemptionRepository.findByViewerId(viewerId)).thenReturn(expectedRedemptions);

    // When
    List<Redemption> result = redemptionService.getRedemptionsByViewer(viewerId);

    // Then
    assertEquals(expectedRedemptions, result);
    verify(redemptionRepository).findByViewerId(viewerId);
  }

  @Test
  void testGetRedemptionsByViewerAndCompleted_shouldReturnFilteredRedemptions() {
    // Given
    UUID viewerId = UUID.randomUUID();
    List<Redemption> expectedRedemptions = List.of(testRedemption);
    when(redemptionRepository.findByViewerIdAndCompleted(viewerId, false))
        .thenReturn(expectedRedemptions);

    // When
    List<Redemption> result = redemptionService.getRedemptionsByViewerAndCompleted(viewerId, false);

    // Then
    assertEquals(expectedRedemptions, result);
    verify(redemptionRepository).findByViewerIdAndCompleted(viewerId, false);
  }

  @Test
  void testGetIncompleteRedemptionsByViewer_shouldReturnIncompleteRedemptions() {
    // Given
    UUID viewerId = UUID.randomUUID();
    List<Redemption> expectedRedemptions = List.of(testRedemption);
    when(redemptionRepository.findByViewerIdAndCompleted(viewerId, false))
        .thenReturn(expectedRedemptions);

    // When
    List<Redemption> result = redemptionService.getIncompleteRedemptionsByViewer(viewerId);

    // Then
    assertEquals(expectedRedemptions, result);
    verify(redemptionRepository).findByViewerIdAndCompleted(viewerId, false);
  }

  @Test
  void testGetCompletedRedemptionsByViewer_shouldReturnCompletedRedemptions() {
    // Given
    UUID viewerId = UUID.randomUUID();
    List<Redemption> expectedRedemptions = List.of(testRedemption);
    when(redemptionRepository.findByViewerIdAndCompleted(viewerId, true))
        .thenReturn(expectedRedemptions);

    // When
    List<Redemption> result = redemptionService.getCompletedRedemptionsByViewer(viewerId);

    // Then
    assertEquals(expectedRedemptions, result);
    verify(redemptionRepository).findByViewerIdAndCompleted(viewerId, true);
  }
}
