package com.streamApp.toolkit.exceptions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RedemptionNotFoundExceptionTest {

  @Test
  void testRedemptionNotFoundExceptionWithMessage_shouldCreateException() {
    // Given
    String message = "Redemption not found";

    // When
    RedemptionNotFoundException exception = new RedemptionNotFoundException(message);

    // Then
    assertNotNull(exception);
    assertEquals(message, exception.getMessage());
  }

  @Test
  void testRedemptionNotFoundExceptionWithMessageAndCause_shouldCreateException() {
    // Given
    String message = "Redemption not found";
    Throwable cause = new RuntimeException("Database error");

    // When
    RedemptionNotFoundException exception = new RedemptionNotFoundException(message, cause);

    // Then
    assertNotNull(exception);
    assertEquals(message, exception.getMessage());
    assertEquals(cause, exception.getCause());
  }
}
