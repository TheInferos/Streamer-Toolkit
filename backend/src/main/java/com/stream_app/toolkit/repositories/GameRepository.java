package com.stream_app.toolkit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.stream_app.toolkit.entities.Game;
import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, UUID> {

    Game findByName(String name);

    Game findByGenreListContaining(String genre);

} 