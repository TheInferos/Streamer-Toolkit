package com.stream_app.toolkit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.stream_app.toolkit.entities.Stream;
import java.util.UUID;

public interface StreamRepository extends JpaRepository<Stream, UUID> {

    Stream findByUrl(String url);

    Stream findByName(String name);

    Stream findByDescription(String description);

}