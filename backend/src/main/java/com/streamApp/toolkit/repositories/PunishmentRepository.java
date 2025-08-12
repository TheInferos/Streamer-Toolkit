package com.streamApp.toolkit.repositories;

import com.streamApp.toolkit.entities.Punishment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;



public interface PunishmentRepository extends JpaRepository<Punishment, UUID> {

}
