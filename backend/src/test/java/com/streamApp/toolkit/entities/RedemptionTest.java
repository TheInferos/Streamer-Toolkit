package com.streamApp.toolkit.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.streamApp.toolkit.entities.enums.RedeemTypes;
import com.streamApp.toolkit.testutils.TestFixtures;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class RedemptionTest {

  private static final FixtureMonkey FIXTURE_MONKEY = TestFixtures.FIXTURE_MONKEY;

  @Test
  void testRedemptionCreation_shouldReturnRedemption() {
    // Given
    Redemption redemption = FIXTURE_MONKEY.giveMeOne(Redemption.class);

    // When & Then - verify the object was created with valid data
    assertNotNull(redemption.getId());
    assertNotNull(redemption.getName());
    assertNotNull(redemption.getDescription());
    assertNotNull(redemption.getType());
    assertNotNull(redemption.getCompleted());
    assertNotNull(redemption.getViewerId());
  }

  @Test
  void testRedemptionWithSpecificValues_shouldReturnRedemption() {
    // Given
    UUID id = UUID.randomUUID();
    String name = "Gift a Game";
    String description = "Gift a game to the streamer";
    RedeemTypes type = RedeemTypes.GIFTED_GAME;
    Boolean completed = false;
    UUID viewerId = UUID.randomUUID();
    
    Redemption redemption = FIXTURE_MONKEY.giveMeBuilder(Redemption.class)
        .set("id", id)
        .set("name", name)
        .set("description", description)
        .set("type", type)
        .set("completed", completed)
        .set("viewerId", viewerId)
        .sample();

    // When & Then
    assertEquals(name, redemption.getName());
    assertEquals(description, redemption.getDescription());
    assertEquals(type, redemption.getType());
    assertFalse(redemption.getCompleted());
    assertEquals(id, redemption.getId());
    assertEquals(viewerId, redemption.getViewerId());
  }

  @Test
  void testRedemptionWithFixtureMonkeyCustomization_shouldReturnCustomizedRedemption() {
    // Given
    UUID viewerId = UUID.randomUUID();
    Redemption redemption = FIXTURE_MONKEY.giveMeBuilder(Redemption.class)
        .set("name", "Custom Game Pick")
        .set("type", RedeemTypes.PICK_GAME)
        .set("completed", true)
        .set("viewerId", viewerId)
        .sample();

    // When & Then
    assertEquals("Custom Game Pick", redemption.getName());
    assertEquals(RedeemTypes.PICK_GAME, redemption.getType());
    assertTrue(redemption.getCompleted());
    assertNotNull(redemption.getId());
    assertNotNull(redemption.getDescription());
    assertEquals(viewerId, redemption.getViewerId());
  }

  @Test
  void testRedemptionWithNullId_shouldReturnIdOnPrePersist() {
    // Given
    UUID viewerId = UUID.randomUUID();
    Redemption redemption = FIXTURE_MONKEY.giveMeBuilder(Redemption.class)
        .set("id", (UUID) null)
        .set("name", "Test Redemption")
        .set("viewerId", viewerId)
        .sample();

    // When
    redemption.assignId();

    // Then
    assertNotNull(redemption.getId());
    assertEquals("Test Redemption", redemption.getName());
    assertEquals(viewerId, redemption.getViewerId());
  }

  @Test
  void testRedemptionWithExistingId_shouldReturnUnchangedIdOnPrePersist() {
    // Given
    UUID existingId = UUID.randomUUID();
    UUID viewerId = UUID.randomUUID();
    Redemption redemption = FIXTURE_MONKEY.giveMeBuilder(Redemption.class)
        .set("id", existingId)
        .set("name", "Test Redemption")
        .set("viewerId", viewerId)
        .sample();

    // When
    redemption.assignId();

    // Then
    assertEquals(existingId, redemption.getId());
    assertEquals("Test Redemption", redemption.getName());
    assertEquals(viewerId, redemption.getViewerId());
  }

  @Test
  void testRedemptionNoArgsConstructor_shouldReturnEmptyRedemption() {
    // When
    Redemption redemption = new Redemption();

    // Then
    assertNull(redemption.getId());
    assertNull(redemption.getName());
    assertNull(redemption.getDescription());
    assertNull(redemption.getType());
    assertNull(redemption.getCompleted());
    assertNull(redemption.getViewerId());
  }
}
