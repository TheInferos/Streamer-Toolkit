package com.stream_app.toolkit.entities;

import com.navercorp.fixturemonkey.FixtureMonkey;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.UUID;

class ViewerTest {

    private static FixtureMonkey FIXTURE_MONKEY = FixtureMonkey.create();

    private Viewer viewer;

    @Test
    void testViewerCreation() {
        // Given
        Viewer testViewer = FIXTURE_MONKEY.giveMeOne(Viewer.class);

        // When
        viewer.setId(testViewer.getId());
        viewer.setTwitchHandle(testViewer.getTwitchHandle());
        viewer.setName(testViewer.getName());

        // Then
        assertEquals(testViewer.getId(), viewer.getId());
        assertEquals(testViewer.getTwitchHandle(), viewer.getTwitchHandle());
        assertEquals(testViewer.getName(), viewer.getName());
    }

    @Test
    void testIdGeneration() {
        // Given
        viewer.setName("Test User");

        // When
        viewer.assignId();

        // Then
        assertNotNull(viewer.getId());
        assertTrue(viewer.getId() instanceof UUID);
    }

    @Test
    void testIdGenerationOnlyWhenNull() {
        // Given
        UUID existingId = UUID.randomUUID();
        viewer.setId(existingId);
        viewer.setName("Test User");

        // When
        viewer.assignId();

        // Then
        assertEquals(existingId, viewer.getId());
    }

    @Test
    void testViewerWithAllArgsConstructor() {
        // Given
        Viewer viewerWithAllArgs = FIXTURE_MONKEY.giveMeBuilder(Viewer.class)
                .setNotNull("id")
                .setNotNull("twitchHandle")
                .setNotNull("name")
                .sample();

        // When & Then
        assertNotNull(viewerWithAllArgs.getId());
        assertNotNull(viewerWithAllArgs.getTwitchHandle());
        assertNotNull(viewerWithAllArgs.getName());
    }

    @Test
    void testViewerEquality() {
        // Given
        Viewer viewer1 = FIXTURE_MONKEY.giveMeBuilder(Viewer.class)
                .setNotNull("id")
                .setNotNull("twitchHandle")
                .setNotNull("name")
                .sample();

        Viewer viewer2 = new Viewer();
        viewer2.setId(viewer1.getId());
        viewer2.setTwitchHandle(viewer1.getTwitchHandle());
        viewer2.setName(viewer1.getName());

        // When & Then
        assertEquals(viewer1, viewer2);
        assertEquals(viewer1.hashCode(), viewer2.hashCode());
    }

    @Test
    void testViewerToString() {
        // Given
        Viewer testViewer = FIXTURE_MONKEY.giveMeBuilder(Viewer.class)
                .setNotNull("name")
                .sample();

        // When
        String result = testViewer.toString();

        // Then
        assertNotNull(result);
        assertTrue(result.contains(testViewer.getName()));
    }

    @Test
    void testViewerWithNullValues() {
        // Given
        viewer.setName(null);
        viewer.setTwitchHandle(null);

        // When & Then
        assertNull(viewer.getName());
        assertNull(viewer.getTwitchHandle());
    }

    @Test
    void testMultipleViewers() {
        // Given
        List<Viewer> viewers = FIXTURE_MONKEY.giveMeBuilder(Viewer.class)
                .setNotNull("id")
                .setNotNull("twitchHandle")
                .setNotNull("name")
                .sampleList(10);

        // When & Then
        assertEquals(10, viewers.size());
        viewers.forEach(viewer -> {

            assertNotNull(viewer.getId());
            assertNotNull(viewer.getTwitchHandle());
            assertNotNull(viewer.getName());
        });
    }

    @Test
    void testViewerWithCustomData() {
        // Given
        Viewer customViewer = FIXTURE_MONKEY.giveMeBuilder(Viewer.class)
                .setNotNull("id")
                .setNotNull("twitchHandle")
                .setNotNull("name")
                .sample();

        // When & Then
        assertNotNull(customViewer.getTwitchHandle());
        assertNotNull(customViewer.getName());
        assertNotNull(customViewer.getId());
    }

    @Test
    void testViewerWithSpecificTwitchHandle() {
        // Given
        Viewer customViewer = FIXTURE_MONKEY.giveMeOne(Viewer.class);
        customViewer.setTwitchHandle("customuser123");
        customViewer.setName("Custom User");

        // When & Then
        assertEquals("customuser123", customViewer.getTwitchHandle());
        assertEquals("Custom User", customViewer.getName());
    }
}