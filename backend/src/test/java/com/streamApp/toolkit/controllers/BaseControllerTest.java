package com.streamApp.toolkit.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BaseControllerTest {

  @InjectMocks
  private BaseController baseController;

  @Test
  void testGetMessages_shouldReturnHelloMessage() {
    // Given
    String expectedMessage = "Hello it's BunBun";
    
    // When
    String result = baseController.getMessages();

    // Then
    assertEquals(expectedMessage, result);
  }

  @Test
  void testGetStream_shouldReturnStatusMessage() {
    // Given
    String expectedMessage = "Getting Milk";
    
    // When
    String result = baseController.getStream();

    // Then
    assertEquals(expectedMessage, result);
  }
} 