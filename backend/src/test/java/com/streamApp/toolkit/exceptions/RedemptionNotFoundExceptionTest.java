package com.streamApp.toolkit.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class RedemptionNotFoundExceptionTest {

  @Test
  void testRedemptionNotFoundExceptionWithMessage_shouldReturnException() {
    // Given
    String message = "Redemption not found";

    // When
    RedemptionNotFoundException exception = new RedemptionNotFoundException(message);

    // Then
    assertNotNull(exception);
    assertEquals(message, exception.getMessage());
  }

  @Test
  void testRedemptionNotFoundExceptionWithMessageAndCause_shouldReturnException() {
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
