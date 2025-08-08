package com.streamApp.toolkit.repositories;

import com.streamApp.toolkit.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GameRepository extends JpaRepository<Game, UUID> {

  Optional<Game> findByName(String name);

  Optional<Game> findByGenreListContaining(String genre);
} 