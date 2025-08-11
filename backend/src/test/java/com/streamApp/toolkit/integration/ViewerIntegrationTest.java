package com.streamApp.toolkit.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.streamApp.toolkit.entities.Viewer;
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
class ViewerIntegrationTest {

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
  void testCreateAndRetrieveViewer() throws Exception {
    // Given
    Viewer viewerToCreate = FIXTURE_MONKEY.giveMeBuilder(Viewer.class)
        .setNull("id")
        .setNotNull("name")
        .sample();

    // When - Create viewer
    String response = mockMvc.perform(post("/api/viewer/add")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(viewerToCreate)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name").value(viewerToCreate.getName()))
        .andReturn().getResponse().getContentAsString();

    // Then - Retrieve the created viewer
    Viewer createdViewer = objectMapper.readValue(response, Viewer.class);
    mockMvc.perform(get("/api/viewer/{id}", createdViewer.getId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(createdViewer.getId().toString()))
        .andExpect(jsonPath("$.name").value(viewerToCreate.getName()));
  }

  @Test
  void testCreateMultipleViewers() throws Exception {
    // Given
    Viewer viewer1 = FIXTURE_MONKEY.giveMeOne(Viewer.class);
    viewer1.setId(null);
    if (viewer1.getName() == null) {
      viewer1.setName("Test Viewer 1");
    }

    Viewer viewer2 = FIXTURE_MONKEY.giveMeOne(Viewer.class);
    viewer2.setId(null);
    if (viewer2.getName() == null) {
      viewer2.setName("Test Viewer 2");
    }

    // When - Create multiple viewers
    mockMvc.perform(post("/api/viewer/add")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(viewer1)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists());

    mockMvc.perform(post("/api/viewer/add")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(viewer2)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists());

    // Then - Verify all viewers are retrievable
    mockMvc.perform(get("/api/viewer/all"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$.length()").value(2));
  }

  @Test
  void testGetAllViewers() throws Exception {
    // When & Then - Test the endpoint
    mockMvc.perform(get("/api/viewer/all"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$").isArray());
  }

  @Test
  void testCreateViewerWithComplexData() throws Exception {
    // Given
    Viewer complexViewer = FIXTURE_MONKEY.giveMeOne(Viewer.class);
    complexViewer.setId(null);
    if (complexViewer.getName() == null) {
      complexViewer.setName("Complex Viewer");
    }

    // When & Then
    mockMvc.perform(post("/api/viewer/add")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(complexViewer)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.name").value(complexViewer.getName()));
  }

  @Test
  void testCreateViewerWithSpecificData() throws Exception {
    // Given
    Viewer specificViewer = FIXTURE_MONKEY.giveMeOne(Viewer.class);
    specificViewer.setName("Integration Test Viewer");
    specificViewer.setTwitchHandle("integration_test_viewer");
    specificViewer.setId(null);

    // When & Then
    mockMvc.perform(post("/api/viewer/add")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(specificViewer)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("Integration Test Viewer"))
        .andExpect(jsonPath("$.twitchHandle").value("integration_test_viewer"));
  }

  @Test
  void testGetViewerNotFound() throws Exception {
    // Given
    UUID nonExistentId = UUID.randomUUID();

    // When & Then
    mockMvc.perform(get("/api/viewer/{id}", nonExistentId))
        .andExpect(status().isOk())
        .andExpect(content().string(""));
  }

  @Test
  void testCreateViewerWithNullValues() throws Exception {
    // Given
    Viewer viewerWithNulls = FIXTURE_MONKEY.giveMeBuilder(Viewer.class)
        .setNull("id")
        .setNull("twitchHandle")
        .setNotNull("name")
        .sample();

    // When & Then
    mockMvc.perform(post("/api/viewer/add")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(viewerWithNulls)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value(viewerWithNulls.getName()));
  }

  @Test
  void testCreateMultipleViewersAndVerifyAll() throws Exception {
    // Given
    List<Viewer> viewers = FIXTURE_MONKEY.giveMeBuilder(Viewer.class)
        .setNull("id")
        .setNotNull("name")
        .sampleList(SAMPLE_LIST_SIZE);

    // When - Create all viewers
    for (Viewer viewer : viewers) {
      mockMvc.perform(post("/api/viewer/add")
          .contentType(MediaType.APPLICATION_JSON)
          .content(objectMapper.writeValueAsString(viewer)))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id").exists());
    }

    // Then - Verify all viewers are in the list
    mockMvc.perform(get("/api/viewer/all"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$.length()").value(SAMPLE_LIST_SIZE));
  }
}