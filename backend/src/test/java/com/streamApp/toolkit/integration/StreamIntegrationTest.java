package com.streamApp.toolkit.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.streamApp.toolkit.entities.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@Transactional
@Import(com.streamApp.toolkit.config.TestConfig.class)
class StreamIntegrationTest {

  private static final int SAMPLE_LIST_SIZE = 3;

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
        .setNull("games")
        .setNull("viewers")
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
    Stream stream1 = FIXTURE_MONKEY.giveMeBuilder(Stream.class)
        .setNull("id")
        .setNull("games")
        .setNull("viewers")
        .setNotNull("name")
        .sample();

    Stream stream2 = FIXTURE_MONKEY.giveMeBuilder(Stream.class)
        .setNull("id")
        .setNull("games")
        .setNull("viewers")
        .setNotNull("name")
        .sample();

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
    Stream complexStream = FIXTURE_MONKEY.giveMeBuilder(Stream.class)
        .setNull("id")
        .setNull("games")
        .setNull("viewers")
        .setNotNull("name")
        .sample();
    if (complexStream.getName() == null) {
      complexStream.setName("Complex Stream");
    }

    // When & Then
    mockMvc.perform(post("/api/stream/add")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(complexStream)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name").value(complexStream.getName()));
  }

  @Test
  void testCreateStreamWithSpecificData() throws Exception {
    // Given
    Stream specificStream = FIXTURE_MONKEY.giveMeBuilder(Stream.class)
        .setNull("id")
        .setNull("games")
        .setNull("viewers")
        .setNotNull("name")
        .sample();
    specificStream.setName("Integration Test Stream");
    specificStream.setUrl("https://twitch.tv/integration");
    specificStream.setDescription("A stream for integration testing");

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
        .setNull("games")
        .setNull("viewers")
        .setNull("description")
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
        .setNull("games")
        .setNull("viewers")
        .setNotNull("name")
        .sampleList(SAMPLE_LIST_SIZE);

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
        .andExpect(jsonPath("$.length()").value(SAMPLE_LIST_SIZE));
  }

  @Test
  void testCreateStreamWithEmptyArrays() throws Exception {
    // Given
    Stream streamWithEmptyArrays = FIXTURE_MONKEY.giveMeBuilder(Stream.class)
        .setNull("id")
        .setNull("games")
        .setNull("viewers")
        .set("tags", List.of())
        .set("categories", List.of())
        .setNotNull("name")
        .sample();

    // When & Then
    mockMvc.perform(post("/api/stream/add")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(streamWithEmptyArrays)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value(streamWithEmptyArrays.getName()));
  }
}