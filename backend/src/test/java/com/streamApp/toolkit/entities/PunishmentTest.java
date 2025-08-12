package com.streamApp.toolkit.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.streamApp.toolkit.testutils.TestFixtures;
import jakarta.persistence.PrePersist;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.UUID;



class PunishmentTest {

  private static final FixtureMonkey FIXTURE_MONKEY = TestFixtures.FIXTURE_MONKEY;

  @Test
  void testAssignId_shouldReturnGeneratedUuidWhenIdIsNull() {
    // Given
    Punishment punishment = FIXTURE_MONKEY.giveMeBuilder(Punishment.class)
        .setNull("id")
        .sample();

    // When
    punishment.assignId();

    // Then
    assertNotNull(punishment.getId());
    assertTrue(punishment.getId() instanceof UUID);
  }

  @Test
  void testAssignId_shouldReturnExistingIdWhenIdIsNotNull() {
    // Given
    Punishment punishment = FIXTURE_MONKEY.giveMeOne(Punishment.class);
    UUID testId = punishment.getId();

    // When
    punishment.assignId();

    // Then
    assertEquals(testId, punishment.getId());
  }

  @Test
  void testAssignId_shouldReturnPrePersistAnnotation() throws NoSuchMethodException {
    // Given
    Method assignIdMethod = Punishment.class.getDeclaredMethod("assignId");

    // Then
    assertTrue(assignIdMethod.isAnnotationPresent(PrePersist.class));
  }
}
