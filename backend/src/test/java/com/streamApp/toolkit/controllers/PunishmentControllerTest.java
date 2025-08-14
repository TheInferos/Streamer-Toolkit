package com.streamApp.toolkit.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.streamApp.toolkit.entities.Punishment;
import com.streamApp.toolkit.service.PunishmentService;
import com.streamApp.toolkit.testutils.TestFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class PunishmentControllerTest {

  @Mock
  private PunishmentService punishmentService;

  @InjectMocks
  private PunishmentController punishmentController;

  private static final FixtureMonkey FIXTURE_MONKEY = TestFixtures.FIXTURE_MONKEY;

  private UUID testId;

  private Punishment testPunishment;

  @BeforeEach
  void setUp() {
    testId = UUID.randomUUID();
    testPunishment = FIXTURE_MONKEY.giveMeOne(Punishment.class);
  }

  @Test
  void testAddPunishment_shouldReturnPunishmentResponse() {
    // Given
    when(punishmentService.addPunishment(any(Punishment.class))).thenReturn(testPunishment);

    // When
    ResponseEntity<Punishment> response = punishmentController.addPunishment(testPunishment);

    // Then
    assertEquals(testPunishment, response.getBody());
    verify(punishmentService).addPunishment(testPunishment);
  }

  @Test
  void testGetPunishment_shouldReturnPunishmentResponse() {
    // Given
    when(punishmentService.getPunishment(testId)).thenReturn(testPunishment);

    // When
    ResponseEntity<Punishment> response = punishmentController.getPunishment(testId);

    // Then
    assertEquals(testPunishment, response.getBody());
    verify(punishmentService).getPunishment(testId);
  }

  @Test
  void testGetAllPunishments_shouldReturnPunishmentsListResponse() {
    // Given
    List<Punishment> punishments = Arrays.asList(testPunishment);
    when(punishmentService.getAllPunishments()).thenReturn(punishments);

    // When
    ResponseEntity<List<Punishment>> response = punishmentController.getAllPunishments();

    // Then
    assertEquals(punishments, response.getBody());
    verify(punishmentService).getAllPunishments();
  }

  @Test
  void testGetAllPunishments_shouldReturnEmptyListResponse() {
    // Given
    when(punishmentService.getAllPunishments()).thenReturn(Arrays.asList());

    // When
    ResponseEntity<List<Punishment>> response = punishmentController.getAllPunishments();

    // Then
    assertEquals(0, response.getBody().size());
    verify(punishmentService).getAllPunishments();
  }

  @Test
  void testUpdatePunishment_shouldReturnPunishmentResponse() {
    // Given
    Punishment updatedPunishment = FIXTURE_MONKEY.giveMeBuilder(Punishment.class)
        .set("id", testId)
        .set("name", "Updated Name")
        .set("weight", 10)
        .sample();
    when(punishmentService.updatePunishment(testId, updatedPunishment)).thenReturn(updatedPunishment);

    // When
    ResponseEntity<Punishment> response = punishmentController.updatePunishment(testId, updatedPunishment);

    assertEquals(updatedPunishment, response.getBody());
    verify(punishmentService).updatePunishment(testId, updatedPunishment);
  }

  @Test
  void testUpdatePunishment_shouldReturnNotFoundResponse() {
    // Given
    UUID nonExistentId = UUID.randomUUID();
    when(punishmentService.updatePunishment(nonExistentId, testPunishment)).thenReturn(null);

    // When
    ResponseEntity<Punishment> response = punishmentController.updatePunishment(nonExistentId, testPunishment);

    assertEquals(404, response.getStatusCode().value());
    verify(punishmentService).updatePunishment(nonExistentId, testPunishment);
  }

  @Test
  void testDeletePunishment_shouldReturnNoContentResponse() {
    // Given
    when(punishmentService.deletePunishment(testId)).thenReturn(true);

    // When
    ResponseEntity<Void> response = punishmentController.deletePunishment(testId);

    assertEquals(204, response.getStatusCode().value());
    verify(punishmentService).deletePunishment(testId);
  }

  @Test
  void testDeletePunishment_shouldReturnNotFoundResponse() {
    // Given
    when(punishmentService.deletePunishment(testId)).thenReturn(false);

    // When
    ResponseEntity<Void> response = punishmentController.deletePunishment(testId);

    assertEquals(404, response.getStatusCode().value());
    verify(punishmentService).deletePunishment(testId);
  }
}
