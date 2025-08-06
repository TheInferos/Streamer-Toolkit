package com.stream_app.toolkit.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class BaseControllerTest {

    @InjectMocks
    private BaseController baseController;

    @Test
    void testGetMessages_ShouldReturnHelloMessage() {
        // Given
        String expectedMessage = "Hello it's BunBun";
        
        // When
        String result = baseController.getMessages();

        // Then
        assertEquals(expectedMessage, result);
    }

    @Test
    void testGetStream_ShouldReturnStatusMessage() {
        // Given
        String expectedMessage = "Getting Milk";
        
        // When
        String result = baseController.getStream();

        // Then
        assertEquals(expectedMessage, result);
    }
} 