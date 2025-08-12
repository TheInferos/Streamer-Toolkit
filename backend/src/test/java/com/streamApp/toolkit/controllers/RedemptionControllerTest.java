package com.streamApp.toolkit.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.streamApp.toolkit.entities.Redemption;
import com.streamApp.toolkit.entities.enums.RedeemTypes;
import com.streamApp.toolkit.exceptions.RedemptionNotFoundException;
import com.streamApp.toolkit.service.RedemptionService;
import com.streamApp.toolkit.testutils.TestFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@ExtendWith(MockitoExtension.class)
class RedemptionControllerTest extends BaseControllerTest {

  @Mock
  private RedemptionService redemptionService;

  @InjectMocks
  private RedemptionController redemptionController;

  private Redemption testRedemption;
  
  private UUID testId;

  @BeforeEach
  void setUp() {
    testId = UUID.randomUUID();
    UUID viewerId = UUID.randomUUID();
    testRedemption = TestFixtures.FIXTURE_MONKEY.giveMeBuilder(Redemption.class)
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
    List<Redemption> redemptions = List.of(testRedemption);
    when(redemptionService.getAllRedemptions()).thenReturn(redemptions);

    // When
    ResponseEntity<List<Redemption>> response = redemptionController.getAllRedemptions();

    // Then
    assertEquals(redemptions, response.getBody());
    verify(redemptionService).getAllRedemptions();
  }

  @Test
  void testGetRedemptionById_shouldReturnRedemptionWhenFound() {
    // Given
    when(redemptionService.getRedemptionById(testId)).thenReturn(Optional.of(testRedemption));

    // When
    ResponseEntity<Redemption> response = redemptionController.getRedemptionById(testId);

    // Then
    assertEquals(testRedemption, response.getBody());
    verify(redemptionService).getRedemptionById(testId);
  }

  @Test
  void testGetRedemptionById_shouldReturnNullWhenNotFound() {
    // Given
    when(redemptionService.getRedemptionById(testId)).thenReturn(Optional.empty());

    // When
    ResponseEntity<Redemption> response = redemptionController.getRedemptionById(testId);

    // Then
    assertNull(response.getBody());
    verify(redemptionService).getRedemptionById(testId);
  }

  @Test
  void testCreateRedemption_shouldReturnCreatedRedemption() {
    // Given
    when(redemptionService.createRedemption(any(Redemption.class))).thenReturn(testRedemption);

    // When
    ResponseEntity<Redemption> response = redemptionController.createRedemption(testRedemption);

    // Then
    assertEquals(testRedemption, response.getBody());
    verify(redemptionService).createRedemption(testRedemption);
  }

  @Test
  void testUpdateRedemption_shouldReturnUpdatedRedemptionWhenExists() {
    // Given
    Redemption updateDetails = TestFixtures.FIXTURE_MONKEY.giveMeBuilder(Redemption.class)
        .set("name", "Updated Name")
        .set("description", "Updated Description")
        .set("type", RedeemTypes.PICK_GAME)
        .set("completed", true)
        .sample();

    when(redemptionService.updateRedemption(eq(testId), any(Redemption.class)))
        .thenReturn(Optional.of(testRedemption));

    // When
    ResponseEntity<Redemption> response = 
        redemptionController.updateRedemption(testId, updateDetails);

    // Then
    assertEquals(testRedemption, response.getBody());
    verify(redemptionService).updateRedemption(testId, updateDetails);
  }

  @Test
  void testUpdateRedemption_shouldReturnNullWhenNotExists() {
    // Given
    Redemption updateDetails = TestFixtures.FIXTURE_MONKEY.giveMeOne(Redemption.class);
    when(redemptionService.updateRedemption(eq(testId), any(Redemption.class)))
        .thenReturn(Optional.empty());

    // When
    ResponseEntity<Redemption> response = 
        redemptionController.updateRedemption(testId, updateDetails);

    // Then
    assertNull(response.getBody());
    verify(redemptionService).updateRedemption(testId, updateDetails);
  }

  @Test
  void testDeleteRedemption_shouldReturnNullWhenDeleted() {
    // Given
    when(redemptionService.deleteRedemption(testId)).thenReturn(true);

    // When
    ResponseEntity<Void> response = redemptionController.deleteRedemption(testId);

    // Then
    assertNull(response.getBody());
    verify(redemptionService).deleteRedemption(testId);
  }

  @Test
  void testDeleteRedemption_shouldReturnNullWhenNotExists() {
    // Given
    when(redemptionService.deleteRedemption(testId)).thenReturn(false);

    // When
    ResponseEntity<Void> response = redemptionController.deleteRedemption(testId);

    // Then
    assertNull(response.getBody());
    verify(redemptionService).deleteRedemption(testId);
  }

  @Test
  void testGetRedemptionsByType_shouldReturnFilteredRedemptions() {
    // Given
    List<Redemption> redemptions = List.of(testRedemption);
    when(redemptionService.getRedemptionsByType(RedeemTypes.GIFTED_GAME)).thenReturn(redemptions);

    // When
    ResponseEntity<List<Redemption>> response = 
        redemptionController.getRedemptionsByType(RedeemTypes.GIFTED_GAME);

    // Then
    assertEquals(redemptions, response.getBody());
    verify(redemptionService).getRedemptionsByType(RedeemTypes.GIFTED_GAME);
  }

