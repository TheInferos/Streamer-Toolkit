package com.streamApp.toolkit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.streamApp.toolkit.entities.Punishment;
import com.streamApp.toolkit.repositories.PunishmentRepository;
import com.streamApp.toolkit.testutils.TestFixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class PunishmentServiceTest {

  @Mock
  private PunishmentRepository punishmentRepository;

  @InjectMocks
  private PunishmentService punishmentService;

  private static final FixtureMonkey FIXTURE_MONKEY = TestFixtures.FIXTURE_MONKEY;

  @Test
  void testAddPunishment_shouldReturnSavedPunishment() {
    // Given
    Punishment testPunishment = FIXTURE_MONKEY.giveMeOne(Punishment.class);
    when(punishmentRepository.save(any(Punishment.class))).thenReturn(testPunishment);

    // When
    Punishment result = punishmentService.addPunishment(testPunishment);

    // Then
    assertEquals(testPunishment, result);
    verify(punishmentRepository).save(testPunishment);
  }

  @Test
  void testGetPunishment_shouldReturnPunishmentWhenFound() {
    // Given
    Punishment testPunishment = FIXTURE_MONKEY.giveMeOne(Punishment.class);
    UUID testId = testPunishment.getId();
    when(punishmentRepository.findById(testId)).thenReturn(Optional.of(testPunishment));

    // When
    Punishment result = punishmentService.getPunishment(testId);

    // Then
    assertEquals(testPunishment, result);
    verify(punishmentRepository).findById(testId);
  }

  @Test
  void testGetPunishment_shouldReturnNullWhenNotFound() {
    // Given
    UUID testId = UUID.randomUUID();
    when(punishmentRepository.findById(testId)).thenReturn(Optional.empty());

    // When
    Punishment result = punishmentService.getPunishment(testId);

    // Then
    assertEquals(null, result);
    verify(punishmentRepository).findById(testId);
  }

  @Test
  void testGetAllPunishments_shouldReturnPunishmentsList() {
    // Given
    Punishment testPunishment = FIXTURE_MONKEY.giveMeOne(Punishment.class);
    List<Punishment> punishments = Arrays.asList(testPunishment);
    when(punishmentRepository.findAll()).thenReturn(punishments);

    // When
    List<Punishment> result = punishmentService.getAllPunishments();

    // Then
    assertEquals(punishments, result);
    verify(punishmentRepository).findAll();
  }
}
