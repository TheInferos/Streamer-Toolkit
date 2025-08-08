package com.streamApp.toolkit.repositories;

import com.streamApp.toolkit.entities.Stream;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StreamRepository extends JpaRepository<Stream, UUID> {

  Stream findByUrl(String url);

  Stream findByName(String name);

  Stream findByDescription(String description);

}