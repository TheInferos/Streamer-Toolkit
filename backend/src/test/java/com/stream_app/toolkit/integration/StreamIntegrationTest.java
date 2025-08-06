package com.stream_app.toolkit.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.stream_app.toolkit.entities.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Transactional
class StreamIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;
    private static FixtureMonkey FIXTURE_MONKEY = FixtureMonkey.create();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testCreateAndRetrieveStream() throws Exception {
        // Given
        Stream streamToCreate = FIXTURE_MONKEY.giveMeBuilder(Stream.class)
                .setNull("id")
                .setNotNull("name")
                .sample();

        // When - Create stream
        String response = mockMvc.perform(post("/api/stream/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(streamToCreate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(streamToCreate.getName()))
                .andReturn().getResponse().getContentAsString();

        // Then - Retrieve the created stream
        Stream createdStream = objectMapper.readValue(response, Stream.class);
        mockMvc.perform(get("/api/stream/{id}", createdStream.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdStream.getId().toString()))
                .andExpect(jsonPath("$.name").value(streamToCreate.getName()));
    }

    @Test
    void testCreateMultipleStreams() throws Exception {
        // Given
        Stream stream1 = FIXTURE_MONKEY.giveMeOne(Stream.class);
        stream1.setId(null);
        if (stream1.getName() == null) {
            stream1.setName("Test Stream 1");
        }

        Stream stream2 = FIXTURE_MONKEY.giveMeOne(Stream.class);
        stream2.setId(null);
        if (stream2.getName() == null) {
            stream2.setName("Test Stream 2");
        }

        // When - Create multiple streams
        mockMvc.perform(post("/api/stream/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(stream1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());

        mockMvc.perform(post("/api/stream/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(stream2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());

        // Then - Verify all streams are retrievable
        mockMvc.perform(get("/api/stream/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testGetAllStreams() throws Exception {
        // When & Then - Test the endpoint
        mockMvc.perform(get("/api/stream/all"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testCreateStreamWithComplexData() throws Exception {
        // Given
        Stream complexStream = FIXTURE_MONKEY.giveMeOne(Stream.class);
        complexStream.setId(null);
        if (complexStream.getName() == null) {
            complexStream.setName("Complex Stream");
        }

        // When & Then
        mockMvc.perform(post("/api/stream/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(complexStream)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(complexStream.getName()))
                .andExpect(jsonPath("$.games").isArray())
                .andExpect(jsonPath("$.tags").isArray())
                .andExpect(jsonPath("$.categories").isArray());
    }

    @Test
    void testCreateStreamWithSpecificData() throws Exception {
        // Given
        Stream specificStream = FIXTURE_MONKEY.giveMeOne(Stream.class);
        specificStream.setName("Integration Test Stream");
        specificStream.setUrl("https://twitch.tv/integration");
        specificStream.setDescription("A stream for integration testing");
        specificStream.setId(null);

        // When & Then
        mockMvc.perform(post("/api/stream/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(specificStream)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Integration Test Stream"))
                .andExpect(jsonPath("$.url").value("https://twitch.tv/integration"))
                .andExpect(jsonPath("$.description").value("A stream for integration testing"));
    }

    @Test
    void testGetStreamNotFound() throws Exception {
        // Given
        UUID nonExistentId = UUID.randomUUID();

        // When & Then
        mockMvc.perform(get("/api/stream/{id}", nonExistentId))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void testCreateStreamWithNullValues() throws Exception {
        // Given
        Stream streamWithNulls = FIXTURE_MONKEY.giveMeBuilder(Stream.class)
                .setNull("id")
                .setNull("description")
                .setNull("game")
                .setNull("tags")
                .setNull("categories")
                .setNotNull("name")
                .sample();

        // When & Then
        mockMvc.perform(post("/api/stream/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(streamWithNulls)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(streamWithNulls.getName()))
                .andExpect(jsonPath("$.url").value(streamWithNulls.getUrl()));
    }

    @Test
    void testCreateMultipleStreamsAndVerifyAll() throws Exception {
        // Given
        List<Stream> streams = FIXTURE_MONKEY.giveMeBuilder(Stream.class)
                .setNull("id")
                .setNotNull("name")
                .sampleList(5);

        // When - Create all streams
        for (Stream stream : streams) {
            mockMvc.perform(post("/api/stream/add")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(stream)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").exists());
        }

        // Then - Verify all streams are in the list
        mockMvc.perform(get("/api/stream/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(5));
    }

    @Test
    void testCreateStreamWithEmptyArrays() throws Exception {
        // Given
        Stream streamWithEmptyArrays = FIXTURE_MONKEY.giveMeBuilder(Stream.class)
                .setNull("id")
                .set("games", new String[0])
                .set("tags", new String[0])
                .set("categories", new String[0])
                .setNotNull("name")
                .sample();

        // When & Then
        mockMvc.perform(post("/api/stream/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(streamWithEmptyArrays)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.games").isArray())
                .andExpect(jsonPath("$.tags").isArray())
                .andExpect(jsonPath("$.categories").isArray());
    }
} 