  @Test
  void testGetRedemptionsByCompletedStatus_shouldReturnFilteredRedemptions() {
    // Given
    List<Redemption> redemptions = List.of(testRedemption);
    when(redemptionService.getRedemptionsByCompletedStatus(false)).thenReturn(redemptions);

    // When
    ResponseEntity<List<Redemption>> response = 
        redemptionController.getRedemptionsByCompletedStatus(false);

    // Then
    assertEquals(redemptions, response.getBody());
    verify(redemptionService).getRedemptionsByCompletedStatus(false);
  }

  @Test
  void testGetRedemptionsByTypeAndCompleted_shouldReturnFilteredRedemptions() {
    // Given
    List<Redemption> redemptions = List.of(testRedemption);
    when(redemptionService.getRedemptionsByTypeAndCompleted(RedeemTypes.GIFTED_GAME, false))
        .thenReturn(redemptions);

    // When
    ResponseEntity<List<Redemption>> response = 
        redemptionController.getRedemptionsByTypeAndCompleted(RedeemTypes.GIFTED_GAME, false);

    // Then
    assertEquals(redemptions, response.getBody());
    verify(redemptionService).getRedemptionsByTypeAndCompleted(RedeemTypes.GIFTED_GAME, false);
  }

  @Test
  void testSearchRedemptionsByName_shouldReturnMatchingRedemptions() {
    // Given
    List<Redemption> redemptions = List.of(testRedemption);
    when(redemptionService.searchRedemptionsByName("Test")).thenReturn(redemptions);

    // When
    ResponseEntity<List<Redemption>> response = 
        redemptionController.searchRedemptionsByName("Test");

    // Then
    assertEquals(redemptions, response.getBody());
    verify(redemptionService).searchRedemptionsByName("Test");
  }

  @Test
  void testMarkRedemptionAsCompleted_shouldReturnCompletedRedemption() {
    // Given
    Redemption completedRedemption = TestFixtures.FIXTURE_MONKEY.giveMeBuilder(Redemption.class)
        .set("id", testId)
        .set("completed", true)
        .sample();

    when(redemptionService.markRedemptionAsCompleted(testId)).thenReturn(completedRedemption);

    // When
    ResponseEntity<Redemption> response = redemptionController.markRedemptionAsCompleted(testId);

    // Then
    assertEquals(completedRedemption, response.getBody());
    verify(redemptionService).markRedemptionAsCompleted(testId);
  }

  @Test
  void testMarkRedemptionAsCompleted_shouldReturnNullWhenExceptionThrown() {
    // Given
    when(redemptionService.markRedemptionAsCompleted(testId))
        .thenThrow(new RedemptionNotFoundException("Redemption not found"));

    // When
    ResponseEntity<Redemption> response = redemptionController.markRedemptionAsCompleted(testId);

    // Then
    assertNull(response.getBody());
    verify(redemptionService).markRedemptionAsCompleted(testId);
  }

  @Test
  void testGetRedemptionsByViewer_shouldReturnViewerRedemptions() {
    // Given
    UUID viewerId = UUID.randomUUID();
    List<Redemption> redemptions = List.of(testRedemption);
    when(redemptionService.getRedemptionsByViewer(viewerId)).thenReturn(redemptions);

    // When
    ResponseEntity<List<Redemption>> response = 
        redemptionController.getRedemptionsByViewer(viewerId);

    // Then
    assertEquals(redemptions, response.getBody());
    verify(redemptionService).getRedemptionsByViewer(viewerId);
  }

  @Test
  void testGetIncompleteRedemptionsByViewer_shouldReturnIncompleteRedemptions() {
    // Given
    UUID viewerId = UUID.randomUUID();
    List<Redemption> redemptions = List.of(testRedemption);
    when(redemptionService.getIncompleteRedemptionsByViewer(viewerId)).thenReturn(redemptions);

    // When
    ResponseEntity<List<Redemption>> response = 
        redemptionController.getIncompleteRedemptionsByViewer(viewerId);

    // Then
    assertEquals(redemptions, response.getBody());
    verify(redemptionService).getIncompleteRedemptionsByViewer(viewerId);
  }

  @Test
  void testGetCompletedRedemptionsByViewer_shouldReturnCompletedRedemptions() {
    // Given
    UUID viewerId = UUID.randomUUID();
    List<Redemption> redemptions = List.of(testRedemption);
    when(redemptionService.getCompletedRedemptionsByViewer(viewerId)).thenReturn(redemptions);

    // When
    ResponseEntity<List<Redemption>> response = 
        redemptionController.getCompletedRedemptionsByViewer(viewerId);

    // Then
    assertEquals(redemptions, response.getBody());
    verify(redemptionService).getCompletedRedemptionsByViewer(viewerId);
  }
}
