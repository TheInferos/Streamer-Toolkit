package com.stream_app.toolkit.repositories;

import com.stream_app.toolkit.entities.Viewer;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public interface ViewerRepository extends JpaRepository<Viewer, UUID>{

    @Query(value = "SELECT * FROM viewer", nativeQuery = true)
    List<Viewer> getAllViewers();

}
